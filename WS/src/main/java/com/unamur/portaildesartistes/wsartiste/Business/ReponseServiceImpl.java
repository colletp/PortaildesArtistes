package com.unamur.portaildesartistes.wsartiste.Business;

import com.unamur.portaildesartistes.DTO.ReponseDTO;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeReponseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
public class ReponseServiceImpl implements IService<ReponseDTO>{
    private static final Logger logger = LoggerFactory.getLogger(ReponseServiceImpl.class);

    @Autowired
    private DonneeReponseImpl repImpl;

    @Transactional
    public List<ReponseDTO>getByTrt(UUID trtId){ return repImpl.listByTrt(trtId); }
    @Transactional
    public List<ReponseDTO> listReponse(){
        return repImpl.list();
    }
    @Transactional
    public List<ReponseDTO> list(){
        return repImpl.list();
    }
    @Transactional
    public ReponseDTO getById( UUID uuid ){
        return repImpl.getById(uuid);
    }
    @Transactional
    public void update( ReponseDTO rep )throws Exception{ repImpl.update(rep); }
    @Transactional
    public UUID insert(ReponseDTO rep )throws Exception{ return repImpl.insert(rep); }
    @Transactional
    public void delete( UUID uuid )throws Exception{ repImpl.delete(uuid); }

}
