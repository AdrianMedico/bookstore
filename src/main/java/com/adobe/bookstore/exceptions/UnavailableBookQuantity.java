package com.adobe.bookstore.exceptions;

import com.adobe.bookstore.models.Book;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class UnavailableBookQuantity extends Exception {

    private static final String DEFAULT_ERROR_MESSAGE = "Rejected order: the requested quantity for: '%s' with id: '%s' is not available";

    public UnavailableBookQuantity(Book book) {
        super(String.format(DEFAULT_ERROR_MESSAGE, book.getName(),book.getId()));
    }

    public UnavailableBookQuantity(String message) {
        super(message);
    }

    public UnavailableBookQuantity(String message, Throwable cause) {
        super(message, cause);
    }
}
