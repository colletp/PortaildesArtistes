package com.unamur.portaildesartistes.wsartiste.gestionutilisateur;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;

import java.util.List;
import java.util.UUID;

public interface UtilistateurService {
    List<CitoyenDTO> list();
    UUID insertOK();
    void insertAndFail();
}
