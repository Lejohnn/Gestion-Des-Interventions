package com.example.intervenction.implement;
import com.example.intervenction.entities.Categorie;
import com.example.intervenction.entities.Demande;
import com.example.intervenction.entities.Departement;
import com.example.intervenction.entities.PieceJointe;
import com.example.intervenction.repositories.DemandeRepo;
import com.example.intervenction.repositories.PieceJointeRepo;
import com.example.intervenction.services.PieceJointeServ;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PieceJointeImp implements PieceJointeServ {

    PieceJointeRepo pieceJointeRepo;

    DemandeRepo demandeRepo;

    private final String uploadDir = "/opt/uploads";

    public PieceJointeImp(PieceJointeRepo pieceJointeRepo, DemandeRepo demandeRepo){
        this.pieceJointeRepo = pieceJointeRepo;
        this.demandeRepo =  demandeRepo;
    }


    @Override
    public List<PieceJointe> getAllFiles() {
        List<PieceJointe> liste = new ArrayList<>();
        pieceJointeRepo.findAll().forEach(liste::add);
        return liste;
    }


    @Override
    public PieceJointe getOneFile(Long id) {
        return pieceJointeRepo.findById(id).get();
    }


    @Override
    public String saveAttachment(Long demande_id, MultipartFile file, PieceJointe pieceJointe) throws Exception {
        Optional<Demande> demandeOptional = demandeRepo.findById(demande_id);
        if (demandeOptional.isPresent()) {
            Demande demande = demandeOptional.get();
            String originalFileName = file.getOriginalFilename();

            // Create the upload directory if it doesn't exist
            Path uploadPath = Path.of(uploadDir);
            Files.createDirectories(uploadPath);

            // Resolve the path for the new file
            Path filePath = uploadPath.resolve(originalFileName);


            // Copy the file to the destination directory
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            pieceJointe.setDemande(demande);
            pieceJointe.setFileName(originalFileName);
            pieceJointe.setData(file.getBytes());

            final PieceJointe save = pieceJointeRepo.save(pieceJointe);
            if (save != null) {
                return "La pièce jointe a été upload avec succès";
            }else {
                return "La pièce jointe n'a pas pue etre upload";
            }


        }
        else {
            throw new IllegalArgumentException("La demande est introuvable, ID : " + demande_id);
        }
    }


    @Override
    public void saveFiles(Long demande_id, MultipartFile[] files, PieceJointe pieceJointe) throws Exception {
        Optional<Demande> demandeOptional = demandeRepo.findById(demande_id);
        if (demandeOptional.isPresent()) {
            Demande demande = demandeOptional.get();
            pieceJointe.setDemande(demande);
            Arrays.asList(files).forEach(file -> {
                try {
                    saveAttachment(demande_id, file, pieceJointe);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        else {
            throw new IllegalArgumentException("La demande est introuvable, ID : " + demande_id);
        }
    }

    @Override
    public String deleteAttachment(Long pieceJointe_id) throws Exception {
        Optional<PieceJointe> pieceJointeOptional = pieceJointeRepo.findById(pieceJointe_id);
        if (pieceJointeOptional.isPresent()) {
            PieceJointe pieceJointe = pieceJointeOptional.get();
            pieceJointeRepo.deleteById(pieceJointe.getId());
            return "La pièce jointe a bien été supprimée";
        }
        return "Désolé la pièce jointe n'a pas pue etre supprimée";
    }
}
