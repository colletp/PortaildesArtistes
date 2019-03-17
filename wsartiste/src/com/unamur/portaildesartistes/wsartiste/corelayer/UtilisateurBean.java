package com.unamur.portaildesartistes.wsartiste.corelayer;

import java.io.Serializable;

public class UtilisateurBean implements Serializable {

    private Integer id;
    private String nomUtilisateur;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }
}