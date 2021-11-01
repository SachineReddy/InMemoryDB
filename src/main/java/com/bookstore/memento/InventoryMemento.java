package com.bookstore.memento;

import com.bookstore.model.Book;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;

public class InventoryMemento implements Serializable {
    private static final long serialVersionUID = 1L;
    private Hashtable<Integer, Book> bookInventory;
    private AtomicInteger nextBookIndex;

    public InventoryMemento(Hashtable<Integer, Book> bookInventory, AtomicInteger nextBookIndex) {
        this.bookInventory = bookInventory;
        this.nextBookIndex = nextBookIndex;
    }

    public Hashtable<Integer, Book> getBookInventory() {
        return this.bookInventory;
    }

    public AtomicInteger getNextBookIndex() {
        return this.nextBookIndex;
    }
}
