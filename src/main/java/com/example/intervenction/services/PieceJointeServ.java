package com.example.intervenction.services;

import com.example.intervenction.entities.Demande;
import com.example.intervenction.entities.PieceJointe;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PieceJointeServ {
    List<PieceJointe> getAllFiles();

    PieceJointe getOneFile(Long id);

    String saveAttachment(Long demande_id, MultipartFile file, PieceJointe pieceJointe) throws Exception;

    void saveFiles(Long demande_id, MultipartFile[] files, PieceJointe pieceJointe) throws Exception;

    String deleteAttachment(Long pieceJointe_id) throws Exception;
}
