package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;

import java.text.ParseException;

public class UtilisateurInscript {

/* Cette classe est un assemblage de formulaires, retournant un Utilisateur avec un citoyen et une adresse. */
    private Utilisateur usr;
    private Citoyen cit;
    private Adresse adr;

    // ******************
    // Setter/Getter
    // ******************
    public Utilisateur getUtilisateur(){return usr; }
    public void setUtilisateur(Utilisateur usr) { this.usr = usr; }

    public Citoyen getCitoyen(){return cit; }
    public void setCitoyen(Citoyen cit) { this.cit = cit; }

    public Adresse getAdresse(){return adr; }
    public void setAdresse(Adresse adr) { this.adr = adr; }

    // ******************
    // Fonctions
    // ******************
    public UtilisateurDTO getDTO()throws ParseException {
        UtilisateurDTO usr = this.usr.getDTO();
        usr.setCitoyen( this.cit.getDTO() );
        usr.getCitoyen().setResideAdr( adr.getDTO() );
        return usr;
    }
}
