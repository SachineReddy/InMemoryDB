package com.bookstore.command;

import com.bookstore.repository.InventoryRepository;

public class AddBookCopiesCommand implements BookCommand {
    private transient InventoryRepository inventoryRepository;
    private Integer bookId;
    private Integer additionalCopies;

    public AddBookCopiesCommand(InventoryRepository inventoryRepository, Integer bookId, Integer additionalCopies) {
        this.inventoryRepository = inventoryRepository;
        this.bookId = bookId;
        this.additionalCopies = additionalCopies;
    }

    public Boolean execute() {
        return inventoryRepository.addCopies(bookId, additionalCopies);
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
