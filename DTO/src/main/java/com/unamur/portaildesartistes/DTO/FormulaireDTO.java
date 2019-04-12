package com.unamur.portaildesartistes.DTO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class FormulaireDTO implements Serializable {

    // ******************
    // Champs/propriétés
    // ******************

    private UUID id;
    private UUID citoyenId;
    private Timestamp dateDemande;
    private List<String> cursurAc;
    private List<String> expPro;
    private List<String> ressources;
    private String langue;
    private Boolean carte;
    private Boolean visa;

    public List<ActiviteDTO> lActivites;


    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public UUID getId() { return id; }
    public void setId( UUID p_id) { this.id = p_id; }
    public UUID getCitoyenIdId() { return citoyenId; }
    public void setCitoyenIdId( UUID p_id) { this.citoyenId = p_id; }
    public Timestamp getDateDemande(){ return dateDemande;}
    public void setDateDemande(Timestamp d){ this.dateDemande=d;}
    public List<String> getCursusAc(){ return cursurAc;}
    public void setCursurAc(List<String> ls){ this.cursurAc=ls;}
    public List<String> getExpPro(){ return expPro;}
    public void setExpPro(List<String> ls){ this.expPro=ls;}
    public List<String> getRessources(){ return ressources;}
    public void setRessources(List<String> ls){ this.ressources=ls;}
    public String getLangue(){ return langue;}
    public void setLangue(String s){ this.langue=s;}
    public Boolean getCarte(){ return carte;}
    public void setCarte(Boolean b){ this.carte=b;}
    public Boolean getVisa(){ return visa;}
    public void setVisa(Boolean b){ this.visa=b;}

    public void setActivites(List<ActiviteDTO> l){this.lActivites=l;}
    public List<ActiviteDTO> getActivites(){return this.lActivites;}

    // ******************
    // Fonctions
    // ******************

}