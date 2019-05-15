package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.AdresseDTO;
import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

public class UtilisateurInscript extends Utilisateur{
    private static final Logger logger = LoggerFactory.getLogger(com.unamur.portaildesartistes.webclient.dataForm.UtilisateurInscript.class);

/* Cette classe est un assemblage de formulaires, retournant un Utilisateur avec un citoyen et une adresse. */
    //private Utilisateur utilisateur;
    private Citoyen citoyen;
    private Adresse adresse;

    // ******************
    // Constructor
    // ******************

    public UtilisateurInscript(){
        setCitoyen(new Citoyen());
        setAdresse(new Adresse());
    }
    public UtilisateurInscript( UtilisateurDTO usr ){
        setCitoyen(new Citoyen());
        setAdresse(new Adresse());
        setFromDTO(usr);
    }
    // ******************
    // Setter/Getter
    // ******************
    public Utilisateur getUtilisateur(){return this; }
    public void setUtilisateur(Utilisateur usr) { setUsername(usr.getUsername());setPassword(usr.getPassword()); }
    public void setUtilisateur(UtilisateurDTO usr) {super.setFromDTO(usr);}

    public Citoyen getCitoyen(){return citoyen;}
    public void setCitoyen(Citoyen cit) {citoyen = cit;}
    public void setCitoyen(CitoyenDTO cit) {citoyen.setFromDTO(cit);}

    public Adresse getAdresse(){return adresse;}
    public void setAdresse(Adresse adr){adresse = adr;}
    public void setAdresse(AdresseDTO adr){adresse.setFromDTO(adr);}

    // ******************
    // Fonctions
    // ******************
    @Override
    public UtilisateurDTO getDTO(){
        UtilisateurDTO usr = super.getDTO();
        usr.setCitoyen( citoyen.getDTO() );
        usr.setId(usr.getCitoyen().getId());
        usr.getCitoyen().setResideAdr( adresse.getDTO() );
        usr.getCitoyen().setReside(usr.getCitoyen().getResideAdr().getId());
        return usr;
    }
    @Override
    public void setFromDTO(UtilisateurDTO usr){
        setUtilisateur(usr);
        setCitoyen(usr.getCitoyen());
        setAdresse(usr.getCitoyen().getResideAdr());
    }
}
