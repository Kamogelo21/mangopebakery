package com.mangope.mangopebakeryandfastfood.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class EmailServiceProd implements EmailService {

    public void sendEmail(String to, String subject, String body) {
        // do nothing (or log)
        System.out.println("Email disabled in production");
    }
}
