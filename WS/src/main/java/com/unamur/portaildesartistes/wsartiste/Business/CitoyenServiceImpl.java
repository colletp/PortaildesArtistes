package com.unamur.portaildesartistes.wsartiste.Business;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeAdresseImpl;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeCitoyenImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CitoyenServiceImpl implements IService<CitoyenDTO> {

    private static final Logger logger = LoggerFactory.getLogger(CitoyenServiceImpl.class);

    @Autowired
    private DonneeCitoyenImpl citImpl;
    @Autowired
    private DonneeAdresseImpl adrImpl;

    @Transactional
    public List<CitoyenDTO> list(){
        List<CitoyenDTO> citDTOList = citImpl.list();
        for( CitoyenDTO usr : citDTOList ){
            usr.setResideAdr( adrImpl.getById( usr.getReside() ) );
        }
        return citDTOList;
    }

    @Transactional
    public CitoyenDTO getById( UUID uuid ){
        CitoyenDTO cit= citImpl.getById(uuid);
        cit.setResideAdr( adrImpl.getById( cit.getReside() ) );
        return cit;
    }

    @Transactional
    public void update( CitoyenDTO cit ){
        citImpl.update(cit);
        if( cit.getResideAdr()!=null )
            adrImpl.update( cit.getResideAdr());
    }

    @Transactional
    public UUID insert( CitoyenDTO cit ) {
        logger.error("Use insert( UtilisateurDTO ) instead...");
        throw new UnsupportedOperationException("Use insert( UtilisateurDTO ) instead...");
    }

    @Transactional
    public UUID insert( UtilisateurDTO usr ){
        UUID adr=null;
        if( usr.getCitoyen().getResideAdr()!=null )
            adr=adrImpl.insert(usr.getCitoyen().getResideAdr());
        else
            throw new IllegalArgumentException("Insertion d'un citoyen sans adresse");
        usr.getCitoyen().setReside(adr);
        return citImpl.insert( usr );
    }

    @Transactional
    public void delete( UUID uuid ){
        citImpl.delete(uuid);
    }

}