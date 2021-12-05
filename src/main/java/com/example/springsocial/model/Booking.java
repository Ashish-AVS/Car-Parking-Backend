package com.example.springsocial.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Long slotId;
    @NotNull
    private Long lotId;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalDate dateOfBooking;
    @NotNull
    private String hours;
    @NotNull
    private Integer cost;
    @NotNull
    private String services;
}
