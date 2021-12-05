package com.example.springsocial.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;

@Entity @Data @NoArgsConstructor
@AllArgsConstructor
@ToString
public class Waitlist {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long Id;

    private Long lotId;
}
