package com.mangope.mangopebakeryandfastfood;

import com.mangope.mangopebakeryandfastfood.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @Test
    void shouldSubmitOrderSuccessfully() throws Exception {

        mockMvc.perform(post("/submitOrder")
                        .param("name", "Karabo")
                        .param("phone", "0685962483")
                        .param("orderDetails", "Plain Scones 10L x2"))
                .andExpect(status().isOk())
                .andExpect(view().name("confirmation"));
    }

    @Test
    void shouldRejectEmptyOrder() throws Exception {

        mockMvc.perform(post("/submitOrder")
                        .param("name", "Karabo")
                        .param("phone", "0685962483")
                        .param("orderDetails", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("order"))
                .andExpect(model().attributeExists("error"));
    }

}