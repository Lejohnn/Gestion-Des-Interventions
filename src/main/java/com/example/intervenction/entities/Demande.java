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
@Table(name = "demande")
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_demande;

    @Column(nullable = false)
    private String objet;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String statut;

    @ManyToOne
    @JoinColumn(name="id_etudiant", nullable=false)
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name="sous_categorie", nullable=false)
    private SousCategorie sousCategorie;

    @ManyToOne
    @JoinColumn(name="id_personnel")
    private Personnel personnel;
}
