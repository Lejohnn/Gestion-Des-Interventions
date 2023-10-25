package com.example.intervenction.controllers;


import com.example.intervenction.entities.Categorie;
import com.example.intervenction.services.CategorieServ;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class CategorieCont {
    CategorieServ categorieServ;
    public CategorieCont(CategorieServ categorieServ) {
        this.categorieServ = categorieServ;
    }

    @GetMapping("/allCat")
    public List<Categorie> allCat(){
        List<Categorie> categorieList = categorieServ.getAll();
        return categorieList;
    }

    @GetMapping("/oneCat/{id}")
    public Categorie oneCat(@PathVariable(value = "id") Long id){
        Categorie categorie = categorieServ.getOne(id);
        return categorie;
    }

    @PostMapping("/addCat")
    public String addCat(@RequestBody Categorie categorie){
        return categorieServ.add(categorie);
    }

    @PutMapping("/updateCat/{id}")
    public String updateCat(@PathVariable(value = "id") Long id, @RequestBody Categorie categorie){
        return categorieServ.update(id, categorie);
    }
}
