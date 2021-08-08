package com.adobe.bookstore.exceptions;

import com.adobe.bookstore.models.Book;

public class BookValidationException extends Exception {

    private static final String ERROR_MESSAGE_SPECIFIC_BOOK =
        "Rejected order: there is an invalid book. \n Received ID: %s \n Received Name: %s \n Received Quantity: %s";

    private static final String DEFAULT_ERROR_MESSAGE = "Rejected order: there is one or more invalid books.";


    public BookValidationException(Book book) {
        super(
            String.format(ERROR_MESSAGE_SPECIFIC_BOOK, book.getId(), book.getName(), book.getQuantity()));
    }


    public BookValidationException() {
    super(DEFAULT_ERROR_MESSAGE);
}}
