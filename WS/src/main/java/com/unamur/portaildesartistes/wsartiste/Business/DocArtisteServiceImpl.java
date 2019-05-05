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
    private DonneeDocArtisteImpl artisteImpl;

    @Transactional
    public List<DocArtisteDTO> list(){ return artisteImpl.list(); }
    @Transactional
    public DocArtisteDTO getById( UUID uuid ){
        return artisteImpl.getById(uuid);
    }
    @Transactional
    public void update( DocArtisteDTO act ){ artisteImpl.update(act); }
    @Transactional
    public UUID insert( DocArtisteDTO act ){ return artisteImpl.insert(act); }
    @Transactional
    public void delete( UUID uuid ){
        artisteImpl.delete(uuid);
    }

}
