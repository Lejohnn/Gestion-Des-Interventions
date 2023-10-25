package com.example.intervenction.implement;

import com.example.intervenction.entities.*;
import com.example.intervenction.repositories.DemandeRepo;
import com.example.intervenction.repositories.EtudiantRepo;
import com.example.intervenction.repositories.PersonnelRepo;
import com.example.intervenction.repositories.SouscategorieRepo;
import com.example.intervenction.services.DemandeServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DemandeImp implements DemandeServ {

    DemandeRepo demandeRepo;
    SouscategorieRepo souscategorieRepo;
    EtudiantRepo etudiantRepo;
    PersonnelRepo personnelRepo;

    @Autowired
    private JavaMailSender mailSender;

    public DemandeImp(
            DemandeRepo demandeRepo,
            SouscategorieRepo souscategorieRepo,
            PersonnelRepo personnelRepo,
            EtudiantRepo etudiantRepo){
        this.demandeRepo = demandeRepo;
        this.souscategorieRepo = souscategorieRepo;
        this.etudiantRepo = etudiantRepo;
        this.personnelRepo = personnelRepo;
    }

    @Override
    public List<Demande> getAll() {
        List<Demande> liste = new ArrayList<>();
        demandeRepo.findAll().forEach(liste::add);
        return liste;
    }


    @Override
    public Demande getOne(Long id) {
        return demandeRepo.findById(id).get();
    }


    @Override
    public String send(Long etudiant_id, Long sous_categorie_id, Demande demande) {
        Optional<SousCategorie> sousCategorieOptional = souscategorieRepo.findById(sous_categorie_id);
        if (sousCategorieOptional.isPresent()) {
            SousCategorie sousCategorie = sousCategorieOptional.get();
            Optional<Etudiant> etudiantOptional = etudiantRepo.findById(etudiant_id);
            if (etudiantOptional.isPresent()) {
                Etudiant etudiant = etudiantOptional.get();
                demande.setEtudiant(etudiant);
                demande.setSousCategorie(sousCategorie);
                Demande save = demandeRepo.save(demande);
                if (save != null) {
                    return "La demande de l'étudiant <b> " + etudiant.getNom().toUpperCase()
                            + " </b> pour la sous catégorie <b>" + sousCategorie.getNom()
                            + " </b> à bien été envoyé";
                }else {
                    return "La demande n'a pas été envoyé";
                }

            }else {
                throw new IllegalArgumentException("L'étudiant est introuvable, ID : " + etudiant_id);
            }

        }else {
            throw new IllegalArgumentException("La sous-catégorie est introuvable, ID : " + sous_categorie_id);
        }
    }


    @Override
    public String prise_en_charge(Long personnel_id, Long id) {
        Optional<Personnel> personnelOptional = personnelRepo.findById(personnel_id);
        if (personnelOptional.isPresent()) {
            Personnel personnel = personnelOptional.get();

            Optional<Demande> demandeOptional = demandeRepo.findById(id);
            if (demandeOptional.isPresent()) {
                Demande demande = demandeOptional.get();
                demande.setPersonnel(personnel);
                demande.setStatut("pris en charge");
                Demande save = demandeRepo.save(demande);
                if (save != null) {
                    String message = "La demande de l'étudiant <b> " + demande.getEtudiant().getNom() + " </b> à été prise en change";
                    String objet = "Demande Pise en charge";
                    this.send(demande.getEtudiant().getEmail(), message, objet);
                    return message;
                }else {
                    return "La demande de l'étudiant <b> " + demande.getEtudiant().getNom() + " </b> n'a pas été prise en charge";
                }

            }else {
                throw new IllegalArgumentException("La demande est introuvable, ID : " + personnel_id);
            }
        }else {
            throw new IllegalArgumentException("Le personnel est introuvable, ID : " + personnel_id);
        }
    }


    @Override
    public String approuver(Long personnel_id, Long id) {
        Optional<Personnel> personnelOptional = personnelRepo.findById(personnel_id);
        if (personnelOptional.isPresent()) {
            Personnel personnel = personnelOptional.get();
            Optional<Demande> demandeOptional = demandeRepo.findById(id);
            if (demandeOptional.isPresent()) {
                Demande demande = demandeOptional.get();
                demande.setPersonnel(personnel);
                demande.setStatut("finalisée");
                Demande save = demandeRepo.save(demande);
                if (save != null) {
                    String message = "La demande de l'étudiant <b> " + demande.getEtudiant().getNom() + " </b> à été Finalisée";
                    String objet = "Demande Finalisée";
                    this.send(demande.getEtudiant().getEmail(), message, objet);
                    return message;
                }else {
                    return "La demande de l'étudiant <b> " + demande.getEtudiant().getNom() + " </b> n'a pas pue etre Finalisée";
                }

            }else {
                throw new IllegalArgumentException("La demande est introuvable, ID : " + personnel_id);
            }
        }else {
            throw new IllegalArgumentException("Le personnel est introuvable, ID : " + personnel_id);
        }
    }


    @Override
    public String rejetter(Long personnel_id, Long id) {
        Optional<Personnel> personnelOptional = personnelRepo.findById(personnel_id);
        if (personnelOptional.isPresent()) {
            Personnel personnel = personnelOptional.get();
            Optional<Demande> demandeOptional = demandeRepo.findById(id);
            if (demandeOptional.isPresent()) {
                Demande demande = demandeOptional.get();
                demande.setPersonnel(personnel);
                demande.setStatut("rejettée");
                Demande save = demandeRepo.save(demande);
                if (save != null) {
                    String message = "La demande de l'étudiant <b> " + demande.getEtudiant().getNom() + " </b> à été rejettée";
                    String objet = "Demande Rejettée";
                    this.send(demande.getEtudiant().getEmail(), message, objet);
                    return message;
                }else {
                    return "La demande de l'étudiant <b> " + demande.getEtudiant().getNom() + " </b> n'a pas pue etre rejettée";
                }

            }else {
                throw new IllegalArgumentException("La demande est introuvable, ID : " + personnel_id);
            }
        }else {
            throw new IllegalArgumentException("Le personnel est introuvable, ID : " + personnel_id);
        }
    }




    public void send(String etudiant, String messages, String objet) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gestioninterventiongroupe11@gmail.com");
        message.setTo(etudiant);
        message.setText(messages);
        message.setSubject(objet);
        mailSender.send(message);
    }
}
