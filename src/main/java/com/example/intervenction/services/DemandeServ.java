package com.example.intervenction.services;

import com.example.intervenction.entities.Demande;

import java.util.List;

public interface DemandeServ {
    List<Demande> getAll();

    Demande getOne(Long id);

    String send(Long etudiant_id, Long sous_categorie_id, Demande demande);

    String prise_en_charge(Long personnel_id, Long id);

    String approuver(Long personnel_id, Long id);

    String rejetter(Long personnel_id, Long id);
}
