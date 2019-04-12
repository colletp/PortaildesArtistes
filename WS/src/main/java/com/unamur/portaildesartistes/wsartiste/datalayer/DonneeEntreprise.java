package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.EntrepriseDTO;
import com.unamur.portaildesartistes.DTO.SecteurDTO;

import java.util.List;
import java.util.UUID;

public interface DonneeEntreprise {
    List<EntrepriseDTO> list();
    UUID insert(EntrepriseDTO item);
}
