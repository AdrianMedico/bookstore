package com.adobe.bookstore.service;

import com.adobe.bookstore.exceptions.BookValidationException;
import com.adobe.bookstore.exceptions.UnavailableBook;
import com.adobe.bookstore.exceptions.UnavailableBookQuantity;
import com.adobe.bookstore.models.Book;
import java.util.List;

public interface BooksService {

    String processBooksOrder(List<Book> desiredBooks) throws UnavailableBook, UnavailableBookQuantity, BookValidationException;

}
