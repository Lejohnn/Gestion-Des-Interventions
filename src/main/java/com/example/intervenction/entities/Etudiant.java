package com.example.intervenction.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
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
public class Etudiant extends Utilisateur{
    private String matricule;

    @OneToMany(mappedBy = "etudiant")
    private List<Demande> demandes;
}
