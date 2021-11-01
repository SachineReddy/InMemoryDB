package com.bookstore.command;

import com.bookstore.model.Book;
import com.bookstore.repository.InventoryRepository;

public class AddBookCommand implements BookCommand {

    private transient InventoryRepository inventoryRepository;
    private Book book;

    public AddBookCommand(InventoryRepository inventoryRepository, Book book) {
        this.inventoryRepository = inventoryRepository;
        this.book = book;
    }

    public Boolean execute() {
        return inventoryRepository.addBook(book);
    }

    public InventoryRepository getInventoryRepository() {
        return inventoryRepository;
    }

    public void setInventoryRepository(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
}
