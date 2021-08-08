package com.adobe.bookstore.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.adobe.bookstore.exceptions.BookValidationException;
import com.adobe.bookstore.exceptions.UnavailableBook;
import com.adobe.bookstore.exceptions.UnavailableBookQuantity;
import com.adobe.bookstore.models.Book;
import com.adobe.bookstore.service.BooksServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/books", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class BooksController {

    private final BooksServiceImpl booksService;

    @Autowired
    public BooksController(BooksServiceImpl booksService) {
        this.booksService = booksService;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String buyOrder(@RequestBody List<Book> books)
        throws UnavailableBook, UnavailableBookQuantity, BookValidationException {

        return booksService.processBooksOrder(books);
    }


}
