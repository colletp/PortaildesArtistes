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
    private ActiviteServiceImpl actServImpl;
    @Autowired
    private DonneeFormulaireImpl formImpl;
    @Autowired
    private SecteurServiceImpl sectServImpl;
    @Autowired
    private TraitementServiceImpl trtServImpl;
    @Autowired
    private ReponseServiceImpl repServImpl;
    @Autowired
    private DocArtisteServiceImpl docArtServImpl;

    @Autowired
    private CitoyenServiceImpl citServImpl;

    @Transactional
    public List<FormulaireDTO> list(){ return formImpl.list(); }

    @Transactional
    public List<FormulaireDTO> listByLangNoTrt(String lang){
        List<FormulaireDTO> lFormDTO = formImpl.listByLangNoTrt(lang);
        return lFormDTO;
    }

    @Transactional
    public List<FormulaireDTO> listByLangTrtNotDone(String lang){
        List<FormulaireDTO> lFormDTO = formImpl.listByLangTrtNotDone(lang);
        for( FormulaireDTO formDTO : lFormDTO )
            formDTO.setTrt( trtServImpl.listByForm( formDTO.getId() ) );
        return lFormDTO;
    }

    @Transactional
    public List<FormulaireDTO> listByLangTrtDone(String lang){
        List<FormulaireDTO> lFormDTO = formImpl.listByLangTrtDone(lang);
        for( FormulaireDTO formDTO : lFormDTO ) {
            formDTO.setTrt(trtServImpl.listByForm(formDTO.getId()));
            for( TraitementDTO trtDTO : formDTO.getTrt() ){
                trtDTO.setReponses( repServImpl.getByTrt( trtDTO.getId() ) );
                for( ReponseDTO repDTO : trtDTO.getReponses() ){
                    repDTO.setDocArt( docArtServImpl.getByReponse( repDTO.getId() ) );
                }
            }
        }
        return lFormDTO;
    }

    @Transactional
    public FormulaireDTO getById( UUID uuid ){
        FormulaireDTO form= formImpl.getById(uuid);
        form.setSecteurActivites( sectServImpl.listSecteurActiviteByFormId( uuid ) );

        List<ActiviteDTO> lAct = actServImpl.getByFormId( form.getId() );
        List<UUID> lActId = new ArrayList<>();
        for( ActiviteDTO act : lAct )
            lActId.add(act.getId());
        form.setActivitesId(lActId);

        form.setCitoyen( citServImpl.getById( form.getCitoyenId() ) );

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