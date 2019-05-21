package com.unamur.portaildesartistes.wsartiste.Business;

import com.unamur.portaildesartistes.DTO.DocArtisteDTO;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeDocArtisteImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
public class DocArtisteServiceImpl implements IService<DocArtisteDTO> {
    private static final Logger logger = LoggerFactory.getLogger(DocArtisteServiceImpl.class);

    @Autowired
    private DonneeDocArtisteImpl docArtImpl;

    @Transactional
    public List<DocArtisteDTO> getByReponse(UUID repId){ return docArtImpl.getByReponse(repId); }
    @Transactional
    public List<DocArtisteDTO> listByLang(String lang){ return docArtImpl.listByLang(lang); }
    @Transactional
    public List<DocArtisteDTO> list(){ return docArtImpl.list(); }
    @Transactional
    public DocArtisteDTO getById( UUID uuid ){
        return docArtImpl.getById(uuid);
    }
    @Transactional
    public void update( DocArtisteDTO act ){ docArtImpl.update(act); }
    @Transactional
    public UUID insert( DocArtisteDTO act ){ return docArtImpl.insert(act); }
    @Transactional
    public void delete( UUID uuid ){
        docArtImpl.delete(uuid);
    }

}
