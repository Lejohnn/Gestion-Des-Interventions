package com.example.intervenction.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Data
@Table(name = "etudiants")
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String email;
    private String telephone;
    private String login;
    private String password;
    private String statut;
    private String matricule;


    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;
}
