package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.PrestationDTO;

import java.util.List;
import java.util.UUID;

public interface DonneePrestation {
    List<PrestationDTO> list();
    UUID insert(PrestationDTO item);
}
