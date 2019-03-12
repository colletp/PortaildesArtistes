package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.wsartiste.corelayer.AuthentificationBean;

import java.util.List;

public interface DonneeUtilisateur {
    public List<AuthentificationBean> list();
    public Integer insert(AuthentificationBean item);

}
