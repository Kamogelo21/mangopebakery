package com.mangope.mangopebakeryandfastfood;

import com.mangope.mangopebakeryandfastfood.model.Order;
import com.mangope.mangopebakeryandfastfood.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    void shouldSaveOrderSuccessfully() {
        Order order = orderService.saveOrder(
                "Karabo",
                "0685962483",
                "Plain Scones 10L x2"
        );

        assertNotNull(order.getId());
        assertEquals("Karabo", order.getName());
        assertEquals("0685962483", order.getPhone());
        assertTrue(order.getItem().contains("Plain Scones"));
    }

    @Test
    void shouldReturnAllOrders() {
        orderService.saveOrder("Test1", "111", "Item1");
        orderService.saveOrder("Test2", "222", "Item2");

        List<Order> orders = orderService.getAllOrders();

        assertTrue(orders.size() >= 2);
    }
}