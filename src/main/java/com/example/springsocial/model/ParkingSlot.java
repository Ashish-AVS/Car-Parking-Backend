package com.example.springsocial.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class ParkingSlot {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // ASCII of 0 is 48
    // TILL H IS 0 TO 23 => TODAY
    // TILL u is 23 => TOMMORROW
    // TILL m in 23 => Day after
//    0123456789:;<=>?@ABCDEFH IJKLMNOPQRSTUVWXYZ[/]^_` abcdefghijklmnopqrstuvwx
    private String hours = "0123456789:;<=>?@ABCDEFHIJKLMNOPQRSTUVWXYZ[/]^_`abcdefghijklmnopqrstuvwx"; // This denotes hours from 0 to 23
    // INITIALISE ALL HOURS' isAvailable to TRUE

//    @OneToMany(targetEntity = Timing.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "slot_timing", referencedColumnName = "id")
//    private List<Timing> isAvailable = new ArrayList<>();
    // All slots in a parking lot will have same costs,
    // Workers will be assigned to a parking lot
}
