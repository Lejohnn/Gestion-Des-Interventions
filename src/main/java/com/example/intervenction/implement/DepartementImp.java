package com.example.intervenction.implement;

import com.example.intervenction.entities.Demande;
import com.example.intervenction.entities.Departement;
import com.example.intervenction.repositories.DepartementRepo;
import com.example.intervenction.repositories.MailRepo;
import com.example.intervenction.services.DepartementServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartementImp implements DepartementServ {
    DepartementRepo departementRepo;
    MailRepo mailRepo;

    public DepartementImp(DepartementRepo departementRepo, MailRepo mailRepo){
        this.departementRepo = departementRepo;
        this.mailRepo = mailRepo;
    }

    @Override
    public List<Departement> getAll() {
        List<Departement> liste = new ArrayList<>();
        departementRepo.findAll().forEach(liste::add);
        return liste;
    }

    @Override
    public Departement getOne(Long id) {
        return departementRepo.findById(id).get();
    }

    @Override
    public String add(Departement departement) {
        Departement save = departementRepo.save(departement);
        if (save != null) {
            return "La département <b> " + departement.getNom().toUpperCase() + " </b> à bien été enregistré";
        }else {
            return "La département n'a pas été enregistré";
        }
    }

    @Override
    public String updte(Long id, Departement departement) {
        Optional<Departement> departementOptional = departementRepo.findById(id);
        if (departementOptional.isPresent()) {
            Departement departement1 = departementOptional.get();
            departement1.setNom(departement.getNom());
            Departement save = departementRepo.save(departement1);
            if (save != null) {
                return "Le département <b> " + departement.getNom().toUpperCase() + " </b> à été modifié";
            }else {
                return "Le département <b> " +  departement.getNom().toUpperCase() + " </b> n'a pas été modifié";
            }
        }
        else {
            throw new IllegalArgumentException("Le département est introuvable, ID : " + id);
        }
    }
}
