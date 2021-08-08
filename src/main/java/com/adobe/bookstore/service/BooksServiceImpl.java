package com.adobe.bookstore.service;

import com.adobe.bookstore.exceptions.BookValidationException;
import com.adobe.bookstore.exceptions.UnavailableBook;
import com.adobe.bookstore.exceptions.UnavailableBookQuantity;
import com.adobe.bookstore.models.Book;
import com.adobe.bookstore.models.Order;
import com.adobe.bookstore.repository.BookRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class BooksServiceImpl implements BooksService {

    private AsyncTaskExecutor executor;
    private final BookRepository bookRepository;
    private final OrdersService ordersService;

    @Autowired
    public BooksServiceImpl(BookRepository bookRepository,
        OrdersService ordersService) {
        this.bookRepository = bookRepository;
        this.ordersService = ordersService;
    }

    @Autowired
    @Qualifier("mvcTaskExecutor")
    public void setExecutor(AsyncTaskExecutor executor) {
        this.executor = executor;
    }

    public String processBooksOrder(List<Book> desiredBooks)
        throws UnavailableBook, UnavailableBookQuantity, BookValidationException {
        validateBooks(desiredBooks);
        Map<String, Book> desiredBooksStock = getAvailableBooksById(desiredBooks);

        List<Book> booksToUpdate = updateAvailableStockForSelectedBooks(desiredBooks,
            desiredBooksStock);
        Order order = ordersService.createOrder();
        executor.execute(() -> updateBooks(booksToUpdate));
        return order.getId();
    }

    private Map<String, Book> getAvailableBooksById(List<Book> desiredBooks) {
        List<Book> existentDesiredBooks = bookRepository
            .findAllById(desiredBooks.stream().map(Book::getId).collect(Collectors.toList()));
        return existentDesiredBooks.stream()
            .collect(Collectors.toMap(Book::getId, Function.identity()));
    }

    private List<Book> updateAvailableStockForSelectedBooks(List<Book> desiredBooks,
        Map<String, Book> desiredBooksStock) throws UnavailableBook, UnavailableBookQuantity {
        List<Book> booksToUpdate = new ArrayList<>();

        for (Book desiredBook : desiredBooks) {
            if (desiredBooksStock.containsKey(desiredBook.getId())) {
                Book bookInStock = desiredBooksStock.get(desiredBook.getId());
                int currentStock = bookInStock.getQuantity() - desiredBook.getQuantity();
                if (currentStock >= 0) {
                    bookInStock.setQuantity(currentStock);
                    booksToUpdate.add(bookInStock);
                } else {
                    throw new UnavailableBookQuantity(bookInStock);
                }
            } else {
                throw new UnavailableBook(desiredBook);
            }
        }
        return booksToUpdate;
    }

    public void updateBooks(List<Book> booksToUpdate) {
        bookRepository.saveAll(booksToUpdate);
    }

    private void validateBooks(List<Book> books) throws BookValidationException {
        if (books.stream().anyMatch(book ->
            book.getId() == null || book.getName() == null || book.getQuantity() <= 0)) {
            throw new BookValidationException();
        }
    }


}
