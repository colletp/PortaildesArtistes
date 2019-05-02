package com.unamur.portaildesartistes.wsartiste.Service;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeActiviteImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
public class ActiviteServiceImpl implements IService<ActiviteDTO> {

    private static final Logger logger = LoggerFactory.getLogger(ActiviteServiceImpl.class);

    @Autowired
    private DonneeActiviteImpl actImpl;

    @Transactional
    public List<ActiviteDTO> listActivite(){
        return actImpl.list();
   }

    @Transactional
    public List<ActiviteDTO> list(){
        return actImpl.list();

    }

    @Transactional
    public ActiviteDTO getById( UUID uuid ){
        return actImpl.getById(uuid);
    }

    @Transactional
    public void update( ActiviteDTO act ){
        // if(act.getSecteur () != "")
        actImpl.update(act);
    }

    @Transactional
    public UUID insert( ActiviteDTO act ){
        // Ajout des contôles fonctionnels
        return actImpl.insert(act);
    }

    @Transactional
    public void delete( UUID uuid ){
        actImpl.delete(uuid);
    }

}
