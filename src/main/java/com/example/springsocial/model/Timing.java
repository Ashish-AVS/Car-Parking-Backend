package com.example.springsocial.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @Entity @NoArgsConstructor @AllArgsConstructor
public class Timing {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "is_available")
    boolean isAvailable;

}
