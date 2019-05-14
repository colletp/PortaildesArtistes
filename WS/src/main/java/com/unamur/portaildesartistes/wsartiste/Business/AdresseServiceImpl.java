package com.unamur.portaildesartistes.wsartiste.Business;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
import com.unamur.portaildesartistes.DTO.AdresseDTO;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeActiviteImpl;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeAdresseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
public class AdresseServiceImpl implements IService<AdresseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(AdresseServiceImpl.class);

    @Autowired
    private DonneeAdresseImpl adrImpl;

    @Transactional
    public List<AdresseDTO> list(){
        return adrImpl.list();
    }
    @Transactional
    public AdresseDTO getById( UUID uuid ){
        return adrImpl.getById(uuid);
    }
    @Transactional
    public void update( AdresseDTO act ){ adrImpl.update(act); }
    @Transactional
    public UUID insert( AdresseDTO act ){ return adrImpl.insert(act); }
    @Transactional
    public void delete( UUID uuid ){ adrImpl.delete(uuid); }

}
