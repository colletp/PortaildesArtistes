package com.unamur.portaildesartistes.wsartiste.Business;

import com.unamur.portaildesartistes.DTO.*;

import com.unamur.portaildesartistes.wsartiste.datalayer.*;
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
    private DonneeAdresseImpl adrImpl;
    @Autowired
    private DonneeCommanditaireImpl comImpl;
    @Autowired
    private DonneeActiviteImpl actImpl;
    @Autowired
    private DonneeDocArtisteImpl docImpl;
    @Autowired
    private DonneeCitoyenImpl cytImpl;
    @Autowired
    private DonneeReponseImpl repImpl;
    @Autowired
    private DonneeEntrepriseImpl entImpl;

    @Autowired
        private DonneePrestationImpl prestationImpl;

        @Transactional
        public List<PrestationDTO> list(){

            List<PrestationDTO> objList;

            objList = prestationImpl.list();

            objList.forEach((objPrest) -> {
                        objPrest.setActivite(actImpl.getById(objPrest.getActiviteId()));
                        objPrest.setSeDeroule(adrImpl.getById(objPrest.getSeDerouleId()));
                        DocArtisteDTO objDocArtiste = docImpl.getById(objPrest.getDocArtisteId());
                        if (objDocArtiste.getReponseId() != null)
                            objDocArtiste.setReponse(repImpl.getById(objDocArtiste.getReponseId()));
                        if (objDocArtiste.getCitoyenId() != null)
                            objDocArtiste.setCitoyen(cytImpl.getById(objDocArtiste.getCitoyenId()));
                        objPrest.setDocArtiste(objDocArtiste);
                        CommanditaireDTO objCommanditaire = comImpl.getById(objPrest.getCommanditaireId());
                        if (objCommanditaire.getCitoyenId() != null)
                            objCommanditaire.setCitoyen(cytImpl.getById(objCommanditaire.getCitoyenId()));
                        if (objCommanditaire.getEntrepriseId() != null)
                            objCommanditaire.setEntreprise(entImpl.getById(objCommanditaire.getEntrepriseId()));
                        objPrest.setCommanditaire(objCommanditaire);}
                        );

            return objList;

        }

        @Transactional
        public List<PrestationDTO> listByTypeId(DTO searchType, UUID uuid){

            List<PrestationDTO> objList;

            objList = prestationImpl.listByTypeId(searchType,uuid);

            objList.forEach((objPrest) -> {
                objPrest.setSeDeroule(adrImpl.getById(objPrest.getSeDerouleId()));
                objPrest.setActivite(actImpl.getById(objPrest.getActiviteId()));
                DocArtisteDTO objDocArtiste = docImpl.getById(objPrest.getDocArtisteId());
                if (objDocArtiste.getReponseId() != null)
                    objDocArtiste.setReponse(repImpl.getById(objDocArtiste.getReponseId()));
                if (objDocArtiste.getCitoyenId() != null)
                    objDocArtiste.setCitoyen(cytImpl.getById(objDocArtiste.getCitoyenId()));
                objPrest.setDocArtiste(objDocArtiste);
                objPrest.setCommanditaire(comImpl.getById(objPrest.getCommanditaireId()));}
            );

            return objList;

        }

        @Transactional
        public PrestationDTO getById( UUID uuid ){

            PrestationDTO objPrest;

            objPrest = prestationImpl.getById(uuid);

            objPrest.setSeDeroule(adrImpl.getById(objPrest.getSeDerouleId()));
            objPrest.setDocArtiste(docImpl.getById(objPrest.getDocArtisteId()));
            objPrest.setActivite(actImpl.getById(objPrest.getActiviteId()));
            objPrest.setCommanditaire(comImpl.getById(objPrest.getCommanditaireId()));

            return objPrest;
        }

        @Transactional
        public void update( PrestationDTO act ){
            //TODO
            prestationImpl.update(act);
        }

        @Transactional
        public UUID insert( PrestationDTO act ){
            // TODO
            return prestationImpl.insert(act);
        }
        @Transactional
        public void delete( UUID uuid ){

            prestationImpl.delete(uuid);
        }
}