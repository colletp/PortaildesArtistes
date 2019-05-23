package com.unamur.portaildesartistes.wsartiste.Business;

import com.unamur.portaildesartistes.DTO.TraitementDTO;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeTraitementImpl;
import com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat.UtilisateurServiceFront;
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
    private DonneeTraitementImpl trtImpl;
    @Autowired
    private GestionnaireServiceImpl gestServImpl;
    @Autowired
    private FormulaireServiceImpl formServImpl;
    @Autowired
    private UtilisateurServiceFront usrServFront;

    @Transactional
    public List<TraitementDTO> listByForm(UUID formId){
        List<TraitementDTO> lTrtDTO = trtImpl.listByForm( formId );
        for( TraitementDTO trtDTO : lTrtDTO) {
            trtDTO.setGest(gestServImpl.getById(trtDTO.getGestId()));
        }
        return lTrtDTO;
    }

    @Transactional
    public List<TraitementDTO> listByLang(String lang){
        List<TraitementDTO> lTrtDTO = trtImpl.listByLang( lang );
        for( TraitementDTO trtDTO : lTrtDTO) {
            trtDTO.setGest(gestServImpl.getById(trtDTO.getGestId()));
            trtDTO.setForm( formServImpl.getById( trtDTO.getFormId() ) );
        }
        return lTrtDTO;
    }
    @Transactional
    public List<TraitementDTO> list(){
        List<TraitementDTO> lTrtDTO = trtImpl.list();
        for( TraitementDTO trtDTO : lTrtDTO){
            trtDTO.setGest( gestServImpl.getById( trtDTO.getGestId() ) );
            trtDTO.setForm( formServImpl.getById( trtDTO.getFormId() ) );
        }
        return lTrtDTO;
    }
    @Transactional
    public TraitementDTO getById( UUID uuid ){
        TraitementDTO trtDTO = trtImpl.getById(uuid);
        trtDTO.setGest( gestServImpl.getById( trtDTO.getGestId() ) );
        trtDTO.setForm( formServImpl.getById( trtDTO.getFormId() ) );
        return trtDTO;
   }
    @Transactional
    public void update( TraitementDTO trtDTO )throws Exception{ trtImpl.update(trtDTO); }
    @Transactional
    public UUID insert( TraitementDTO trtDTO )throws Exception{ return trtImpl.insert(trtDTO); }
    @Transactional
    public void delete( UUID uuid )throws Exception{ trtImpl.delete(uuid); }
}
