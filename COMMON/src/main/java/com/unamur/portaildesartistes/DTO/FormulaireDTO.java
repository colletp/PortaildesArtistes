package com.unamur.portaildesartistes.DTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class FormulaireDTO extends DTO {

    // ******************
    // Champs/propriétés
    // ******************

    private UUID citoyenId;
    private Timestamp dateDemande;
    private List<String> cursusAc;
    private List<String> expPro;
    private List<String> ressources;
    public enum Lang {FR,EN;}
    private Lang langue;
    private Boolean carte;
    private Boolean visa;

    private List<UUID> lActivitesId;
    //private List<ActiviteDTO> lActivites;
    private Collection<SecteurDTO> lSecteurs;


    // ******************
    // Constructeur
    // ******************
    public FormulaireDTO(){
        lActivitesId=new ArrayList<>();

        cursusAc=new ArrayList<>();
        expPro=new ArrayList<>();
        ressources=new ArrayList<>();
    }
    // ******************
    // Setter/Getter
    // ******************

    public UUID getCitoyenId() { return citoyenId; }
    public void setCitoyenId( UUID p_id) { citoyenId = p_id; }
    public Timestamp getDateDemande(){ return dateDemande;}
    public void setDateDemande(Timestamp d){ dateDemande=d;}
    public List<String> getCursusAc(){ return cursusAc;}
    public void setCursusAc(List<String> ls){ cursusAc=ls;}
    public List<String> getExpPro(){ return expPro;}
    public void setExpPro(List<String> ls){ expPro=ls;}
    public List<String> getRessources(){ return ressources;}
    public void setRessources(List<String> ls){ ressources=ls;}
    public String getLangue(){
        return langue.toString();
    }
    public void setLangue(String s){
        langue = Lang.valueOf(s);
    }
    public void setLangue(Lang l){langue=l;}
    public Boolean getCarte(){ return carte;}
    public void setCarte(Boolean b){ carte=b;}
    public Boolean getVisa(){ return visa;}
    public void setVisa(Boolean b){ visa=b;}

    public void setActivitesId(List<UUID> l){lActivitesId=l;}
    public List<UUID> getActivitesId(){return lActivitesId;}
    public void setSecteurActivites(Collection<SecteurDTO> ls){lSecteurs=ls;}
    public Collection<SecteurDTO> getSecteurActivites(){return lSecteurs;}

    // ******************
    // Fonctions
    // ******************

}