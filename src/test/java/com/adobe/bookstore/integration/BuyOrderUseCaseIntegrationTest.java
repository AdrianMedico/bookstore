package com.adobe.bookstore.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class BuyOrderUseCaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrdersRepository ordersRepository;

    @Test
    void buyOrderWorksThroughAllLayers() throws Exception {
        List<Book> books = mockedBooksList();

        MvcResult mvcResult = mockMvc
            .perform(post("/books").contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(books)))
            .andExpect(status().isAccepted())
            .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Order order = ordersRepository.findById(actualResponseBody).get();

        assertThat(actualResponseBody).hasSize(36); // uuids of 36 chars
        assertThat(order.getId()).isEqualTo(actualResponseBody);
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
