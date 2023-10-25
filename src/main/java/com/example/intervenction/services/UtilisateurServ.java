package com.example.intervenction.services;

import com.example.intervenction.entities.SousCategorie;
import com.example.intervenction.entities.Utilisateur;

import java.util.List;

public interface UtilisateurServ {
    Utilisateur findByLoginPassword(String login, String password);
}
