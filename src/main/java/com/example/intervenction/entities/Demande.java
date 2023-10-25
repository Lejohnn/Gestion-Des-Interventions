package com.example.intervenction.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Data
@Table(name = "demandes")
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String statut;

    @ManyToOne
    @JoinColumn(name = "sous_categorie_id")
    private SousCategorie sousCategorie;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "demande")
    private List<PieceJointe> piecesJointes;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "personnel_id", columnDefinition = "bigint default 0")
    private Personnel personnel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "demande")
    private List<Mail> mails;
}
