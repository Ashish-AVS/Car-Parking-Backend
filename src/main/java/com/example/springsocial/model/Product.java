package com.example.springsocial.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity @Data
public class Product {
    @Id
    @Column(name = "id", nullable = false)
    private Long Id;
    private String productName;
    private int quantity;
    private int price;

}
