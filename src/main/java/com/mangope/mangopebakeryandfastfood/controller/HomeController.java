package com.mangope.mangopebakeryandfastfood.controller;

import com.mangope.mangopebakeryandfastfood.model.Order;
import com.mangope.mangopebakeryandfastfood.service.EmailService;
import com.mangope.mangopebakeryandfastfood.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    private final OrderService orderService;
    private final EmailService emailService;

    public HomeController(OrderService orderService, EmailService emailService) {
        this.orderService = orderService;
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }

    // Display order form
    @GetMapping("/order")
    public String showOrderForm(Model model) {
        Order order = new Order();
        order.setSize(5); //
        model.addAttribute("order", order);
        return "order";
    }

    // Handle order submission
    @PostMapping("/submitOrder")
    public String submitOrder(Order order, Model model) {
        orderService.saveOrder(order);
        model.addAttribute("order", order);

        // Send email
        String to = "vameno22@outlook.com";
        String subject = "New Order Received!";
        String text = "Order Details:\n" +
                "Name: " + order.getName() + "\n" +
                "Item: " + order.getItem() + "\n" +
                "Size: " + order.getSize() + "L" + "\n" +
                "Phone: " + order.getPhone();

        try {
            emailService.sendOrderEmail(to, subject, text);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "confirmation";
    }

    @GetMapping("/admin")
    public String viewOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin";
    }

    @GetMapping("/test-db")
    @ResponseBody
    public String testDB() {
        return orderService.getAllOrders().toString();
    }

    @GetMapping("/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=orders.csv");

        List<Order> orders = orderService.getAllOrders();

        PrintWriter writer = response.getWriter();

        // Header
        writer.println("Name,Item,Size,Phone");

        // Data
        for (Order order : orders) {
            writer.println(
                    order.getName() + "," +
                            order.getItem() + "," +
                            order.getSize() + "L" +
                            order.getPhone()
            );
        }

        writer.flush();
        writer.close();
    }
}