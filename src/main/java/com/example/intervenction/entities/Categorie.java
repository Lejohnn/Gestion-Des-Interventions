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
@Table(name = "categorie")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_categorie;
    private String libelle;
    private String description;
    private String statut;

    @ManyToOne
    @JoinColumn(name="id_service", nullable=false)
    private Service service;
}
