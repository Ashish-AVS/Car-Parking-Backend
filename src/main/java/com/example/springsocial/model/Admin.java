package com.example.springsocial.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Admin {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "total", nullable = false)
    private Long amount;
}
