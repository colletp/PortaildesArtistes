package com.unamur.portaildesartistes.wsartiste.Business;

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
    private DonneeFormulaireImpl formImpl;
    @Autowired
    private DonneeSecteurImpl sectImpl;

    @Transactional
    public List<FormulaireDTO> list(){ return formImpl.list(); }
    @Transactional
    public FormulaireDTO getById( UUID uuid ){
        FormulaireDTO form= formImpl.getById(uuid);
        form.setActivites( actImpl.getByFormId( form.getId() ) );
        List<SecteurDTO> lSect = sectImpl.list();
        for( SecteurDTO sect : lSect ){
            for( ActiviteDTO act : form.getActivites() ){
                if( act.getSecteurId()==sect.getId() )
                    sect.add(act);
            }
        }
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