package com.example.intervenction.services;

import com.example.intervenction.entities.Categorie;
import com.example.intervenction.repositories.CategoreRepository;

import java.util.List;

public interface CategorieService {
    List<Categorie> getAllCategories();

    Categorie oneCategorie(Long id);

    String addCategorie(Categorie categorie);

    String updateCategorie(Long id, Categorie categorie);

    String enableCategorie(Long id);

    String disableCategorie(Long id);
}
