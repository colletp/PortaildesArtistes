package com.unamur.portaildesartistes.wsartiste.gestionutilisateur;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;

import java.util.List;
import java.util.UUID;

public interface UtilistateurService {
    List<UtilisateurDTO> list();
    UUID insertOK();
    void insertAndFail();
}
