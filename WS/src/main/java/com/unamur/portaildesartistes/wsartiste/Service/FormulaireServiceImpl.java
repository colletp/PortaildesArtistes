package com.unamur.portaildesartistes.wsartiste.Service;

import com.unamur.portaildesartistes.DTO.*;
import com.unamur.portaildesartistes.wsartiste.datalayer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FormulaireServiceImpl implements IService<FormulaireDTO> {

    private static final Logger logger = LoggerFactory.getLogger(FormulaireServiceImpl.class);

    @Autowired
    private DonneeActiviteImpl actImpl;
    @Autowired
    private DonneeSecteurImpl sectImpl;
    @Autowired
    private DonneeFormulaireImpl formImpl;

    @Transactional
    public List<FormulaireDTO> list(){
        List<FormulaireDTO> formDTOList = formImpl.list();
        for( FormulaireDTO form : formDTOList ){
            form.setActivites( actImpl.getByFormId( form.getId() ) );
            for( ActiviteDTO act : form.getActivites() ){
                act.setSecteur( sectImpl.getById( act.getSecteurId() ) );
            }
        }
        return formDTOList;
    }
    
    @Transactional
    public FormulaireDTO getById( UUID uuid ){
        FormulaireDTO form= formImpl.getById(uuid);
        //form.setCitoyen( citImpl.getById( form.getCitoyenId() ) );
        return form;
    }

    @Transactional
    public void update( FormulaireDTO form ){
        formImpl.update(form);
    }

    @Transactional
    public UUID insert( FormulaireDTO form ){
        return formImpl.insert(form);
    }

    @Transactional
    public void delete( UUID uuid ){
        formImpl.delete(uuid);
    }

}