package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.SecteurDTO;

import java.util.List;
import java.util.UUID;

public interface DonneeSecteur {
    List<SecteurDTO> list();
    UUID insert(SecteurDTO item);
    SecteurDTO getById(UUID p_id);
}
