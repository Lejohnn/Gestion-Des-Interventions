package com.example.intervenction.controllers;


import com.example.intervenction.entities.Categorie;
import com.example.intervenction.entities.Demande;
import com.example.intervenction.entities.SousCategorie;
import com.example.intervenction.services.DemandeServ;
import com.example.intervenction.services.SouscategorieServ;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class DemandeCont {
    DemandeServ demandeServ;

    public DemandeCont(DemandeServ demandeServ) {
        this.demandeServ = demandeServ;
    }

    @GetMapping("/allDem")
    public List<Demande> allDem(){
        List<Demande> categorieList = demandeServ.getAll();
        return categorieList;
    }


    @GetMapping("/oneDem/{id}")
    public Demande oneDem(@PathVariable(value = "id") Long id){
        Demande categorie = demandeServ.getOne(id);
        return categorie;
    }


    @PostMapping("/addDem/{etudiant_id}/{sous_categorie_id}")
    public String addDem(@PathVariable(value = "etudiant_id") Long etudiant_id, @PathVariable(value = "sous_categorie_id") Long sous_categorie_id, @RequestBody Demande demande){
        return demandeServ.send(etudiant_id, sous_categorie_id, demande);
    }
}
