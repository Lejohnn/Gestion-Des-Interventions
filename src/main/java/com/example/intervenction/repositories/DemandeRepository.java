package com.example.intervenction.repositories;

import com.example.intervenction.entities.Demande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeRepository  extends JpaRepository<Demande, Long> {
}
