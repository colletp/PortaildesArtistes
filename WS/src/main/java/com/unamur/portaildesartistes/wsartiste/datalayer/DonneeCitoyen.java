package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;

import java.util.List;
import java.util.UUID;

public interface DonneeCitoyen {
    List<CitoyenDTO> list();
    UUID insert(CitoyenDTO item);
}
