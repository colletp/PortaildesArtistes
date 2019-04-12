package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.CommanditaireDTO;
import com.unamur.portaildesartistes.DTO.SecteurDTO;

import java.util.List;
import java.util.UUID;

public interface DonneeCommanditaire {
    List<CommanditaireDTO> list();
    UUID insert(CommanditaireDTO item);
}
