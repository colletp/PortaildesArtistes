package com.unamur.portaildesartistes.wsartiste.corelayer;

import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeUtilisateur;

import java.io.Serializable;

import org.springframework.context.annotation.Bean;

public class UtilisateurBean implements Serializable {

    // ******************
    // Champs/propriétés
    // ******************

    private Integer id;
    private String nomUtilisateur;
    private String login;
    private String motDePasse;
    private boolean actif;

    // ******************
    // Constructeur
    // ******************

    public UtilisateurBean() {
        super();
    }

    public UtilisateurBean(String p_nomUtilisateur, String p_login, String p_motDePasse, boolean p_actif) {
        this.nomUtilisateur = p_nomUtilisateur;
        this.login = p_login;
        this.motDePasse = p_motDePasse;
        this.actif = p_actif;
    }

    public UtilisateurBean(DonneeUtilisateur p_donneeUtilisateur) {
        /*
        this.nomUtilisateur = p_donneeUtilisateur;
        this.login = p_donneeUtilisateur;
        this.motDePasse = p_donneeUtilisateur;
        this.actif = p_donneeUtilisateur.;
        */
    }

    // ******************
    // Setter/Getter
    // ******************

    public Integer getId() { return id; }

    public void setId( Integer p_id) { this.id = p_id; }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    // ******************
    // Fonctions
    // ******************

}