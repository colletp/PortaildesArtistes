package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.DocArtisteDTO;
import com.unamur.portaildesartistes.DTO.SecteurDTO;

import java.util.List;
import java.util.UUID;

public interface DonneeDocArtiste {
    List<DocArtisteDTO> list();
    UUID insert(DocArtisteDTO item);
}
