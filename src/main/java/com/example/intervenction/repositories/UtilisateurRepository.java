package com.example.intervenction.repositories;

import com.example.intervenction.entities.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository <Utilisateurs, Long> {
}
