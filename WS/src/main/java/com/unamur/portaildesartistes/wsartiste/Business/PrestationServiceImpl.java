package com.unamur.portaildesartistes.wsartiste.Business;

import com.unamur.portaildesartistes.DTO.DTO;
import com.unamur.portaildesartistes.DTO.PrestationDTO;

import com.unamur.portaildesartistes.wsartiste.datalayer.DonneePrestationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public class PrestationServiceImpl implements IService<PrestationDTO>{
    private static final Logger logger = LoggerFactory.getLogger(PrestationServiceImpl.class);
        @Autowired
        private DonneePrestationImpl prestationImpl;

        @Transactional
        public List<PrestationDTO> list(){ return prestationImpl.list(); }
        @Transactional
        public List<PrestationDTO> listByTypeId(DTO searchType, UUID uuid){ return prestationImpl.listByTypeId(searchType,uuid); }
        @Transactional
        public PrestationDTO getById( UUID uuid ){
            return prestationImpl.getById(uuid);
        }
        @Transactional
        public void update( PrestationDTO act ){ prestationImpl.update(act); }
        @Transactional
        public UUID insert( PrestationDTO act ){ return prestationImpl.insert(act); }
        @Transactional
        public void delete( UUID uuid ){
            prestationImpl.delete(uuid);
        }
}