package com.example.intervenction.controllers;


import com.example.intervenction.entities.Personnel;
import com.example.intervenction.entities.PieceJointe;
import com.example.intervenction.services.DemandeServ;
import com.example.intervenction.services.PieceJointeServ;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
public class PieceJointeCont {

    DemandeServ demandeServ;
    PieceJointeServ pieceJointeServ;

    public PieceJointeCont(DemandeServ demandeServ, PieceJointeServ pieceJointeServ) {
        this.demandeServ = demandeServ;
        this.pieceJointeServ = pieceJointeServ;
    }


    @GetMapping("/allPieces")
    public List<PieceJointe> allPieces(){
        List<PieceJointe> liste = pieceJointeServ.getAllFiles();
        return liste;
    }


    @GetMapping("/onePiece/{id}")
    public PieceJointe onePiece(@PathVariable(value = "id") Long id){
        PieceJointe result = pieceJointeServ.getOneFile(id);
        return result;
    }


    @PostMapping("/addPiece/{id_demande}")
    public String addPiece(@PathVariable(value = "id_demande") Long id_demande, @RequestParam("file") MultipartFile file, @RequestBody PieceJointe pieceJointe)  throws Exception{
        return pieceJointeServ.saveAttachment(id_demande, file, pieceJointe);
    }

    @PostMapping("/removePiece/{id_pieceJointe}")
    public String removePiece(@PathVariable(value = "id_pieceJointe") Long id_pieceJointe)  throws Exception{
        return pieceJointeServ.deleteAttachment(id_pieceJointe);
    }
}
