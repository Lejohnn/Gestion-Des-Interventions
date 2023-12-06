package com.example.intervenction.controllers;


import com.example.intervenction.entities.Etudiant;
import com.example.intervenction.entities.Personnel;
import com.example.intervenction.services.EtudiantServ;
import com.example.intervenction.services.PersonnelServ;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RestController
@CrossOrigin("*")
public class Dashboard {
    private final EtudiantServ etudiantServ;
    private final PersonnelServ personnelServ;

    public Dashboard(EtudiantServ etudiantServ, PersonnelServ personnelServ) {
        this.etudiantServ = etudiantServ;
        this.personnelServ = personnelServ;
    }

    @RequestMapping(value = {"/home"})
    public ModelAndView pageAccueil(Model model){
        return new ModelAndView("Accueil");
    }


    @RequestMapping(value = {"/dashboard"})
    public ModelAndView dashboard(Model model){
        return new ModelAndView("Dashboard");
    }


    @RequestMapping(value = {"/loginEtudiant"})
    public ModelAndView loginEtudiant(Model model){
        return new ModelAndView("loginEtudiant");
    }


    @RequestMapping(value = {"/"})
    public ModelAndView login(Model model){
        return new ModelAndView("login");
    }


    @GetMapping("/authentification/{login}/{password}")
    public ResponseEntity<Object> authentification(@PathVariable(value = "login") String login, @PathVariable(value = "password") String password){
        Etudiant etudiant = etudiantServ.login(login, password);
        Personnel personnel = personnelServ.login(login, password);
        if (etudiant != null) {
            etudiant.setStatut("etudiant");
            return ResponseEntity.ok(etudiant);
        } else if (personnel != null) {
            personnel.setStatut("personnel");
            return ResponseEntity.ok(personnel);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


    @RequestMapping(value = {"/etudiant"})
    public ModelAndView etudiant(Model model){
        return new ModelAndView("etudiant");
    }
}
