package com.example.intervenction.controllers;

import com.example.intervenction.entities.Departement;
import com.example.intervenction.entities.Etudiant;
import com.example.intervenction.entities.Personnel;
import com.example.intervenction.entities.SousCategorie;
import com.example.intervenction.services.DepartementServ;
import com.example.intervenction.services.EtudiantServ;
import com.example.intervenction.services.PersonnelServ;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@CrossOrigin("*")
public class PersonnelCont {
    PersonnelServ personnelServ;
    DepartementServ departementServ;
    public PersonnelCont(PersonnelServ personnelServ, DepartementServ departementServ) {
        this.departementServ = departementServ;
        this.personnelServ = personnelServ;
    }




    @RequestMapping(value = {"/personnel"})
    public ModelAndView personnel(Model model){
        List<Personnel> personnelList = personnelServ.getAll();
        List<Departement> departementList = departementServ.getAll();


        model.addAttribute("departementList", departementList);
        model.addAttribute("personnelList", personnelList);
        model.addAttribute("personnel", new Personnel());

        return new ModelAndView("personnel");
    }

    @GetMapping("/allPers")
    public List<Personnel> allPers(){
        List<Personnel> liste = personnelServ.getAll();
        return liste;
    }

    @PostMapping("/authenticationPersonnel/{login}/{password}")
    public Personnel authenticationPersonnel(@PathVariable(value = "login") String login, @PathVariable(value = "password") String password){
        return personnelServ.login(login, password);
    }

    @GetMapping("/onePers/{id}")
    public Personnel onePers(@PathVariable(value = "id") Long id){
        Personnel etudiant = personnelServ.getOne(id);
        return etudiant;
    }

    @PostMapping("/addPersone/{id}")
    public String addPersone(@PathVariable(value = "id") Long id, @RequestBody Personnel personnel){
        return personnelServ.add(id, personnel);
    }

    @PutMapping("/updatePers/{id_personnel}/{id_departemnt}")
    public String updatePers(@PathVariable(value = "id_personnel") Long id_personnel, @PathVariable(value = "id_departemnt") Long id_departemnt, @RequestBody Personnel personnel){
        return personnelServ.updte(id_personnel, id_departemnt, personnel);
    }

    @PutMapping("/onPers/{id}")
    public String onPers(@PathVariable(value = "id") Long id){
        return personnelServ.on(id);
    }

    @PutMapping("/offPers/{id}")
    public String offPers(@PathVariable(value = "id") Long id){
        return personnelServ.off(id);
    }
}
