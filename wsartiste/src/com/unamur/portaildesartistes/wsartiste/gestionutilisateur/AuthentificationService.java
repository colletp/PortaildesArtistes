package com.unamur.portaildesartistes.wsartiste.gestionutilisateur;

import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeUtilisateur;
import com.unamur.portaildesartistes.wsartiste.corelayer.AuthentificationBean;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public class AuthentificationService {

    @Autowired
    private DonneeUtilisateur donneeUtilisateur;

    @Transactional
    public List<AuthentificationBean> list(){
        System.out.println(donneeUtilisateur.list());
        return donneeUtilisateur.list();
    }

    @Transactional
    public Integer insertOK(){
        AuthentificationBean authUser = new AuthentificationBean();
        authUser.setNomUtilisateur("nomUtilisateur"+new Date().getTime());
        return donneeUtilisateur.insert(authUser);
    }

    @Transactional
    public void insertAndFail(){
        AuthentificationBean authUser = new AuthentificationBean();
        authUser.setNomUtilisateur("username"+new Date().getTime());
        donneeUtilisateur.insert(authUser);

        throw new RuntimeException("Hello this is an error message");
    }

}