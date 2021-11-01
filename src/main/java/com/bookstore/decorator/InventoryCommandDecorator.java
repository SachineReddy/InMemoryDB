package com.bookstore.decorator;

import com.bookstore.command.*;
import com.bookstore.history.InventoryHistory;
import com.bookstore.history.InventoryHistoryImpl;
import com.bookstore.model.Book;
import com.bookstore.repository.InventoryRepository;

public class InventoryCommandDecorator extends InventoryDecorator {

    private InventoryHistory inventoryHistory;

    public InventoryCommandDecorator(InventoryRepository inventoryRepository) {
        super(inventoryRepository);
        inventoryHistory = new InventoryHistoryImpl();
    }

    public boolean addBook(Book book) {
        BookCommand addBookCommand = new AddBookCommand(inventoryRepository, book);
        boolean addStatus = (Boolean) addBookCommand.execute();
        inventoryHistory.saveCommandsToHistory(addBookCommand);
        return addStatus;
    }

    public Book sellBook(Integer bookId) {
        BookCommand sellBookCommand = new SellBookCommand(inventoryRepository, bookId);
        Book soldBook = (Book) sellBookCommand.execute();
        inventoryHistory.saveCommandsToHistory(sellBookCommand);
        return soldBook;
    }

    public boolean addCopies(Integer bookId, Integer additionalCopies) {
        BookCommand addBookCopiesCommand = new AddBookCopiesCommand(inventoryRepository, bookId, additionalCopies);
        boolean addBookCopiesStatus = (Boolean) addBookCopiesCommand.execute();
        inventoryHistory.saveCommandsToHistory(addBookCopiesCommand);
        return addBookCopiesStatus;
    }

    public boolean updatePrice(Integer bookId, Float newPrice) {
        BookCommand updatePriceBookCommand = new UpdateBookPriceCommand(inventoryRepository, bookId, newPrice);
        boolean updatePriceStatus = (Boolean) updatePriceBookCommand.execute();
        inventoryHistory.saveCommandsToHistory(updatePriceBookCommand);
        return updatePriceStatus;
    }

}
