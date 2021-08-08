package com.adobe.bookstore.exceptions;

import com.adobe.bookstore.models.Book;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class UnavailableBook extends Exception {

    private static final String DEFAULT_ERROR_MESSAGE =
        "Rejected order: the book: '%s' with id: '%s' is not available";


    public UnavailableBook(Book book) {
        super(String.format(DEFAULT_ERROR_MESSAGE, book.getName(), book.getId()));
    }

    public UnavailableBook(String message) {
        super(message);

    }
}
