package com.mangope.mangopebakeryandfastfood.controller;

import com.mangope.mangopebakeryandfastfood.service.OrderService;
import org.springframework.ui.Model;
import com.mangope.mangopebakeryandfastfood.model.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    private OrderService orderService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }

    @GetMapping("/order")
    public String order() {
        return "order";
    }

    @PostMapping("/submitOrder")
    public String submitOrder(Order order, Model model) {
        orderService.saveOrder(order);
        model.addAttribute("order", order);
        return "confirmation";
    }

    @GetMapping("/admin")
    public String viewOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin";
    }
}
