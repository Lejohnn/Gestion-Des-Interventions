package com.example.intervenction.services;

import com.example.intervenction.entities.Departement;

import java.util.List;

public interface DepartementServ {
    List<Departement> getAll();

    Departement getOne(Long id);

    String add(Departement departement);

    String updte(Long id, Departement departement);

}
