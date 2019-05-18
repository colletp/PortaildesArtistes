package com.unamur.portaildesartistes.wsartiste.Business;

import com.unamur.portaildesartistes.DTO.GestionnaireDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeCitoyenImpl;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeGestionnaireImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class GestionnaireServiceImpl implements IService<GestionnaireDTO> {

    private static final Logger logger = LoggerFactory.getLogger(GestionnaireServiceImpl.class);

    @Autowired
    private DonneeGestionnaireImpl gestImpl;

    @Autowired
    private CitoyenServiceImpl citServImpl;

    @Transactional
    public List<GestionnaireDTO> listGestionnaire(){
        List<GestionnaireDTO> lGestDTO = gestImpl.list();
        for( GestionnaireDTO gestDTO : lGestDTO )
            gestDTO.setCitoyen( citServImpl.getById(gestDTO.getCitoyenId()) );
        return lGestDTO;
    }

    @Transactional
    public List<GestionnaireDTO> list(){ return gestImpl.list(); }

    @Transactional
    public GestionnaireDTO getById( UUID uuid ){
        GestionnaireDTO gestDTO = gestImpl.getById(uuid);
        gestDTO.setCitoyen( citServImpl.getById(gestDTO.getCitoyenId()) );
        return gestDTO;
    }

    @Transactional
    public void update( GestionnaireDTO gest ){
        gestImpl.update(gest);
    }


    @Transactional
    public UUID insert( GestionnaireDTO gest ) {
        if( gest.getCitoyenId()==null )
            throw new IllegalArgumentException("Insertion d'un Gestionnaire sans citoyen");
        if( gest.getTravailleId()==null )
            throw new IllegalArgumentException("Insertion d'un Gestionnaire sans adresse de travail");
        return gestImpl.insert( gest );
    }

    @Transactional
    public void delete( UUID uuid ){
        gestImpl.delete(uuid);
    }

}