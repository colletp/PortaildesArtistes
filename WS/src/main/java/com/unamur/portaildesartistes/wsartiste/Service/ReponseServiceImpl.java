package com.unamur.portaildesartistes.wsartiste.Service;

import com.unamur.portaildesartistes.DTO.ReponseDTO;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeReponseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public class ReponseServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(ActiviteServiceImpl.class);

    @Autowired
    private DonneeReponseImpl repImpl;

    @Transactional
    public List<ReponseDTO> listActivite(){
        return repImpl.list();
    }
    
    @Transactional
    public List<ReponseDTO> list(){
        return repImpl.list();
    }

    @Transactional
    public ReponseDTO getById( UUID uuid ){
        return repImpl.getById(uuid);
    }

    @Transactional
    public void update( ReponseDTO act ){
        // if(act.getSecteur () != "")
        repImpl.update(act);
    }

    @Transactional
    public UUID insert(ReponseDTO act ){
        // Ajout des contôles fonctionnels
        return repImpl.insert(act);
    }

    @Transactional
    public void delete( UUID uuid ){
        repImpl.delete(uuid);
    }

}
