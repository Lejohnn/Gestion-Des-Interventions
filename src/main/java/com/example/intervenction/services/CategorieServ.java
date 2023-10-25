package com.example.intervenction.services;

import com.example.intervenction.entities.Categorie;

import java.util.List;

public interface CategorieServ {
    List<Categorie> getAll();

    Categorie getOne(Long id);

    String add(Categorie categorie);

    String update(Long id, Categorie categorie);

    void clean(Long id);
}
