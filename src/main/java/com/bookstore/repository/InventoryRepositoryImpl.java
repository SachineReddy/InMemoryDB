package com.bookstore.repository;

import com.bookstore.memento.InventoryMemento;
import com.bookstore.model.Book;

import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;

public class InventoryRepositoryImpl implements InventoryRepository {
    private Hashtable<Integer, Book> bookInventory;
    private AtomicInteger nextBookId;

    public InventoryRepositoryImpl() {
        bookInventory = new Hashtable<>();
        nextBookId = new AtomicInteger(0);
    }

    private Integer getNextBookId() {
        return this.nextBookId.incrementAndGet();
    }

    @Override
    public Hashtable<Integer, Book> getBookInventory() {
        return this.bookInventory;
    }

    public boolean addBook(Book book) {
        Book bookInInventory = bookInventory.get(book.getBookId());
        if (bookInInventory == null) {
            book.setBookId(getNextBookId());
        }
        bookInventory.put(book.getBookId(), book);
        return true;
    }

    public Book sellBook(Integer bookId) {
        Book bookInInventory = bookInventory.get(bookId);
        if (bookInInventory != null && bookInInventory.getCopiesAvailable() > 0) {
            bookInInventory.setCopiesAvailable(bookInInventory.getCopiesAvailable() - 1);
        }
        return bookInInventory;
    }

    public boolean addCopies(Integer bookId, Integer additionalCopies) {
        Book bookInInventory = bookInventory.get(bookId);
        if (bookInInventory != null) {
            bookInInventory.setCopiesAvailable(bookInInventory.getCopiesAvailable() + additionalCopies);
        }
        return true;
    }

    public boolean updatePrice(Integer bookId, Float newPrice) {
        Book bookInInventory = bookInventory.get(bookId);
        if (bookInInventory != null) {
            bookInInventory.setPrice(newPrice);
        }
        return true;
    }

    @Override
    public Object get(Predicate<Book> criteria, Function<Book, Object> dataMapper) {
        return bookInventory.values().stream()
                .filter(criteria)
                .map(dataMapper)
                .findFirst().orElse(null);
    }

    public InventoryMemento createInventoryMemento() {
        return new InventoryMemento(bookInventory, nextBookId);
    }

    public void restoreFromInventoryMemento(InventoryMemento inventoryMemento) {
        if (inventoryMemento != null) {
            bookInventory = inventoryMemento.getBookInventory();
            nextBookId = inventoryMemento.getNextBookIndex();
        }
    }

}
