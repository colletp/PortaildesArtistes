package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
import com.unamur.portaildesartistes.DTO.SecteurDTO;

import java.util.List;
import java.util.UUID;

public interface DonneeActivite {
    List<ActiviteDTO> list();
    UUID insert(ActiviteDTO item);
}
