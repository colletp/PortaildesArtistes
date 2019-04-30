package com.unamur.portaildesartistes.wsartiste.gestionutilisateur;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;

import java.util.List;
import java.util.UUID;

public interface UtilistateurService extends Service<UtilisateurDTO> {
    List<CitoyenDTO> listCitoyen();
}
