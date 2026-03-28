package com.mangope.mangopebakeryandfastfood.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String item;
    private Integer size;
    private String phone;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getItem() { return item; }
    public void setItem(String item) { this.item = item; }

    public int getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}