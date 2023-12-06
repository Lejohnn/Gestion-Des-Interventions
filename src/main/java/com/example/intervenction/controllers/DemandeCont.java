package com.example.intervenction.controllers;


import com.example.intervenction.entities.Categorie;
import com.example.intervenction.entities.Demande;
import com.example.intervenction.entities.SousCategorie;
import com.example.intervenction.services.DemandeServ;
import com.example.intervenction.services.SouscategorieServ;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@CrossOrigin("*")
public class DemandeCont {
    DemandeServ demandeServ;
    SouscategorieServ souscategorieServ;

    public DemandeCont(SouscategorieServ souscategorieServ, DemandeServ demandeServ) {
        this.demandeServ = demandeServ;
        this.souscategorieServ = souscategorieServ;
    }



    @RequestMapping(value = {"/demande"})
    public ModelAndView viewDemandePage(Model model) {
        List<Demande> demandeList = demandeServ.getAll();
        List<SousCategorie> sousCategorieList = souscategorieServ.getAll();

        model.addAttribute("sousCategorieList", sousCategorieList);
        model.addAttribute("demandeList", demandeList);
        model.addAttribute("demande", new Demande());
        return new ModelAndView("demande");
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


    @PutMapping("/prisEnchargeDem/{personnel_id}/{demande_id}")
    public String prisEnchargeDem(@PathVariable(value = "personnel_id") Long personnel_id, @PathVariable(value = "demande_id") Long demande_id){
        return demandeServ.prise_en_charge(personnel_id, demande_id);
    }


    @PutMapping("/rejetterDem/{personnel_id}/{demande_id}")
    public String rejetterDem(@PathVariable(value = "personnel_id") Long personnel_id, @PathVariable(value = "demande_id") Long demande_id){
        return demandeServ.rejetter(personnel_id, demande_id);
    }


    @PutMapping("/approuverDem/{personnel_id}/{demande_id}")
    public String approuverDem(@PathVariable(value = "personnel_id") Long personnel_id, @PathVariable(value = "demande_id") Long demande_id){
        return demandeServ.approuver(personnel_id, demande_id);
    }

}
