package com.example.intervenction.controllers;

import com.example.intervenction.entities.Etudiant;
import com.example.intervenction.repositories.EtudiantRepo;
import com.example.intervenction.repositories.PersonnelRepo;
import com.example.intervenction.services.EtudiantServ;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class EtudiantCont {
    EtudiantServ etudiantServ;
    private final EtudiantRepo etudiantRepo;
    private final PersonnelRepo personnelRepo;

    public EtudiantCont(EtudiantServ etudiantServ,
                        EtudiantRepo etudiantRepo,
                        PersonnelRepo personnelRepo) {
        this.etudiantServ = etudiantServ;
        this.etudiantRepo = etudiantRepo;
        this.personnelRepo = personnelRepo;
    }

    @GetMapping("/allEtu")
    public List<Etudiant> allEtu(){
        List<Etudiant> liste = etudiantServ.getAll();
        return liste;
    }



    @GetMapping("/oneEtu/{id}")
    public Etudiant oneEtu(@PathVariable(value = "id") Long id){
        Etudiant etudiant = etudiantServ.getOne(id);
        return etudiant;
    }

    @PostMapping("/addEtu")
    public String addEtu(@RequestBody Etudiant etudiant){
        return etudiantServ.add(etudiant);
    }

    @PostMapping("/authenticationEtudiant/{login}/{password}")
    public Etudiant authenticationEtudiant(@PathVariable(value = "login") String login, @PathVariable(value = "password") String password){
        return etudiantServ.login(login, password);
    }



    @PutMapping("/updateEtu/{id}")
    public String updateEtu(@PathVariable(value = "id") Long id, @RequestBody Etudiant etudiant){
        return etudiantServ.updte(id, etudiant);
    }

    @PutMapping("/onEtu/{id}")
    public String onEtu(@PathVariable(value = "id") Long id){
        return etudiantServ.on(id);
    }

    @PutMapping("/offEtu/{id}")
    public String offEtu(@PathVariable(value = "id") Long id){
        return etudiantServ.off(id);
    }
}
