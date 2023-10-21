package com.example.intervenction.servicesImplement;

import com.example.intervenction.entities.Categorie;
import com.example.intervenction.repositories.CategoreRepository;
import com.example.intervenction.services.CategorieService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategorieServiceImplement implements CategorieService {

    CategoreRepository categoreRepository;

    private String response = "";

    public CategorieServiceImplement(CategoreRepository categoreRepository) {
        this.categoreRepository = categoreRepository;
    }

    @Override
    public List<Categorie> getAllCategories() {
        List<Categorie> categorieList = new ArrayList<>();
        categoreRepository.findAll().forEach(categorieList::add);
        return categorieList;
    }

    @Override
    public Categorie oneCategorie(Long id) {
        return categoreRepository.findById(id).get();
    }

    @Override
    public String addCategorie(Categorie categorie) {
        final Categorie save = categoreRepository.save(categorie);
        if (save != null) {
            return this.response = "La catégorie " + save.getLibelle() + " à bien été enregistrée";
        }else {
            return this.response = "La catégorie " + save.getLibelle() + " n'a pas été enregistrée";
        }
    }

    @Override
    public String updateCategorie(Long id, Categorie categorie) {
        Categorie updateCategorie = categoreRepository.findById(id).get();
        System.out.println(updateCategorie.toString());
        updateCategorie.setLibelle(categorie.getLibelle());
        updateCategorie.setDescription(categorie.getDescription());
        Categorie update = categoreRepository.save(updateCategorie);
        if (update != null) {
            return this.response = "La catégorie " + updateCategorie.getLibelle() + " a été modifiée avec succès";
        }else {
            return this.response = "La catégorie " + categorie.getLibelle() + " n'a pas été modifiée";
        }
    }

    @Override
    public String enableCategorie(Long id) {
        Categorie updateCategorie = categoreRepository.findById(id).get();
        System.out.println(updateCategorie.toString());
        updateCategorie.setStatut("actif");
        categoreRepository.save(updateCategorie);
        return this.response = "La catégorie " + updateCategorie.getLibelle() + " a été réactivée avec succès";
    }

    @Override
    public String disableCategorie(Long id) {
        Categorie updateCategorie = categoreRepository.findById(id).get();
        System.out.println(updateCategorie.toString());
        updateCategorie.setStatut("inactif");
        categoreRepository.save(updateCategorie);
        return this.response = "La catégorie " + updateCategorie.getLibelle() + " a été désactivée avec succès";
    }
}
