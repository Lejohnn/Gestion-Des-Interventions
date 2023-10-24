package com.example.intervenction.servicesImplement;

import com.example.intervenction.entities.Etudiant;
import com.example.intervenction.repositories.EtudiantRepository;
import com.example.intervenction.repositories.ServiceRepository;
import com.example.intervenction.services.EtudiantService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EtudiantServiceImplement implements EtudiantService {

    EtudiantRepository etudiantRepository;

    private String response = "";

    public EtudiantServiceImplement(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }

    @Override
    public List<Etudiant> all() {
        List<Etudiant> etudiantList = new ArrayList<>();
        etudiantRepository.findAll().forEach(etudiantList::add);
        return etudiantList;
    }

    @Override
    public Etudiant un(Long id) {
        return etudiantRepository.findById(id).get();
    }

    @Override
    public String plus(Etudiant etudiant) {
        final Etudiant etudiant1 = etudiantRepository.save(etudiant);
        if (etudiant1 != null) {
            return this.response = "L'étudiant " + etudiant.getNom().toUpperCase() + " " + etudiant.getPrenom() + " à été enregistrée";
        }else {
            return this.response = "L'étudiant n'a pas été enregistré";
        }
    }

    @Override
    public String update(Long id, Etudiant etudiant) {
        Optional<Etudiant> etudiantOptional = etudiantRepository.findById(id);
        if (etudiantOptional.isPresent()) {
            Etudiant etudiant1 = etudiantOptional.get();
            etudiant1.setMatricule(etudiant.getMatricule());
            etudiant1.setFiliere(etudiant.getFiliere());
            etudiant1.setClasse(etudiant.getClasse());
            etudiant1.setEmail(etudiant.getEmail());
            etudiant1.setPrenom(etudiant.getPrenom());
            etudiant1.setNom(etudiant.getNom());
            etudiant1.setLogin(etudiant.getLogin());
            etudiant1.setMot_passe(etudiant.getMot_passe());
            etudiant1.setTelephone(etudiant.getTelephone());

            final Etudiant etudiant2 = etudiantRepository.save(etudiant1);
            if (etudiant2 != null) {
                return this.response = "L'étudiant " +  etudiant.getNom().toUpperCase() + " " + etudiant.getPrenom()  + " à été modifié";
            }else {
                return this.response = "L'étudiant n'a pas pue etre modifié";
            }
        }else {
            throw new IllegalArgumentException("L'étudiant est introuvable, ID : " + id);
        }
    }

    @Override
    public String on(Long id) {
        Optional<Etudiant> etudiantOptional = etudiantRepository.findById(id);
        if (etudiantOptional.isPresent()) {
            Etudiant etudiant = etudiantOptional.get();
            etudiant.setStatut("actif");
            final Etudiant etudiant1 = etudiantRepository.save(etudiant);
            if (etudiant1 != null) {
                return this.response = "L'étudiant " +  etudiant.getNom().toUpperCase() + " " + etudiant.getPrenom()  + " à été réactivé";
            }else {
                return this.response = "L'étudiant n'a pas pu etre réactive";
            }
        }else {
            throw new IllegalArgumentException("L'étudiant est introuvable, ID : " + id);
        }
    }

    @Override
    public String off(Long id) {
        Optional<Etudiant> etudiantOptional = etudiantRepository.findById(id);
        if (etudiantOptional.isPresent()) {
            Etudiant etudiant = etudiantOptional.get();
            etudiant.setStatut("inactif");
            final Etudiant etudiant1 = etudiantRepository.save(etudiant);
            if (etudiant1 != null) {
                return this.response = "L'étudiant " +  etudiant.getNom().toUpperCase() + " " + etudiant.getPrenom()  + " à été désactivé";
            }else {
                return this.response = "L'étudiant n'a pas pu etre désactivé";
            }
        }else {
            throw new IllegalArgumentException("L'étudiant est introuvable, ID : " + id);
        }
    }

    @Override
    public String clean(Long id) {
        return null;
    }
}
