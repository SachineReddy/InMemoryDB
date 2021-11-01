package com.bookstore.decorator;

import com.bookstore.memento.InventoryMemento;
import com.bookstore.model.Book;
import com.bookstore.repository.InventoryRepository;

import java.util.Hashtable;
import java.util.function.Function;
import java.util.function.Predicate;

public class InventoryDecorator implements InventoryRepository {
    protected InventoryRepository inventoryRepository;

    public InventoryDecorator(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public boolean addBook(Book book) {
        return inventoryRepository.addBook(book);
    }

    public Book sellBook(Integer bookId) {
        return inventoryRepository.sellBook(bookId);
    }

    public boolean addCopies(Integer bookId, Integer additionalCopies) {
        return inventoryRepository.addCopies(bookId, additionalCopies);
    }

    public boolean updatePrice(Integer bookId, Float newPrice) {
        return inventoryRepository.updatePrice(bookId, newPrice);
    }

    @Override
    public Object get(Predicate<Book> criteria, Function<Book, Object> dataMapper) {
        return inventoryRepository.get(criteria, dataMapper);
    }

    public InventoryMemento createInventoryMemento() {
        return inventoryRepository.createInventoryMemento();
    }

    public void restoreFromInventoryMemento(InventoryMemento inventoryMemento) {
        inventoryRepository.restoreFromInventoryMemento(inventoryMemento);
    }

    @Override
    public Hashtable<Integer, Book> getBookInventory() {
        return inventoryRepository.getBookInventory();
    }

}
