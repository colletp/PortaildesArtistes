package com.unamur.portaildesartistes.wsartiste.Business;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeRoleImpl;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeUtilisateurImpl;
import com.unamur.portaildesartistes.wsartiste.security.WebSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class UtilisateurServiceImpl implements IService<UtilisateurDTO> {

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurServiceImpl.class);

    //@Autowired
    //PasswordEncoder encoder;

    @Autowired
    private DonneeUtilisateurImpl usrImpl;
    @Autowired
    private CitoyenServiceImpl citServImpl;

    @Autowired
    private DonneeRoleImpl roleImpl;
    @Transactional
    public List<UtilisateurDTO> list(){
        List<UtilisateurDTO> usrDTOList = usrImpl.list();
        for( UtilisateurDTO usr : usrDTOList )
            usr.setAuthorities( roleImpl.getByCitoyenId( usr.getId() ) );
        return usrDTOList;
    }

    @Transactional
    public UtilisateurDTO getById( UUID uuid ){
        UtilisateurDTO usr= usrImpl.getById( uuid );
        usr.setAuthorities( roleImpl.getByCitoyenId( usr.getId() ) );
        usr.setCitoyen( citServImpl.getById( uuid ) );
        return usr;
    }

    @Transactional
    public UUID getUuidByName( String userName ){
        UtilisateurDTO usr= usrImpl.getByName(userName);
        return usr.getId();
    }

    @Transactional
    public void update( UtilisateurDTO usr ){
        if( !usr.getPassword().isEmpty() ) {
            usr.setPassword(WebSecurityConfig.encoder().encode(usr.getPassword()));
            usrImpl.update(usr);
        }
        if(usr.getCitoyen()!=null){
            citServImpl.update( usr.getCitoyen() );
        }
    }

    @Transactional
    public UUID insert( UtilisateurDTO usr ){
        usr.setPassword( WebSecurityConfig.encoder().encode(usr.getPassword()) );
        if( usr.getCitoyen()!=null ){
            return citServImpl.insert(usr);
        }
        else
            throw new IllegalArgumentException("Insertion d'un utilisateur sans citoyen");
    }

    @Transactional
    public void delete( UUID uuid ){
        usrImpl.delete(uuid);
    }

}