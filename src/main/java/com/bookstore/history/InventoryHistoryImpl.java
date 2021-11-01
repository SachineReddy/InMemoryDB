package com.bookstore.history;

import com.bookstore.command.BookCommand;
import com.bookstore.io.FileUtils;
import com.bookstore.memento.InventoryMemento;
import com.bookstore.repository.InventoryRepository;
import com.bookstore.util.PropertiesCache;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.bookstore.io.FileUtils.*;
import static com.bookstore.util.constant.PropertiesConstant.*;

public class InventoryHistoryImpl implements InventoryHistory {
    private static Integer commandsSaved = 0;

    private static final Integer MAX_COMMANDS_TO_SAVE;
    private static final String INVENTORY_FILENAME_STARTS_WITH;
    private static final String INVENTORY_DIR;
    private static final String COMMANDS_FILE_PATH;
    private static final String TXT_EXTENSION;

    static {
        PropertiesCache properties = PropertiesCache.getInstance();
        MAX_COMMANDS_TO_SAVE = Integer.valueOf(properties.getProperty(MAX_COMMANDS_SAVE));
        INVENTORY_FILENAME_STARTS_WITH = properties.getProperty(INVENTORY_FILE_STARTS_WITH);
        INVENTORY_DIR = properties.getProperty(INVENTORY_DIR_PATH);
        COMMANDS_FILE_PATH = properties.getProperty(COMMAND_FILE_PATH);
        TXT_EXTENSION = properties.getProperty(TXT_FILE_EXTENSION);
    }

    @Override
    public void updateInventorySnapshot(InventoryMemento inventoryMemento) {
        String fileName = INVENTORY_FILENAME_STARTS_WITH + System.currentTimeMillis() + TXT_EXTENSION;
        String filePath = INVENTORY_DIR + fileName;
        writeObjectToFile(inventoryMemento, filePath, false);
        //to delete the Inventory snapshot with old timestamp
        deleteFilesMatchingFileNamePattern(INVENTORY_DIR, INVENTORY_FILENAME_STARTS_WITH, Collections.singletonList(fileName));
        flushFile(COMMANDS_FILE_PATH);
    }

    @Override
    public InventoryMemento getInventorySnapshot() {
        return Arrays.stream(FileUtils.getFilesMatchingPattern(INVENTORY_DIR, INVENTORY_FILENAME_STARTS_WITH))
                .map(File::getPath)
                .map(filePath -> (InventoryMemento) FileUtils.readObjectFromFile(filePath))
                .findFirst().orElse(null);
    }

    @Override
    public void saveCommandsToHistory(BookCommand bookCommand) {
        writeObjectToFile(bookCommand, COMMANDS_FILE_PATH, true);
        commandsSaved++;
        if (commandsSaved >= MAX_COMMANDS_TO_SAVE) {
            updateInventorySnapshot(bookCommand.getInventoryRepository().createInventoryMemento());
            commandsSaved = 0;
        }
    }

    @Override
    public List<BookCommand> getSavedCommandsFromHistory() {
        List<Object> commandObjectList = readObjectArrayFromFile(COMMANDS_FILE_PATH);
        if (!commandObjectList.isEmpty())
            return (List<BookCommand>) (Object) commandObjectList;
        return Collections.emptyList();
    }

    @Override
    public void restoreLastInventoryState(InventoryRepository inventoryRepository) {
        inventoryRepository.restoreFromInventoryMemento(getInventorySnapshot());
        List<BookCommand> bookCommandList = getSavedCommandsFromHistory();
        bookCommandList.forEach(command -> command.setInventoryRepository(inventoryRepository));
        bookCommandList.forEach(BookCommand::execute);
    }

}
