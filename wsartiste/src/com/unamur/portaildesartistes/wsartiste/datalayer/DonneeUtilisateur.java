package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.wsartiste.corelayer.UtilisateurBean;

import java.util.List;

public interface DonneeUtilisateur {
    List<UtilisateurBean> list();

    Integer insert(UtilisateurBean item);
}
