package com.bookstore.command;

import com.bookstore.model.Book;
import com.bookstore.repository.InventoryRepository;

public class SellBookCommand implements BookCommand {
    private transient InventoryRepository inventoryRepository;
    private Integer bookId;

    public SellBookCommand(InventoryRepository inventoryRepository, Integer bookId) {
        this.inventoryRepository = inventoryRepository;
        this.bookId = bookId;
    }

    public Book execute() {
        return inventoryRepository.sellBook(bookId);
    }

    @Override
    public void setInventoryRepository(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public InventoryRepository getInventoryRepository() {
        return inventoryRepository;
    }
}
