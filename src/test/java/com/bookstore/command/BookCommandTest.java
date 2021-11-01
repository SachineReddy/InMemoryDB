package com.bookstore.command;

import com.bookstore.model.Book;
import com.bookstore.repository.InventoryRepository;
import com.bookstore.repository.InventoryRepositoryImpl;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class BookCommandTest {

    InventoryRepository inventoryRepository;

    @Before
    public void setup() {
        inventoryRepository = new InventoryRepositoryImpl();
        Book book = new Book(1, "Sachin", 2, 10f);
        BookCommand addBookCommand = new AddBookCommand(inventoryRepository, book);
        addBookCommand.execute();
    }

    @Test
    public void testAddCommandExecute() {
        Book book = new Book(2, "Aditya", 2, 10f);
        BookCommand addBookCommand = new AddBookCommand(inventoryRepository, book);
        addBookCommand.execute();

        String bookNameOfId1 = inventoryRepository.getBookInventory().get(2).getBookName();
        String expectedBookName = "Aditya";

        assertEquals(expectedBookName, bookNameOfId1);
        assertThat(inventoryRepository.getBookInventory().size(), is(2));
    }

    @Test
    public void testSellBookCommandExecute() {
        BookCommand sellBookCommand = new SellBookCommand(inventoryRepository, 1);
        sellBookCommand.execute();

        int availableCopies = inventoryRepository.getBookInventory().get(1).getCopiesAvailable();
        assertEquals(availableCopies, 1);
    }

}