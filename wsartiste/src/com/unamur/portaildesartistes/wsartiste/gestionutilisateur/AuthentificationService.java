package com.unamur.portaildesartistes.wsartiste.gestionutilisateur;

import com.unamur.portaildesartistes.wsartiste.corelayer.AuthentificationBean;

import java.util.List;

public interface AuthentificationService {
    public List<AuthentificationBean> list();

    public Integer insertOK();

    public void insertAndFail();

}
