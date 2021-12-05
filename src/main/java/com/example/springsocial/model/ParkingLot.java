package com.example.springsocial.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class ParkingLot {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String City;
    private String Location; // Ex: Inorbit Mall, Big Bazaar
    private Integer Cost;


    // List of all parking slots in a parking Lot
    @OneToMany(targetEntity = ParkingSlot.class, cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_slot", referencedColumnName = "id")
    private List<ParkingSlot> parkingSlotList = new ArrayList<>();
    // 100 Slots in Each Parking Lot
    @OneToMany(targetEntity = Worker.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_worker", referencedColumnName = "id")
    private List<Worker> workerList = new ArrayList<>();
    // ADMIN CAN ADD THE WORKERS, BUT THE WORKERS CAN CHANGE THEIR SPECIALISATION
}
