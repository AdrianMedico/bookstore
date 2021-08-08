package com.adobe.bookstore.controllers;


import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.adobe.bookstore.models.Book;
import com.adobe.bookstore.service.BooksServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = BooksController.class)
class BooksControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BooksServiceImpl booksService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void buyOrderShouldReturnAcceptWhenValidInput() throws Exception {
        List<Book> books = mockedBooksList();

        mockMvc
            .perform(post("/books").contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(books))) // verify input serialization
            .andExpect(status().isAccepted()); // verify http status is Accepted

        ArgumentCaptor<List<Book>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(booksService, times(1)).processBooksOrder(argumentCaptor.capture()); // verify service layer call

        // verify that param List<Book> is passed correctly to processBooksOrder()
        assertThat(argumentCaptor.getValue().size()).isEqualTo(2);
        assertThat(argumentCaptor.getValue().get(0).getId())
            .isEqualTo("22d580fc-d02e-4f70-9980-f9693c18f6e0");
        assertThat(argumentCaptor.getValue().get(0).getName())
            .isEqualTo("dolore aliqua sint ipsum laboris");
        assertThat(argumentCaptor.getValue().get(0).getQuantity()).isEqualTo(1);
    }

    private List<Book> mockedBooksList() {
        List<Book> books = new ArrayList<>();
        books.add(
            new Book("22d580fc-d02e-4f70-9980-f9693c18f6e0", "dolore aliqua sint ipsum laboris",
                1));
        books.add(new Book("b6e8c865-2221-4435-9a65-d30ca0a63701",
            "ad laborum pariatur consequat commodo", 2));
        return books;
    }
}