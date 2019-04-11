package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.dtoArtiste.corelayer.AdresseDTO;

import java.util.List;
import java.util.UUID;

public interface DonneeAdresse {
    List<AdresseDTO> list();
    UUID insert(AdresseDTO item);
}
