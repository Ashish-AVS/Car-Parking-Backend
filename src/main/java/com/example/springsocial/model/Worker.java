package com.example.springsocial.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data @Entity @NoArgsConstructor @AllArgsConstructor @ToString
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @NotNull
    private String workerName;
    @NotNull
    private String email;
    @NotNull
    private String phoneNumber;
    private Double rating;

    @OneToMany(targetEntity = Specialisation.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "worker_specialisation", referencedColumnName = "id")
    private List<Specialisation> specialisations = new ArrayList<>();
}
