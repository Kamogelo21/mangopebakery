package com.mangope.mangopebakeryandfastfood.service;

import com.mangope.mangopebakeryandfastfood.model.Order;
import com.mangope.mangopebakeryandfastfood.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order saveOrder(String name, String phone, String orderDetails) {

        if (!hasItems(orderDetails)) {
            throw new IllegalArgumentException("At least one item must be ordered");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }

        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone number is required");
        }

        if (orderDetails == null || orderDetails.isBlank()) {
            throw new IllegalArgumentException("At least one item must be ordered");
        }

        // ✅ Create Order
        Order order = new Order();
        order.setName(name);
        order.setPhone(phone);
        order.setItem(orderDetails);
        order.setSize(0); // deprecated field

        return orderRepository.save(order);
    }

    public boolean hasItems(String orderDetails) {
        return orderDetails != null && !orderDetails.isBlank();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}