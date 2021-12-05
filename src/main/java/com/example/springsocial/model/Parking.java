package com.example.springsocial.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// REDUNDANT
@Entity @NoArgsConstructor @AllArgsConstructor @Data
public class Parking {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToMany(targetEntity = ParkingLot.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "parking", referencedColumnName = "id")
    private List<ParkingLot> parkingLots = new ArrayList<>();
}
