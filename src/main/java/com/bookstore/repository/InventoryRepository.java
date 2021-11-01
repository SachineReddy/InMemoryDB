package com.bookstore.repository;

import com.bookstore.memento.InventoryMemento;
import com.bookstore.model.Book;

import java.util.Hashtable;
import java.util.function.Function;
import java.util.function.Predicate;

public interface InventoryRepository {

    boolean addBook(Book book);

    Book sellBook(Integer bookId);

    boolean addCopies(Integer bookId, Integer additionalCopies);

    boolean updatePrice(Integer bookId, Float newPrice);

    InventoryMemento createInventoryMemento();

    void restoreFromInventoryMemento(InventoryMemento inventoryMemento);

    Object get(Predicate<Book> criteria, Function<Book, Object> dataMapper);

    Hashtable<Integer, Book> getBookInventory();
}
