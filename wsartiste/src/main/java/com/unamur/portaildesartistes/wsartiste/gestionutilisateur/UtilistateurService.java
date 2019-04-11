package com.unamur.portaildesartistes.wsartiste.gestionutilisateur;

import com.unamur.portaildesartistes.wsartiste.corelayer.UtilisateurBean;

import java.util.List;
import java.util.UUID;

public interface UtilistateurService {
    List<UtilisateurBean> list();
    UUID insertOK();
    void insertAndFail();
}
