package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.wsartiste.corelayer.UtilisateurBean;

import java.util.List;
import java.util.UUID;

public interface DonneeUtilisateur {
    List<UtilisateurBean> list();
    UUID insert(UtilisateurBean item);
}
