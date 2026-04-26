package com.mangope.mangopebakeryandfastfood.service;

public interface EmailService {
    void sendOrderEmail(String to, String subject, String body);
}