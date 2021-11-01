package com.bookstore.history;

import com.bookstore.command.BookCommand;
import com.bookstore.memento.InventoryMemento;
import com.bookstore.repository.InventoryRepository;

import java.util.List;

public interface InventoryHistory {
    void updateInventorySnapshot(InventoryMemento inventoryMemento);

    InventoryMemento getInventorySnapshot();

    void saveCommandsToHistory(BookCommand bookCommand);

    List<BookCommand> getSavedCommandsFromHistory();

    void restoreLastInventoryState(InventoryRepository inventoryRepository);

}
