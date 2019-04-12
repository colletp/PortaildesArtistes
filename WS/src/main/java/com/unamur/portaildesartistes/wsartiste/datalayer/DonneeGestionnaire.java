package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.GestionnaireDTO;

import java.util.List;
import java.util.UUID;

public interface DonneeGestionnaire {
    List<GestionnaireDTO> list();
    UUID insert(GestionnaireDTO item);
}
