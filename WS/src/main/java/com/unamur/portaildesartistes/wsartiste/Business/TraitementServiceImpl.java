package com.unamur.portaildesartistes.wsartiste.Business;

import com.unamur.portaildesartistes.DTO.TraitementDTO;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeTraitementImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
public class TraitementServiceImpl implements IService<TraitementDTO> {
    private static final Logger logger = LoggerFactory.getLogger(ActiviteServiceImpl.class);
    
    @Autowired
    private DonneeTraitementImpl traitImpl;

    @Transactional
    public List<TraitementDTO> listActivite(){
        return traitImpl.list();
    }
    @Transactional
    public List<TraitementDTO> list(){
        return traitImpl.list();
    }
    @Transactional
    public TraitementDTO getById( UUID uuid ){
        return traitImpl.getById(uuid);
   }
    @Transactional
    public void update( TraitementDTO act ){ traitImpl.update(act); }
    @Transactional
    public UUID insert( TraitementDTO act ){ return traitImpl.insert(act); }
    @Transactional
    public void delete( UUID uuid ){
        traitImpl.delete(uuid);
    }


}
