package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.TraitementDTO;

import java.util.List;
import java.util.UUID;

public interface DonneeTraitement {
    List<TraitementDTO> list();
    UUID insert(TraitementDTO item);
}
