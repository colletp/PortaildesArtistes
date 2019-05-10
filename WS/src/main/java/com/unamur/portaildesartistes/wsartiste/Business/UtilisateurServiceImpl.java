package com.unamur.portaildesartistes.wsartiste.Business;

import com.unamur.portaildesartistes.DTO.*;
import com.unamur.portaildesartistes.wsartiste.datalayer.*;

import com.unamur.portaildesartistes.wsartiste.security.WebSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class UtilisateurServiceImpl implements IService<UtilisateurDTO> {

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurServiceImpl.class);

    //@Autowired
    //PasswordEncoder encoder;

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

    @Transactional
    public List<CitoyenDTO> listCitoyen(){
        List<CitoyenDTO> citDTOList = citImpl.list();
        for( CitoyenDTO usr : citDTOList ){
            usr.setResideAdr( adrImpl.getById( usr.getReside() ) );
            usr.setRoles( roleImpl.getByCitoyenId( usr.getId() ) );
            usr.setFormulaires( formImpl.getByCitoyenId( usr.getId() ) );
            for( FormulaireDTO form : usr.getFormulaires() ){
                /*
                form.setActivitesId(  );
                for( ActiviteDTO act : form.getActivites() ){
                    act.setSecteur( sectImpl.getById( act.getSecteurId() ) );
                }*/
            }
            usr.setDocArtistes( docImpl.getByCitoyenId( usr.getId() ) );
            for( DocArtisteDTO doc : usr.getDocArtistes() ){
                doc.setActivites( actImpl.getByDocId( doc.getId() ) );
                for( ActiviteDTO act : doc.getActivites() ){
                    act.setSecteur( sectImpl.getById( act.getSecteurId() ) );
                }
                doc.setPrestations( prestImpl.listByTypeId( doc, doc.getId() ) );
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
    public List<UtilisateurDTO> list(){ return usrImpl.list(); }

    @Transactional
    public UtilisateurDTO getById( UUID uuid ){
        UtilisateurDTO usr= usrImpl.getById(uuid);
        usr.setCitoyen( citImpl.getById( uuid ) );
        usr.getCitoyen().setResideAdr( adrImpl.getById( usr.getCitoyen().getReside() ) );
        return usr;
    }

    @Transactional
    public UUID getUuidByName( String userName ){
        UtilisateurDTO usr= usrImpl.getByName(userName);
        return usr.getId();
    }

    @Transactional
    public void update( UtilisateurDTO usr ){
        if( !usr.getPassword().isEmpty() ) {
            usr.setPassword(WebSecurityConfig.encoder().encode(usr.getPassword()));
            usrImpl.update(usr);
        }
        if(usr.getCitoyen()!=null){
            citImpl.update(usr);
            if( usr.getCitoyen().getResideAdr()!=null )
                adrImpl.update(usr.getCitoyen().getResideAdr());
        }
    }

    @Transactional
    public UUID insert( UtilisateurDTO usr ){
        usr.setPassword( WebSecurityConfig.encoder().encode(usr.getPassword()) );
        UUID cit = null;
        if( usr.getCitoyen()!=null ){
            UUID adr=null;
            if( usr.getCitoyen().getResideAdr()!=null )
                adr=adrImpl.insert(usr.getCitoyen().getResideAdr());
            else
                throw new IllegalArgumentException("Insertion d'un citoyen sans adresse");
            usr.getCitoyen().setReside(adr);
            usr.setPassword(WebSecurityConfig.encoder().encode(usr.getPassword()));
            cit=citImpl.insert(usr);
        }
        else
            throw new IllegalArgumentException("Insertion d'un utilisateur sans citoyen");
        return cit;
    }

    @Transactional
    public void delete( UUID uuid ){
        usrImpl.delete(uuid);
    }

}