package com.example.intervenction.controllers;

import com.example.intervenction.entities.Categorie;
import com.example.intervenction.entities.Departement;
import com.example.intervenction.services.DepartementServ;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@CrossOrigin("*")
public class DepartementCont {
    DepartementServ departementServ;
    public DepartementCont(DepartementServ departementServ) {
        this.departementServ = departementServ;
    }


    @RequestMapping(value = {"/departement"})
    public ModelAndView departement(Model model){
        List<Departement> departementList = departementServ.getAll();
        model.addAttribute("departementList", departementList);
        model.addAttribute("departement", new Departement());
        return new ModelAndView("departement");
    }


    @GetMapping("/allDep")
    public List<Departement> allDep(){
        List<Departement> departementList = departementServ.getAll();
        return departementList;
    }

    @GetMapping("/oneDep/{id}")
    public Departement oneDep(@PathVariable(value = "id") Long id){
        Departement departement = departementServ.getOne(id);
        return departement;
    }

    @PostMapping("/addDep")
    public String addDep(@RequestBody Departement departement){
        return departementServ.add(departement);
    }

    @PutMapping("/updateDep/{id}")
    public String updateDep(@PathVariable(value = "id") Long id, @RequestBody Departement departement){
        return departementServ.updte(id, departement);
    }
}
