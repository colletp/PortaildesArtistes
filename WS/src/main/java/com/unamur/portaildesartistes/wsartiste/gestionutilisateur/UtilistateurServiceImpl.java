package com.unamur.portaildesartistes.wsartiste.gestionutilisateur;

import com.unamur.portaildesartistes.DTO.*;
import com.unamur.portaildesartistes.wsartiste.datalayer.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UtilistateurServiceImpl implements UtilistateurService {

    @Autowired
    private DonneeCitoyenImpl usrImpl;
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

    private CitoyenDTO citoyenDTO;

    public UtilistateurServiceImpl(){
        citoyenDTO = new CitoyenDTO();
    }

    @Transactional
    public List<CitoyenDTO> list(){
        List<CitoyenDTO> usrDTOList = usrImpl.list();
        for( CitoyenDTO usr : usrDTOList ){
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
                        com.setCitoyen(  usrImpl.getById( com.getCitoyenId() ) );
                        EntrepriseDTO entr = entrImpl.getById( com.getEntrepriseId() );
                        if(entr!=null) {
                            entr.setContact(usrImpl.getById(entr.getContactId()));
                            com.setEntreprise(entr);
                        }
                        prest.setCommanditaire( com );
                    prest.setSeDeroule( adrImpl.getById(prest.getSeDerouleId() ) );
                    prest.setActivite( actImpl.getById(prest.getActiviteId() ) );
                }
            }

        }
        return usrDTOList;
    }

    @Transactional
    public UUID insertOK(){
        citoyenDTO.setNom("log"+new Date().getTime());
        return usrImpl.insert(citoyenDTO);
    }

    @Transactional
    public void insertAndFail(){
        //authUser.setNomUtilisateur("login"+new Date().getTime());
        //donneeUtilisateur.insert(authUser);
        throw new RuntimeException("Hello this is an error message");
    }

}