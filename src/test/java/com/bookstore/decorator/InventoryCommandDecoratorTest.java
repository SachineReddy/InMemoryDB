package com.bookstore.decorator;

import com.bookstore.model.Book;
import com.bookstore.repository.InventoryRepository;
import com.bookstore.repository.InventoryRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

public class InventoryCommandDecoratorTest {

    InventoryDecorator inventoryDecorator;
    InventoryRepository inventoryRepository;
    Book book2;

    @BeforeEach
    public void setUp() {
        inventoryRepository = new InventoryRepositoryImpl();
        inventoryDecorator = new InventoryCommandDecorator(inventoryRepository);
        Book book = new Book(1, "Sachin", 2, 10f);
        book2 = new Book(2, "Aditya", 3, 20f);
        inventoryRepository.addBook(book);
    }

    @Test
    public void testAddBook() {
        inventoryDecorator.addBook(book2);
        String bookNameforId2 = inventoryRepository.getBookInventory().get(2).getBookName();
        String expectedBookName = "Aditya";
        assertEquals(expectedBookName, bookNameforId2);
    }

    @Test
    public void testUpdatePrice() {
        inventoryDecorator.updatePrice(1, 15f);
        assertEquals((Float)15f, inventoryRepository.getBookInventory().get(1).getPrice());
    }

    @Test
    public void testGetQuantityByName(){
        String bookName = "Sachin";
        Predicate<Book> bookNameEqual = book -> book.getBookName().equals(bookName);
        Function<Book, Object> bookQuantity = Book::getCopiesAvailable;

        Integer actualQuantity = (Integer) inventoryDecorator.get(bookNameEqual, bookQuantity);

        Integer expectedQuantity = 2;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void testGetPriceById(){
        Integer bookId  = 1;
        Predicate<Book> bookIdEqual = book -> book.getBookId().equals(bookId);
        Function<Book, Object> bookPrice = Book::getPrice;

        Float actualPrice = (Float) inventoryDecorator.get(bookIdEqual, bookPrice);

        Float expectedPrice = 10f;
        assertEquals(expectedPrice, actualPrice);
    }

}