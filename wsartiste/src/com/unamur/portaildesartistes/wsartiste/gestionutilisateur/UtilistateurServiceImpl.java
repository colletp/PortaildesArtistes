package com.unamur.portaildesartistes.wsartiste.gestionutilisateur;

import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeUtilisateur;
import com.unamur.portaildesartistes.wsartiste.corelayer.UtilisateurBean;

import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public class UtilistateurServiceImpl implements UtilistateurService {

   private DonneeUtilisateur donneeUtilisateur;

    @Transactional
    public List<UtilisateurBean> list(){
        System.out.println(donneeUtilisateur.list());
        return donneeUtilisateur.list();
    }

    @Transactional
    public Integer insertOK(){
        UtilisateurBean authUser = new UtilisateurBean();
        authUser.setNomUtilisateur("nomUtilisateur"+new Date().getTime());
        return donneeUtilisateur.insert(authUser);
    }

    @Transactional
    public void insertAndFail(){
        UtilisateurBean authUser = new UtilisateurBean();
        authUser.setNomUtilisateur("username"+new Date().getTime());
        donneeUtilisateur.insert(authUser);

        throw new RuntimeException("Hello this is an error message");
    }

}