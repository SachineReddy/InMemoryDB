package com.bookstore.command;

import com.bookstore.repository.InventoryRepository;

public class UpdateBookPriceCommand implements BookCommand {
    private transient InventoryRepository inventoryRepository;
    private Integer bookId;
    private Float newPrice;

    public UpdateBookPriceCommand(InventoryRepository inventoryRepository, Integer bookId, Float newPrice) {
        this.inventoryRepository = inventoryRepository;
        this.bookId = bookId;
        this.newPrice = newPrice;
    }

    public Boolean execute() {
        return inventoryRepository.updatePrice(bookId, newPrice);
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
