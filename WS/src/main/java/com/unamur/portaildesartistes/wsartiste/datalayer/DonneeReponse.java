package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.ReponseDTO;

import java.util.List;
import java.util.UUID;

public interface DonneeReponse {
    List<ReponseDTO> list();
    UUID insert(ReponseDTO item);
}
