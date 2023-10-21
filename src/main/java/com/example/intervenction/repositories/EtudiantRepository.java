package com.example.intervenction.repositories;

import com.example.intervenction.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository  extends JpaRepository<Etudiant, Long> {
}
