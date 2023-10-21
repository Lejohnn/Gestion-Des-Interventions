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
@Table(name = "utilisateur")
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name="personne_type", discriminatorType = DiscriminatorType.INTEGER )
//@DiscriminatorValue("0")

public abstract class Utilisateurs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_utilisateur;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = true)
    private String prenom;

    @Column(unique = true)
    private String telephone;

    @Column(unique = true)
    private String login;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String mot_passe;

    @Column(nullable = false)
    private String statut; // actif ou inactif

    @Column(nullable = false)
    private String grade; // etudiant ou personnel

}
