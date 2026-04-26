package com.mangope.mangopebakeryandfastfood.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class EmailServiceProd implements EmailService {

    @Override
    public void sendOrderEmail(String to, String subject, String body) {
        // no email in production
        System.out.println("Email disabled in production");
    }
}
