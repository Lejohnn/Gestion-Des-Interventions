package com.example.intervenction.controllers;

import com.example.intervenction.entities.Categorie;
import com.example.intervenction.services.CategorieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin("*")

public class CategorieController {
    CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping("/getAllCategories")
    public List<Categorie> getAllCategories(){
        List<Categorie> categorieList = categorieService.getAllCategories();
        return categorieList;
    }

    @GetMapping("/oneCategorie/{id}")
    public Categorie oneCategorie(@PathVariable(value = "id") Long id){
        Categorie categorie = categorieService.oneCategorie(id);
        return categorie;
    }

    @PostMapping("/addCategorie")
    public String addCategorie(@RequestBody Categorie categorie){
        String savedCategorie = categorieService.addCategorie(categorie);
        return savedCategorie;
    }

    @PutMapping("/updateCategorie/{id}")
    public String updateCategorie(@PathVariable(value = "id") Long id, @RequestBody Categorie categorie){
        String updatedCategorie = categorieService.updateCategorie(id, categorie);
        return updatedCategorie;
    }

    @PutMapping("/enableCategorie/{id}")
    public String enableCategorie(@PathVariable(value = "id") Long id){
        String enabledCategorie = categorieService.enableCategorie(id);
        return enabledCategorie;
    }

    @PutMapping("/disableCategorie/{id}")
    public String disableCategorie(@PathVariable(value = "id") Long id){
        String disabledCategorie = categorieService.disableCategorie(id);
        return disabledCategorie;
    }
}
