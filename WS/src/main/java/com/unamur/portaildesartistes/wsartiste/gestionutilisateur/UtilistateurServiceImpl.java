package com.unamur.portaildesartistes.wsartiste.gestionutilisateur;

import com.unamur.portaildesartistes.DTO.*;
import com.unamur.portaildesartistes.wsartiste.datalayer.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UtilistateurServiceImpl implements UtilistateurService {

    private static final Logger logger = LoggerFactory.getLogger(UtilistateurServiceImpl.class);

    @Autowired
    private DonneeCitoyenImpl citImpl;
    @Autowired
    private DonneeUtilisateurImpl usrImpl;
    @Autowired
    private DonneeAdresseImpl adrImpl;
    @Autowired
    private DonneeRoleImpl roleImpl;
    @Autowired
    private DonneeDocArtisteImpl docImpl;
    @Autowired
    private DonneePrestationImpl prestImpl;
    @Autowired
    private DonneeActiviteImpl actImpl;
    @Autowired
    private DonneeSecteurImpl sectImpl;
    @Autowired
    private DonneeFormulaireImpl formImpl;
    @Autowired
    private DonneeCommanditaireImpl comImpl;
    @Autowired
    private DonneeEntrepriseImpl entrImpl;

    private UtilisateurDTO utilisateurDTO;

    public UtilistateurServiceImpl(){
        utilisateurDTO = new UtilisateurDTO();
    }

    @Transactional
    public List<CitoyenDTO> listCitoyen(){
        List<CitoyenDTO> citDTOList = citImpl.list();
        for( CitoyenDTO usr : citDTOList ){
            usr.setResideAdr( adrImpl.getById( usr.getReside() ) );
            usr.setRoles( roleImpl.getByCitoyenId( usr.getId() ) );
            usr.setFormulaires( formImpl.getByCitoyenId( usr.getId() ) );
            for( FormulaireDTO form : usr.getFormulaires() ){
                form.setActivites( actImpl.getByFormId( form.getId() ) );
                for( ActiviteDTO act : form.getActivites() ){
                    act.setSecteur( sectImpl.getById( act.getSecteurId() ) );
                }
            }
            usr.setDocArtistes( docImpl.getByCitoyenId( usr.getId() ) );
            for( DocArtisteDTO doc : usr.getDocArtistes() ){
                doc.setActivites( actImpl.getByDocId( doc.getId() ) );
                for( ActiviteDTO act : doc.getActivites() ){
                    act.setSecteur( sectImpl.getById( act.getSecteurId() ) );
                }
                doc.setPrestations( prestImpl.getByDocId( doc.getId() ) );
                for( PrestationDTO prest : doc.getPrestations() ){
                    CommanditaireDTO com = comImpl.getById( prest.getCommanditaireId() );
                        com.setCitoyen(  citImpl.getById( com.getCitoyenId() ) );
                        EntrepriseDTO entr = entrImpl.getById( com.getEntrepriseId() );
                        if(entr!=null) {
                            entr.setContact(citImpl.getById(entr.getContactId()));
                            com.setEntreprise(entr);
                        }
                        prest.setCommanditaire( com );
                    prest.setSeDeroule( adrImpl.getById(prest.getSeDerouleId() ) );
                    prest.setActivite( actImpl.getById(prest.getActiviteId() ) );
                }
            }

        }
        return citDTOList;
    }
    @Transactional
    public List<UtilisateurDTO> listUtilisateur(){
        List<UtilisateurDTO> usrDTOList = usrImpl.list();
        for( UtilisateurDTO usr : usrDTOList ){
            logger.error( usr.getUsername() );
            logger.error( usr.getId().toString() );
            usr.setCitoyen( citImpl.getById( usr.getId() ) );
            usr.getCitoyen().setResideAdr( adrImpl.getById( usr.getCitoyen().getReside() ) );
        }
        return usrDTOList;
    }

    @Transactional
    public UUID insertOK(){
        utilisateurDTO.setUsername("log"+new Date().getTime());
        return citImpl.insert(utilisateurDTO);
    }

    @Transactional
    public void insertAndFail(){
        //authUser.setNomUtilisateur("login"+new Date().getTime());
        //donneeUtilisateur.insert(authUser);
        throw new RuntimeException("Hello this is an error message");
    }

}