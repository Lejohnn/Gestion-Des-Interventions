package com.example.intervenction.controllers;


import com.example.intervenction.entities.Categorie;
import com.example.intervenction.entities.SousCategorie;
import com.example.intervenction.services.SousCategorieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class SousCategorieController {
    SousCategorieService sousCategorieService;

    private String response = "";


    public SousCategorieController(SousCategorieService sousCategorieService) {
        this.sousCategorieService = sousCategorieService;
    }


    @GetMapping("/getAllSousCategories")
    public List<SousCategorie> getAllSouscategories(){
        List<SousCategorie> SouscategorieList = sousCategorieService.getAllSousCategories();
        return SouscategorieList;
    }


    @GetMapping("/oneSousCategorie/{id}")
    public SousCategorie oneSousCategorie(@PathVariable(value = "id") Long id){
        SousCategorie sousCategorie = sousCategorieService.oneSousCategorie(id);
        return sousCategorie;
    }


   @PostMapping("/addSousCategorie/{id_categorie}")
    public String addSousCategorie(@PathVariable long id_categorie, @RequestBody SousCategorie sousCategorie){
        this.response = sousCategorieService.addSousCategorie(sousCategorie, id_categorie);
        return this.response;
    }


    @PutMapping("/updateSousCategorie/{id_categorie}/{id_sous_categorie}")
    public String updateSousCategorie(@PathVariable Long id_categorie, @PathVariable Long id_sous_categorie, @RequestBody SousCategorie sousCategorie){
        String updatedCategorie = sousCategorieService.updateSousCategorie(id_categorie, id_sous_categorie, sousCategorie);
        return updatedCategorie;
    }


    @PutMapping("/enableSousCategorie/{id}")
    public String enableSousCategorie(@PathVariable(value = "id") Long id){
        String enabledCategorie = sousCategorieService.enableSousCategorie(id);
        return enabledCategorie;
    }


    @PutMapping("/disableSousCategorie/{id}")
    public String disableSousCategorie(@PathVariable(value = "id") Long id){
        String disabledCategorie = sousCategorieService.disableSousCategorie(id);
        return disabledCategorie;
    }

}
