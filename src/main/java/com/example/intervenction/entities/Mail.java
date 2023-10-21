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
@Table(name = "mail")
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_mail;

    @Column(nullable = false)
    private String objet;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String statut; // envoyé ou non-envoyé

    @ManyToOne
    @JoinColumn(name="id_utilisateur", nullable=false)
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name="id_service", nullable=false)
    private Service service;
}
