package com.unamur.portaildesartistes.wsartiste.Business;

import com.unamur.portaildesartistes.DTO.SecteurDTO;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeActiviteImpl;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeSecteurImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
public class SecteurServiceImpl implements IService<SecteurDTO> {
    private static final Logger logger = LoggerFactory.getLogger(SecteurServiceImpl.class);

    @Autowired
    private DonneeSecteurImpl sectImpl;
    @Autowired
    private DonneeActiviteImpl actImpl;
    //private ActiviteServiceImpl actImpl;

    @Transactional
    public List<SecteurDTO> listSecteurActivite(){
        List<SecteurDTO> ls = list();
        for( SecteurDTO s : ls )
            s.setActivites( actImpl.getBySecteurId( s.getId() ) );
            //s.setActivites( actImpl.listBySecteur( s.getId() ) );
        return ls;
    }
    @Transactional
    public List<SecteurDTO> list(){
        return sectImpl.list();
    }
    @Transactional
    public SecteurDTO getById( UUID uuid ){
        return sectImpl.getById(uuid);
    }
    @Transactional
    public void update( SecteurDTO sect )throws Exception{ sectImpl.update(sect);}
    @Transactional
    public UUID insert( SecteurDTO sect )throws Exception{ return sectImpl.insert(sect); }
    @Transactional
    public void delete( UUID uuid )throws Exception{ sectImpl.delete(uuid); }
    @Transactional
    public List<SecteurDTO> listSecteurActiviteByFormId(UUID formId){
        List<SecteurDTO> lSectDTO = sectImpl.list();//getByFormId(formId);
        for( SecteurDTO sectDTO : lSectDTO){
            sectDTO.setActivites( actImpl.getBySectFormId( formId,sectDTO.getId() ) );
        }
        return lSectDTO;
    }
    @Transactional
    public List<SecteurDTO> listSecteurActiviteByDocId(UUID docId){
        List<SecteurDTO> lSectDTO = sectImpl.getByDocId(docId);
        for( SecteurDTO sectDTO : lSectDTO){
            sectDTO.setActivites( actImpl.getBySectDocId( docId,sectDTO.getId() ) );
        }
        return lSectDTO;
    }

}
