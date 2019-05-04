package com.unamur.portaildesartistes.webclient.dataForm;

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
    // Setter/Getter
    // ******************
    public Utilisateur getUtilisateur(){return utilisateur; }
    public void setUtilisateur(Utilisateur usr) { this.utilisateur = usr; }

    public Citoyen getCitoyen(){return citoyen; }
    public void setCitoyen(Citoyen cit) { this.citoyen = cit; }

    public Adresse getAdresse(){return adresse; }
    public void setAdresse(Adresse adr) { this.adresse = adr; }

    // ******************
    // Fonctions
    // ******************
    public UtilisateurDTO getDTO()throws ParseException {
        logger.error("getDTO");
        UtilisateurDTO usr = utilisateur.getDTO();
        logger.error("usr OK");
        usr.setCitoyen( citoyen.getDTO() );
        logger.error("cit OK");
        usr.getCitoyen().setResideAdr( adresse.getDTO() );
        logger.error("adr OK");
        return usr;
    }
}
