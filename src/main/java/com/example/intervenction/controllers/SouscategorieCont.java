package com.example.intervenction.controllers;

import com.example.intervenction.entities.Categorie;
import com.example.intervenction.entities.Departement;
import com.example.intervenction.entities.SousCategorie;
import com.example.intervenction.services.CategorieServ;
import com.example.intervenction.services.DepartementServ;
import com.example.intervenction.services.SouscategorieServ;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController
@CrossOrigin("*")
public class SouscategorieCont {
    SouscategorieServ souscategorieServ;
    CategorieServ categorieServ;
    DepartementServ departementServ;

    public SouscategorieCont(SouscategorieServ souscategorieServ, CategorieServ categorieServ, DepartementServ departementServ) {
        this.departementServ = departementServ;
        this.souscategorieServ = souscategorieServ;
        this.categorieServ = categorieServ;
    }

    @RequestMapping(value = {"/others"})
    public ModelAndView viewCategoriePage(Model model) {
        List<SousCategorie> sousCategorieList = souscategorieServ.getAll();
        List<Categorie> categorieList = categorieServ.getAll();
        List<Departement> departementList = departementServ.getAll();

        model.addAttribute("categorieList", categorieList);
        model.addAttribute("departementList", departementList);
        model.addAttribute("sousCategorieList", sousCategorieList);

        model.addAttribute("souscategorie", new SousCategorie());
        return new ModelAndView("souscategorie");
    }

    @GetMapping("/allsCat")
    public List<SousCategorie> allsCat(){
        List<SousCategorie> categorieList = souscategorieServ.getAll();
        return categorieList;
    }

    @GetMapping("/onesCat/{id}")
    public SousCategorie onesCat(@PathVariable(value = "id") Long id){
        SousCategorie categorie = souscategorieServ.getOne(id);
        return categorie;
    }

    @PostMapping("/addsCat/{categorie_id}/{departement_id}")
    public String addsCat(@PathVariable(value = "categorie_id") Long categorie_id, @PathVariable(value = "departement_id") Long departement_id, @RequestBody SousCategorie sousCategorie){
        return souscategorieServ.add(categorie_id, departement_id, sousCategorie);
    }

    @PutMapping("/updatesCat/{souscategorie_id}/{categorie_id}/{departement_id}")
    public String updatesCat(@PathVariable(value = "souscategorie_id") Long souscategorie_id, @PathVariable(value = "categorie_id") Long categorie_id, @PathVariable(value = "departement_id") Long departement_id,  @RequestBody SousCategorie sousCategorie){
        return souscategorieServ.update(souscategorie_id, categorie_id, departement_id, sousCategorie);
    }
}
