package com.adobe.bookstore.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.adobe.bookstore.models.Book;
import com.adobe.bookstore.models.Order;
import com.adobe.bookstore.repository.OrdersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ReturnAllOrdersUseCaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrdersRepository ordersRepository;


    @Test
    void returnAllOrdersInJsonWorksThroughAllLayers() throws Exception {
        List<Book> books = mockedBooksList();

        MvcResult mvcResult = mockMvc
            .perform(get("/orders").param("format", "non-csv")
                .content(objectMapper.writeValueAsString(books)))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        List<Order> order = ordersRepository.findAll();

        assertThat(actualResponseBody).containsIgnoringCase(order.get(0).getId());
    }

    @Test
    void returnAllOrdersInCSVWorksThroughAllLayers() throws Exception {
        List<Book> books = mockedBooksList();

        MvcResult mvcResult = mockMvc
            .perform(get("/orders").param("format", "text/csv")
                .content(objectMapper.writeValueAsString(books)))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.parseMediaType("text/csv")))
            .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        List<Order> order = ordersRepository.findAll();

        assertThat(actualResponseBody).containsIgnoringCase(order.get(0).getId()).contains(",");
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
