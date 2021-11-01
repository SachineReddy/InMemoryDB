package com.bookstore.model;

import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bookName;
    private Integer copiesAvailable;
    private Integer bookId;
    private Float price;

    public Book(Integer bookId, String bookName, Integer copiesAvailable, Float price) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.copiesAvailable = copiesAvailable;
        this.price = price;
    }

    public String getBookName() {
        return this.bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getCopiesAvailable() {
        return this.copiesAvailable;
    }

    public void setCopiesAvailable(Integer copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

    public Integer getBookId() {
        return this.bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String toString() {
        return "Book{bookName='" + this.bookName + "', copiesAvailable=" + this.copiesAvailable + ", bookId=" + this.bookId + ", price=" + this.price + "}";
    }
}
