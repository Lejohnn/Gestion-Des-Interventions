package com.example.intervenction.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Data
@Table(name = "souscategorie")

public class SousCategorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_sous_categorie;
    private String libelle;
    private String sigle;
    private String description;
    private String statut;

    @ManyToOne
    @JoinColumn(name="id_categorie", nullable=false)
    private Categorie categorie;
}
