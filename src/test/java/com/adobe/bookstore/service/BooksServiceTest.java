package com.adobe.bookstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.anyIterable;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;

import com.adobe.bookstore.exceptions.BookValidationException;
import com.adobe.bookstore.exceptions.UnavailableBook;
import com.adobe.bookstore.exceptions.UnavailableBookQuantity;
import com.adobe.bookstore.models.Book;
import com.adobe.bookstore.models.Order;
import com.adobe.bookstore.repository.BookRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

class BooksServiceTest {

    private BooksServiceImpl bookService;

    private BookRepository bookRepository = mock(BookRepository.class);

    private OrdersService ordersService = mock(OrdersService.class);


    @BeforeEach
    void setUp() {
        bookService = new BooksServiceImpl(bookRepository, ordersService);
    }

    @Test
    void processBooksOrderShouldWorkAsExpectedWhenValidInput() throws Exception {
        given(bookRepository.findAllById(anyIterable())).willReturn(books());
        given(ordersService.createOrder()).willReturn(new Order());
        bookService.setExecutor(new SimpleAsyncTaskExecutor());

        String order = bookService.processBooksOrder(books());

        assertThat(order).hasSize(36); // uuid length
        verify(bookRepository, times(1)).findAllById(anyIterable());
        verify(ordersService, times(1)).createOrder();
        verify(bookRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void processBooksOrdersShouldRaiseBookValidationExceptionWhenInvalidInput() {
        // given
        List<Book> invalidBookOrder = new ArrayList<>();
        invalidBookOrder.add(new Book(null, null, 0));

        // when
        Exception exception = assertThrows(BookValidationException.class,
            () -> bookService.processBooksOrder(invalidBookOrder));

        // then
        assertEquals("Rejected order: there is one or more invalid books.", exception.getMessage());
    }

    @Test
    void processBooksOrdersShouldRaiseUnavailableBookWhenBookNonExists() {
        given(bookRepository.findAllById(anyIterable())).willReturn(books());
        List<Book> bookOrder = new ArrayList<>();
        bookOrder.add(new Book("unavailableBookID", "dupa", 1));

        // when
        Exception exception = assertThrows(UnavailableBook.class,
            () -> bookService.processBooksOrder(bookOrder));

        // then
        assertThat(exception.getMessage()).contains("is not available");
    }

    @Test
    void processBooksOrdersShouldRaiseUnavailableBookQuantityWhenBookStockIsLowerThanBookOrder() {
        given(bookRepository.findAllById(anyIterable())).willReturn(books());
        List<Book> bookOrder = new ArrayList<>();
        bookOrder.add(
            new Book("22d580fc-d02e-4f70-9980-f9693c18f6e0", "dolore aliqua sint ipsum laboris",
                900));

        // when
        Exception exception = assertThrows(UnavailableBookQuantity.class,
            () -> bookService.processBooksOrder(bookOrder));

        // then
        assertThat(exception.getMessage()).isEqualTo(
            "Rejected order: the requested quantity for: 'dolore aliqua sint ipsum laboris' with id: '22d580fc-d02e-4f70-9980-f9693c18f6e0' is not available");
    }

    private List<Book> books() {
        List<Book> books = new ArrayList<>();
        books.add(
            new Book("22d580fc-d02e-4f70-9980-f9693c18f6e0", "dolore aliqua sint ipsum laboris",
                1));
        books.add(new Book("b6e8c865-2221-4435-9a65-d30ca0a63701",
            "ad laborum pariatur consequat commodo", 1));
        return books;
    }
}