package com.adobe.bookstore.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BOOKS")
public class Book {
    @Id
    @Column(name = "BOOK_ID")
    private String id;
    @Column(name = "BOOK_NAME")
    private String name;
    @Column(name = "BOOK_QUANTITY")
    private int quantity;

    public Book() {
    }

    public Book(String id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String bookId) {
        this.id = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String bookName) {
        this.name = bookName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int bookQuantity) {
        this.quantity = bookQuantity;
    }
}
