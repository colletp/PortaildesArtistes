package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;

import java.util.List;
import java.util.UUID;

public interface DonneeCitoyen {
    List<CitoyenDTO> list();
    UUID insert(UtilisateurDTO item);
}
