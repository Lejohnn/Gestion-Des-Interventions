package com.example.intervenction.implement;

import com.example.intervenction.entities.*;
import com.example.intervenction.repositories.DemandeRepo;
import com.example.intervenction.repositories.EtudiantRepo;
import com.example.intervenction.repositories.PersonnelRepo;
import com.example.intervenction.repositories.SouscategorieRepo;
import com.example.intervenction.services.DemandeServ;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Value("${spring.mail.username}")
    private String FromEmail;

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

    @GetMapping("/currentDateTime")
    public String getCurrentDateTime() {
        // Récupérer la date et l'heure actuelles
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Définir le format de la date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        // Formater la date et l'heure actuelles selon le format spécifié
        String formattedDateTime = currentDateTime.format(formatter);

        return formattedDateTime;
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
                demande.setStatut("receptionnée");
                Demande save = demandeRepo.save(demande);
                String times = this.getCurrentDateTime();

                if (save != null) {
                    String objet = "Accusé de recption de la demande d'intervenctiion";
                    String message = "Msr/Mlle/Mme "+ etudiant.getNom().toUpperCase() +" nous vous signalons simplement que votre demande d'intervenction envoyé le "+ times +" concernant la sous-catégorie " +  sousCategorie.getNom() + " dont l'objet est : <b>"+ demande.getObjet() +"<b> à bien été réceptionnée";

                    // transmission du mail
                    this.sendMail(FromEmail, etudiant.getEmail(), message, objet);

                    return "Hello <b> " + etudiant.getNom().toUpperCase()
                            + " </b> votre demande pour la sous-catégorie <b>" + sousCategorie.getNom()
                            + " </b> dont l'objet est <b>"+ demande.getObjet() +"<b> à été soumise avec succès";
                }else {
                    return "La demande n'a pas été envoyé, veillez recommencer s'il vous plait";
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
                Optional<Etudiant> etudiantOptional = etudiantRepo.findById(demande.getEtudiant().getId());
                if (etudiantOptional.isPresent()) {
                    Etudiant etudiant = etudiantOptional.get();
                    demande.setPersonnel(personnel);
                    demande.setStatut("pris en charge");
                    Demande save = demandeRepo.save(demande);
                    if (save != null) {
                        String objet = "Prise en charge de la demande d'intervenction";
                        String message1 = "Msr/Mlle/Mme "+ etudiant.getNom().toUpperCase() +" nous vous signalons simplement qu'en date du "+ this.getCurrentDateTime() +" votre demande d'intervenction a bien été prise en charge par le personnel nommé : " + personnel.getNom().toUpperCase() +" occupant le poste relatif à "+ personnel.getPoste();

                        String message = "Nous vous informons que la demande de l'étudiant <b> " + etudiant.getNom() + " </b> à bien été prise en change par <b>"+ personnel.getNom().toUpperCase()+ "</b> occupant le poste relatif à <b>"+ personnel.getPoste() + " </b>";
                        this.send(demande.getEtudiant().getEmail(), message1, objet);
                        return message;
                    }else {
                        return "Nous avons rencontré des erreurs lors de la prise en charge de la demande d'intervenction de l'étudiant <b> " + etudiant.getNom() + " </b> veillez réeseillé plutard.";
                    }
                }
                else {
                    throw new IllegalArgumentException("L'étudiant est introuvable, ID : " + demande.getEtudiant().getId());
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
                Optional<Etudiant> etudiantOptional = etudiantRepo.findById(demande.getEtudiant().getId());
                if (etudiantOptional.isPresent()) {
                    Etudiant etudiant = etudiantOptional.get();
                    demande.setPersonnel(personnel);
                    demande.setStatut("finalisée");
                    Demande save = demandeRepo.save(demande);
                    if (save != null) {
                        String objet = "Finalisation de la demande d'intervenction";
                        String message1 = "Msr/Mlle/Mme "+ etudiant.getNom().toUpperCase() +" nous vous signalons simplement qu'en date du "+ this.getCurrentDateTime() +" votre demande d'intervenction a bien été finalisée ceci via le personnel nommé : " + personnel.getNom().toUpperCase() +" occupant le poste relatif à "+ personnel.getPoste();

                        String message = "Nous vous informons que la demande de l'étudiant <b> " + etudiant.getNom() + " </b> à bien été <b style='color:red;'>Finalisée</b> par vous Mrs/Mme <b>"+ personnel.getNom().toUpperCase()+ "</b> occupant le poste de <b style='color:red;'>"+ personnel.getPoste() + " </b>";
                        this.send(demande.getEtudiant().getEmail(), message1, objet);
                        return message;
                    }else {
                        return "Nous avons rencontré des erreurs lors de la l'approbation de la demande d'intervenction de l'étudiant <b> " + etudiant.getNom() + " </b> veillez réeseillé plutard.";
                    }
                }
                else {
                    throw new IllegalArgumentException("L'étudiant est introuvable, ID : " + demande.getEtudiant().getId());
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
                Optional<Etudiant> etudiantOptional = etudiantRepo.findById(demande.getEtudiant().getId());
                if (etudiantOptional.isPresent()) {
                    Etudiant etudiant = etudiantOptional.get();
                    demande.setPersonnel(personnel);
                    demande.setStatut("rejettée");
                    Demande save = demandeRepo.save(demande);
                    if (save != null) {
                        String objet = "Rejet de la demande d'intervenction";
                        String message1 = "Msr/Mlle/Mme "+ etudiant.getNom().toUpperCase() +" nous vous signalons simplement qu'en date du "+ this.getCurrentDateTime() +" votre demande d'intervenction a bien été rejetté ceci via le personnel nommé : " + personnel.getNom().toUpperCase() +" occupant le poste relatif à "+ personnel.getPoste() +" le motif de ce rejet étant : ";

                        String message = "Nous vous informons que la demande de l'étudiant <b> " + etudiant.getNom() + " </b> à bien été <b style='color:red;'>Rejetté</b> par vous Mrs/Mme <b>"+ personnel.getNom().toUpperCase()+ "</b> occupant le poste de <b style='color:red;'>"+ personnel.getPoste() + " </b>";
                        this.send(demande.getEtudiant().getEmail(), message1, objet);
                        return message;
                    }else {
                        return "Nous avons rencontré des erreurs lors du rejet de la demande d'intervenction de l'étudiant <b> " + etudiant.getNom() + " </b> veillez réeseillé plutard.";
                    }
                }
                else {
                    throw new IllegalArgumentException("L'étudiant est introuvable, ID : " + demande.getEtudiant().getId());
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



    public String sendMail(String from, String to, String subject, String body) {
        try {

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true); // enlever cc
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body);
            mailSender.send(mimeMessage);
            return "send";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
