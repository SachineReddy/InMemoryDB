package com.bookstore.command;

import com.bookstore.repository.InventoryRepository;

import java.io.Serializable;

public interface BookCommand<T> extends Serializable {

    T execute();

    void setInventoryRepository(InventoryRepository inventoryRepository);

    InventoryRepository getInventoryRepository();
}
