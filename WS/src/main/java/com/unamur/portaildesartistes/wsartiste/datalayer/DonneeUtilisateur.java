package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;

import java.util.List;
import java.util.UUID;

public interface DonneeUtilisateur {
    List<UtilisateurDTO> list();
    UUID insert(UtilisateurDTO item);
}
