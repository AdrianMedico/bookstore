package com.adobe.bookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.adobe.bookstore.models.Order;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class OrdersRepositoryTest {

    @Autowired
    private OrdersRepository ordersRepository;

    @Test
    void repositoryIsNotNull() {
        assertThat(ordersRepository).isNotNull();
    }

    @Test
    void ordersRepositoryShouldReturnAllOrders() {
        Order orderMock = new Order();
        // given
        ordersRepository.save(orderMock);

        // when
        List<Order> ordersFromDB = ordersRepository.findAll();

        // then
        assertThat(ordersFromDB.isEmpty()).isFalse();
    }

    @Test
    void repositoryShouldSaveOrder() {
        // given
        Order orderMock = new Order();

        //when
        ordersRepository.save(orderMock);

        // then
        List<Order> ordersFromDB = ordersRepository.findAll();
        assertThat(ordersFromDB.stream().anyMatch(order -> order.getId().equals(orderMock.getId())))
            .isTrue();
    }

}
