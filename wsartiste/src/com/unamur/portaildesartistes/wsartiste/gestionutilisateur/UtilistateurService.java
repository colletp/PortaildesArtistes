package com.unamur.portaildesartistes.wsartiste.gestionutilisateur;

import com.unamur.portaildesartistes.wsartiste.corelayer.UtilisateurBean;

import java.util.List;

public interface UtilistateurService {
    List<UtilisateurBean> list();
    Integer insertOK();
    void insertAndFail();
}
