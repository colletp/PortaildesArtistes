package com.unamur.portaildesartistes.wsartiste.gestionutilisateur;

import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeAdresseImpl;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeRoleImpl;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeUtilisateurImpl;
import com.unamur.portaildesartistes.wsartiste.corelayer.UtilisateurBean;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UtilistateurServiceImpl implements UtilistateurService {

    @Autowired
    private DonneeUtilisateurImpl donneeUtilisateur;
    @Autowired
    private DonneeAdresseImpl adrImpl;
    @Autowired
    private DonneeRoleImpl roleImpl;

    private UtilisateurBean authUser;

    UtilistateurServiceImpl(){
        authUser = new UtilisateurBean();
    }

    @Transactional
    public List<UtilisateurBean> list(){
        List<UtilisateurBean> du = donneeUtilisateur.list();
        for( UtilisateurBean usr : du ){
            usr.setResideAdr( adrImpl.getById( usr.getReside() ) );
            usr.setRoles( roleImpl.getByUser( usr.getId() ) );
        }
        return du;
    }

    @Transactional
    public UUID insertOK(){
        authUser.setLogin("log"+new Date().getTime());
        return donneeUtilisateur.insert(authUser);
    }

    @Transactional
    public void insertAndFail(){
        //authUser.setNomUtilisateur("login"+new Date().getTime());
        //donneeUtilisateur.insert(authUser);
        throw new RuntimeException("Hello this is an error message");
    }

}