package com.unamur.portaildesartistes.wsartiste.Business;

import com.unamur.portaildesartistes.DTO.*;
import com.unamur.portaildesartistes.wsartiste.datalayer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

        List<ActiviteDTO> lAct = actImpl.getByFormId( form.getId() );
        List<UUID> lActId = new ArrayList<>();

        List<SecteurDTO> lSect = sectImpl.list();
        Set<SecteurDTO> lSectForm = new HashSet<>();
        for( ActiviteDTO act : lAct ){
            lActId.add(act.getId());
            for( SecteurDTO sect : lSect ){
                if( act.getSecteurId().toString().equals(sect.getId().toString()) ){
                    if(!lSectForm.contains(sect))lSectForm.add(sect);
                    sect.addActivite(act);
                }
            }
        }
        form.setSecteurActivites( lSectForm );
        form.setActivitesId(lActId);

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