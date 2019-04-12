package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.FormulaireDTO;

import java.util.List;
import java.util.UUID;

public interface DonneeFormulaire {
    List<FormulaireDTO> list();
    UUID insert(FormulaireDTO item);
}
