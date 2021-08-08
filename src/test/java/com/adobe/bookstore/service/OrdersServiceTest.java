package com.adobe.bookstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;

import com.adobe.bookstore.models.Order;
import com.adobe.bookstore.repository.OrdersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;

class OrdersServiceTest {

    private OrdersRepository repository = Mockito.mock(OrdersRepository.class);

    private OrdersService ordersService;

    @BeforeEach
    void setUp() {
        ordersService = new OrdersServiceImpl(repository);

    }

    @Test
    void createOrderShouldCreateAndSaveOrder() {
        // given
        Order mockOrder = new Order();
        given(repository.save(Mockito.any(Order.class))).willReturn(mockOrder);

        // when
        Order order = ordersService.createOrder();

        // then
        assertThat(order.getId()).isEqualTo(mockOrder.getId());
        verify(repository, times(1)).save(any(Order.class));
    }

    @Test
    void getJsonOrdersShouldReturnFormattedJson() throws JsonProcessingException, JSONException {
        // given
        Order order = new Order();
        given(repository.findAll()).willReturn(new ArrayList(Arrays.asList(order)));

        // when
        String json = ordersService.getJsonOrders();

        // then
        JSONAssert.assertEquals("[ \""+order.getId()+"\" ]", json, true);
    }

    @Test
    void getCSVOrders() {
        // given
        given(repository.findAll()).willReturn(new ArrayList(Arrays.asList(new Order(), new Order())));

        // when
        String csv = ordersService.getCSVOrders();

        // then
        assertThat(csv).contains(",");
    }


}