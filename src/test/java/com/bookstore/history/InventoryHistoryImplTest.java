package com.bookstore.history;

import com.bookstore.command.BookCommand;
import com.bookstore.decorator.InventoryCommandDecorator;
import com.bookstore.memento.InventoryMemento;
import com.bookstore.model.Book;
import com.bookstore.repository.InventoryRepository;
import com.bookstore.repository.InventoryRepositoryImpl;
import com.bookstore.util.PropertiesCache;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.bookstore.io.FileUtils.deleteFilesMatchingFileNamePattern;
import static com.bookstore.io.FileUtils.flushFile;
import static com.bookstore.util.constant.PropertiesConstant.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InventoryHistoryImplTest {

    InventoryRepository inventoryRepository;
    InventoryCommandDecorator inventoryCommandDecorator;
    InventoryHistory inventoryHistory;

    @BeforeEach
    void setUp() {
        inventoryRepository = new InventoryRepositoryImpl();
        inventoryCommandDecorator =new InventoryCommandDecorator(inventoryRepository);
        inventoryHistory = new InventoryHistoryImpl();

        Book book1 = new Book(1,"sac", 1, 2.00f);
        Book book2 = new Book(2,"frm", 3, 5.00f);
        Book book3 = new Book(3,"sac33", 13, 23.00f);
        Book book4 = new Book(4,"frm44", 34, 54.00f);

        //execution of below code will create an Inventory snapshot and a command file with the last command.
        // As the max commands to be saved before creating a snapshot is set to '3' for testing
        inventoryCommandDecorator.addBook(book1);
        inventoryCommandDecorator.addBook(book2);
        inventoryCommandDecorator.addBook(book3);
        inventoryCommandDecorator.addBook(book4);
    }

    @Test
    void testGetInventorySnapshot() {
        InventoryMemento inventoryMemento = inventoryHistory.getInventorySnapshot();
        assertThat(inventoryMemento.getBookInventory().size(), is(3));
    }

    @Test
    void testGetSavedCommandsFromHistory() {
        List<BookCommand> bookCommandList = inventoryHistory.getSavedCommandsFromHistory();
        assertThat(bookCommandList.size(), is(1));
    }

    @Test
    void testRestoreLastInventoryState() {
        InventoryRepository inventoryRepository = new InventoryRepositoryImpl();
        inventoryHistory.restoreLastInventoryState(inventoryRepository);
        assertThat(inventoryRepository.getBookInventory().size(), is(4));
    }

}