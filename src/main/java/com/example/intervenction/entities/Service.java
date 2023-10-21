package com.example.intervenction.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Data
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_service;
    private String libelle;
    private String description;
    private String statut;

    @OneToMany( targetEntity=Mail.class, mappedBy="service" )
    private List<Mail> mailList = new ArrayList<>();

    @OneToMany( targetEntity=Personnel.class, mappedBy="service" )
    private List<Personnel> personnelList = new ArrayList<>();
}
