package com.example.intervenction.services;

import com.example.intervenction.entities.Etudiant;

import java.util.List;

public interface EtudiantService {

    List<Etudiant> all();

    Etudiant un(Long id);

    String plus(Etudiant etudiant);

    String update(Long id, Etudiant etudiant);

    String on(Long id);

    String off(Long id);

    String clean(Long id);
}
