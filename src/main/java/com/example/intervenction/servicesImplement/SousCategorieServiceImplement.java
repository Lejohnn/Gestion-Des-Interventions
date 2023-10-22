package com.example.intervenction.servicesImplement;

import com.example.intervenction.entities.Categorie;
import com.example.intervenction.entities.SousCategorie;
import com.example.intervenction.repositories.CategoreRepository;
import com.example.intervenction.repositories.SousCategorieRepository;
import com.example.intervenction.services.SousCategorieService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SousCategorieServiceImplement implements SousCategorieService {

    CategoreRepository categoreRepository;
    SousCategorieRepository sousCategorieRepository;
    private String response = "";

    public SousCategorieServiceImplement(SousCategorieRepository sousCategorieRepository, CategoreRepository categoreRepository) {
        this.sousCategorieRepository = sousCategorieRepository;
        this.categoreRepository = categoreRepository;
    }


    @Override
    public List<SousCategorie> getAllSousCategories() {
        List<SousCategorie> sousCategorieList = new ArrayList<>();
        sousCategorieRepository.findAll().forEach(sousCategorieList::add);
        return sousCategorieList;
    }

    @Override
    public SousCategorie oneSousCategorie(Long id) {
        return sousCategorieRepository.findById(id).get();
    }

    @Override
    public String addSousCategorie(SousCategorie sousCategorie, Long id_categorie) {
        Optional<Categorie> categorieOptional = categoreRepository.findById(id_categorie);
        if (categorieOptional.isPresent()) {
            Categorie categorie = categorieOptional.get();
            sousCategorie.setCategorie(categorie);
            sousCategorieRepository.save(sousCategorie);
            return this.response = "La sous catégorie " + sousCategorie.getLibelle() + " a bien été enregistrée";
        } else {
            throw new IllegalArgumentException("Catégorie introuvable, id_catégorie : " + id_categorie);
        }
    }

    @Override
    public String updateSousCategorie(Long id_categorie, Long id_sous_categorie, SousCategorie sousCategorie) {
        Optional<Categorie> categorieOptional = categoreRepository.findById(id_categorie);
        if (categorieOptional.isPresent()) {
            Categorie categorie = categorieOptional.get();
            sousCategorie.setCategorie(categorie);

            Optional<SousCategorie> sousCategorieOptional = sousCategorieRepository.findById(id_sous_categorie);
            if (sousCategorieOptional.isPresent()) {
                SousCategorie updatedsousCategorie = sousCategorieOptional.get();
                updatedsousCategorie.setLibelle(sousCategorie.getLibelle());
                updatedsousCategorie.setSigle(sousCategorie.getSigle());
                updatedsousCategorie.setDescription(sousCategorie.getDescription());
                updatedsousCategorie.setCategorie(sousCategorie.getCategorie());
                sousCategorieRepository.save(updatedsousCategorie);
                return this.response = "La sous catégorie " + updatedsousCategorie.getLibelle() + " a été modifiée avec succès";
            }
            else {
                throw new IllegalArgumentException("La sous categorie recherchée est introuvable, ID: " + id_sous_categorie);
            }
        } else {
            throw new IllegalArgumentException("La categorie recherchée est introuvable, ID: " + id_categorie);
        }
    }


    @Override
    public String enableSousCategorie(Long id) {
        Optional<SousCategorie> sousCategorieOptional = sousCategorieRepository.findById(id);
        if (sousCategorieOptional.isPresent()) {
            SousCategorie sousCategorie = sousCategorieOptional.get();
            sousCategorie.setStatut("actif");
            sousCategorieRepository.save(sousCategorie);
            return this.response = "La sous catégorie " + sousCategorie.getLibelle() + " a été réactivée avec succès";
        } else {
            throw new IllegalArgumentException("La sous catégorie est introuvable id_sous_catégorie : " + id);
        }
    }

    @Override
    public String disableSousCategorie(Long id) {
        Optional<SousCategorie> sousCategorieOptional = sousCategorieRepository.findById(id);
        if (sousCategorieOptional.isPresent()) {
            SousCategorie sousCategorie = sousCategorieOptional.get();
            sousCategorie.setStatut("inactif");
            sousCategorieRepository.save(sousCategorie);
            return this.response = "La sous catégorie " + sousCategorie.getLibelle() + " a été désactivée avec succès";
        } else {
            throw new IllegalArgumentException("La sous catégorie est introuvable id_sous_catégorie : " + id);
        }
    }
}
