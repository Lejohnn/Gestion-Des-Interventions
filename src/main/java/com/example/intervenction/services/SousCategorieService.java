package com.example.intervenction.services;

import com.example.intervenction.entities.Categorie;
import com.example.intervenction.entities.SousCategorie;

import java.util.List;

public interface SousCategorieService {
    List<SousCategorie> getAllSousCategories();

    SousCategorie oneSousCategorie(Long id);

    String addSousCategorie(SousCategorie sousCategorie, Long id_categorie);

    String updateSousCategorie(Long id_categorie, Long id_sous_categorie, SousCategorie sousCategorie);

    String enableSousCategorie(Long id);

    String disableSousCategorie(Long id);
}
