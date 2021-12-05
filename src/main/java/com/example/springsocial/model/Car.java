package com.example.springsocial.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Car {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "car_name", nullable = false)
    private String carName;
    @Column(name = "car_model", nullable = false)
    private String carModel;
    @Column(name = "car_number", nullable = false)
    private String carNumber;
}
