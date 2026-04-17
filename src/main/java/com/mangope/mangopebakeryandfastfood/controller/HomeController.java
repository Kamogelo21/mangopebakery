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

            @RequestParam(required = false, defaultValue = "0") int plainSconesQty,
            @RequestParam(required = false, defaultValue = "0") int flavouredSconesQty,
            @RequestParam(required = false, defaultValue = "0") int queensCakesQty,
            @RequestParam(required = false, defaultValue = "0") int snowballsQty,
            @RequestParam(required = false, defaultValue = "0") int jamTartsQty,
            @RequestParam(required = false, defaultValue = "0") int meltingMomentsQty,
            @RequestParam(required = false, defaultValue = "0") int romanyCreamsQty,
            @RequestParam(required = false, defaultValue = "0") int gingerBiscuitsQty,
            @RequestParam(required = false, defaultValue = "0") int lemonCreamsQty,
            @RequestParam(required = false, defaultValue = "0") int lamingtonsQty,
            @RequestParam(required = false, defaultValue = "0") int eatsumoreQty,
            @RequestParam(required = false, defaultValue = "0") int assortedBiscuitsQty,

            Model model
    ) {

        StringBuilder orderDetails = new StringBuilder();

        if (plainSconesQty > 0) orderDetails.append("Plain Scones x").append(plainSconesQty).append("\n");
        if (flavouredSconesQty > 0) orderDetails.append("Flavoured Scones x").append(flavouredSconesQty).append("\n");
        if (queensCakesQty > 0) orderDetails.append("Queens Cakes x").append(queensCakesQty).append("\n");
        if (snowballsQty > 0) orderDetails.append("Snowballs x").append(snowballsQty).append("\n");
        if (jamTartsQty > 0) orderDetails.append("Jam Tarts x").append(jamTartsQty).append("\n");
        if (meltingMomentsQty > 0) orderDetails.append("Melting Moments x").append(meltingMomentsQty).append("\n");
        if (romanyCreamsQty > 0) orderDetails.append("Romany Creams x").append(romanyCreamsQty).append("\n");
        if (gingerBiscuitsQty > 0) orderDetails.append("Ginger Biscuits x").append(gingerBiscuitsQty).append("\n");
        if (lemonCreamsQty > 0) orderDetails.append("Lemon Creams x").append(lemonCreamsQty).append("\n");
        if (lamingtonsQty > 0) orderDetails.append("Lamingtons x").append(lamingtonsQty).append("\n");
        if (eatsumoreQty > 0) orderDetails.append("Eat-sumore x").append(eatsumoreQty).append("\n");
        if (assortedBiscuitsQty > 0) orderDetails.append("Assorted Biscuits x").append(assortedBiscuitsQty).append("\n");

        // Save order using service
        Order savedOrder = orderService.saveOrder(
                name,
                phone,
                orderDetails.toString().replaceAll("\n$", "")
        );

        // Add to model for confirmation page
        model.addAttribute("order", savedOrder);

        // Email
        String to = "ashleymotapane1@outlook.com";
        String subject = "New Order Received!";
        String text = "🧾 Order Details\n\n" +
                "Customer Name: " + name + "\n" +
                "Contact Number: " + phone + "\n\n" +
                "Ordered Items:\n" +
                "----------------------\n" +
                orderDetails.toString().trim() +
                "----------------------";

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
}