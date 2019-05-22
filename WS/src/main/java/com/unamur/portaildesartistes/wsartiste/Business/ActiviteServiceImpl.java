package com.unamur.portaildesartistes.wsartiste.Business;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeActiviteImpl;

import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeFormulaireImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.management.ServiceNotFoundException;
import java.util.List;
import java.util.UUID;

@Component
public class ActiviteServiceImpl implements IService<ActiviteDTO> {
    private static final Logger logger = LoggerFactory.getLogger(ActiviteServiceImpl.class);

    @Autowired
    private DonneeActiviteImpl actImpl;
    @Autowired
    private DonneeFormulaireImpl formImpl;

    @Transactional
    public List<ActiviteDTO> getByFormId( UUID formId ){ return actImpl.getByFormId(formId); }

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
        throw new UnsupportedOperationException("Don't update an activité");
    }
    @Transactional
    public UUID insert( ActiviteDTO actDTO ){
        throw new UnsupportedOperationException("Use instead insert( ActiviteDTO actDTO, UUID formId )");
    }
    @Transactional
    public UUID insert( ActiviteDTO actDTO, UUID formId ){
        UUID actId = actImpl.insert(actDTO);
        formImpl.insertFormAct(actId,formId);
        return actId;
    }

    @Transactional
    public void delete( UUID uuid ){ 
		actImpl.delete(uuid); 
	}
    @Transactional
    public void deleteByForm( UUID uuid ){
		actImpl.deleteByForm(uuid);
	}

    @Transactional
    public List<ActiviteDTO> listBySecteur(UUID uuid){
        return actImpl.getBySecteurId(uuid);
    }

}
