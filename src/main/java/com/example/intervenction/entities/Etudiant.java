package com.example.intervenction.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@ToString
@Getter
@Setter
@Data
@Table(name = "etudiant")
@DiscriminatorValue("2")

public class Etudiant extends Utilisateurs {

    public Etudiant(){
        super();
    }

    @Column(unique = true, nullable = false)
    private String matricule;

    @Column(nullable = false)
    private String classe;

    @Column(nullable = false)
    private String filiere;

    @OneToMany( targetEntity=Demande.class, mappedBy="etudiant" )
    private List<Demande> demandeList = new ArrayList<>();

    @OneToMany( targetEntity=Mail.class, mappedBy="etudiant" )
    private List<Mail> mailList = new ArrayList<>();

}
