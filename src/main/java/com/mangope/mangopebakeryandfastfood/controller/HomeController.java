package com.mangope.mangopebakeryandfastfood.controller;

import com.mangope.mangopebakeryandfastfood.model.Order;
import com.mangope.mangopebakeryandfastfood.service.EmailService;
import com.mangope.mangopebakeryandfastfood.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String submitOrder(
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam String orderDetails,
            Model model
    ) {

        // Basic validation
        if (orderDetails == null || orderDetails.isBlank()) {
            model.addAttribute("error", "Please add at least one item to your order.");
            return "order";
        }

        // Save order
        Order order = orderService.saveOrder(name, phone, orderDetails);

        model.addAttribute("order", order);

        // Email
        String to = "ashleymotapane1@outlook.com";

        String text = "🧾 New Order\n\n" +
                "Customer: " + name + "\n" +
                "Phone: " + phone + "\n\n" +
                "Items:\n" +
                "----------------------\n" +
                orderDetails +
                "\n----------------------";

        try {
            emailService.sendOrderEmail(to, "New Order Received!", text);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "confirmation";
    }

    @GetMapping("/admin")
    public String viewOrders(@RequestParam(required = false) String key, Model model) {

        if (!"mangope123".equals(key)) {
            return "redirect:/";
        }

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

            String cleanItems = order.getItem()
                    .replace("\n", " | ")   // replace line breaks
                    .replace(",", " ");     // avoid breaking CSV

            writer.println(
                    order.getName() + "," +
                            "\"" + cleanItems + "\"," +
                            order.getPhone()
            );
        }

        writer.flush();
        writer.close();
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    private String formatItemName(String raw) {

        // Convert camelCase → spaced words
        String result = raw.replaceAll("([A-Z])", " $1").trim();

        // Capitalize first letter
        return result.substring(0, 1).toUpperCase() + result.substring(1);
    }
}