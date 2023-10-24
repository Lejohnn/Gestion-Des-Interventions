package com.example.intervenction.controllers;


import com.example.intervenction.entities.Etudiant;
import com.example.intervenction.entities.Service;
import com.example.intervenction.services.EtudiantService;
import com.example.intervenction.services.ServiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class EtudiantController {

    EtudiantService etudiantService;

    private String res = "";

    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    @GetMapping("/allEtu")
    public List<Etudiant> allEtu(){
        return etudiantService.all();
    }




    @GetMapping("/unEtu/{id}")
    public Etudiant unEtu(@PathVariable(value = "id") Long id){
        return etudiantService.un(id);
    }




    @PostMapping("/addEtu")
    public String addEtu(@RequestBody Etudiant etudiant){
        return etudiantService.plus(etudiant);
    }


    @PutMapping("/updateEtu/{id}")
    public String updateEtu(@PathVariable Long id, @RequestBody Etudiant etudiant){
        return etudiantService.update(id, etudiant);
    }


    @PutMapping("/onEtu/{id}")
    public String onEtu(@PathVariable(value = "id") Long id){
        return etudiantService.on(id);
    }


    @PutMapping("/offEtu/{id}")
    public String offEtu(@PathVariable(value = "id") Long id){
        return etudiantService.off(id);
    }

}
