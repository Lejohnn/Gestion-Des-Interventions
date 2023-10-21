package com.example.intervenction.repositories;


import com.example.intervenction.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoreRepository extends JpaRepository<Categorie, Long> {
}
