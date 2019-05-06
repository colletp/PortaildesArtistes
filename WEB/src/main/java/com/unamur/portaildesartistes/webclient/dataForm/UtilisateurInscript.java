package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.AdresseDTO;
import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

public class UtilisateurInscript {
    private static final Logger logger = LoggerFactory.getLogger(com.unamur.portaildesartistes.webclient.dataForm.UtilisateurInscript.class);

/* Cette classe est un assemblage de formulaires, retournant un Utilisateur avec un citoyen et une adresse. */
    private Utilisateur utilisateur;
    private Citoyen citoyen;
    private Adresse adresse;

    // ******************
    // Constructor
    // ******************

    public UtilisateurInscript(){
        setUtilisateur(new Utilisateur());
        setCitoyen(new Citoyen());
        setAdresse(new Adresse());
    }
    public UtilisateurInscript( UtilisateurDTO usr ){
        setUtilisateur(new Utilisateur());
        setCitoyen(new Citoyen());
        setAdresse(new Adresse());

        setUtilisateur(usr);
        setCitoyen(usr.getCitoyen());
        setAdresse(usr.getCitoyen().getResideAdr());
    }
    // ******************
    // Setter/Getter
    // ******************
    public Utilisateur getUtilisateur(){return utilisateur; }
    public void setUtilisateur(Utilisateur usr) { utilisateur = usr; }
    public void setUtilisateur(UtilisateurDTO usr) {utilisateur.setFromDTO(usr);}

    public Citoyen getCitoyen(){return citoyen;}
    public void setCitoyen(Citoyen cit) {citoyen = cit;}
    public void setCitoyen(CitoyenDTO cit) {citoyen.setFromDTO(cit);}

    public Adresse getAdresse(){return adresse;}
    public void setAdresse(Adresse adr){adresse = adr;}
    public void setAdresse(AdresseDTO adr){adresse.setFromDTO(adr);}

    // ******************
    // Fonctions
    // ******************
    public UtilisateurDTO getDTO()throws ParseException {
        UtilisateurDTO usr = utilisateur.getDTO();
        usr.setCitoyen( citoyen.getDTO() );
        usr.getCitoyen().setResideAdr( adresse.getDTO() );
        return usr;
    }
}
