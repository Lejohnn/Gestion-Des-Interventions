package com.example.intervenction.controllers;


import com.example.intervenction.entities.Categorie;
import com.example.intervenction.entities.Etudiant;
import com.example.intervenction.entities.SousCategorie;
import com.example.intervenction.services.CategorieServ;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@CrossOrigin("*")
public class CategorieCont {
    CategorieServ categorieServ;
    public CategorieCont(CategorieServ categorieServ) {
        this.categorieServ = categorieServ;
    }

    @RequestMapping(value = {"/categorie"})
    public ModelAndView viewCategoriePage(Model model) {
        List<Categorie> categorieList = categorieServ.getAll();
        model.addAttribute("categorieList", categorieList);
        model.addAttribute("categorie", new Categorie());
        return new ModelAndView("categorie");
    }

    @RequestMapping(value = "/saveCat", method = RequestMethod.POST)
    public String saveCategorie(@RequestBody Categorie categorie) {
        return categorieServ.add(categorie);
    }

    @GetMapping("/allCat")
    public List<Categorie> allCat(){
        List<Categorie> liste = categorieServ.getAll();
        return liste;
    }


    @GetMapping("/getOneCat/{id}")
    public Categorie getOneCat(@PathVariable(value = "id") Long id){
        Categorie categorie = categorieServ.getOne(id);
        return categorie;
    }


    @PutMapping("/updateCat/{id}")
    public String updateCat(@PathVariable(value = "id") Long id, @RequestBody Categorie categorie){
        return categorieServ.update(id, categorie);
    }

}
