package com.unamur.portaildesartistes.DTO;

import java.util.*;

public class FormulaireDTO extends DTO {

    // ******************
    // Champs/propriétés
    // ******************

    private UUID citoyenId;
    private Date dateDemande;
    private List<String> cursusAc;
    private List<String> expPro;
    private List<String> ressources;
    public enum Lang {FR,EN;}
    private Lang langue;
    private Boolean carte;
    private Boolean visa;
	private Boolean aTrairer;

    //private List<UUID> lActivitesId;
    private Collection<SecteurDTO> lSecteurs;

    private List<TraitementDTO> lTrt;

    private CitoyenDTO citDTO;
    // ******************
    // Constructeur
    // ******************
    public FormulaireDTO(){
        //lActivitesId=new ArrayList<>();

        cursusAc=new ArrayList<>();
        expPro=new ArrayList<>();
        ressources=new ArrayList<>();
    }
    // ******************
    // Setter/Getter
    // ******************

    public UUID getCitoyenId() { return citoyenId; }
    public void setCitoyenId( UUID p_id) { citoyenId = p_id; }
    public Date getDateDemande(){ return dateDemande;}
    public void setDateDemande(Date d){ dateDemande=d;}
    public List<String> getCursusAc(){ return cursusAc==null?new ArrayList<>():cursusAc;}
    public void setCursusAc(List<String> ls){ cursusAc=ls;}
    public List<String> getExpPro(){ return expPro==null?new ArrayList<>():expPro;}
    public void setExpPro(List<String> ls){ expPro=ls;}
    public List<String> getRessources(){ return ressources==null?new ArrayList<>():ressources;}
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
    public Boolean getATraiter(){ return aTrairer;}
    public void setATraiter(Boolean b){ aTrairer=b;}

    //public void setActivitesId(List<UUID> l){lActivitesId=l;}
    //public List<UUID> getActivitesId(){return lActivitesId;}

    public void setSecteurActivites(Collection<SecteurDTO> ls){lSecteurs=ls;}
    public Collection<SecteurDTO> getSecteurActivites(){return lSecteurs;}

    public void setTrt(List<TraitementDTO> l){lTrt=l;}
    public List<TraitementDTO> getTrt(){return lTrt;}
    public CitoyenDTO getCitoyen() { return citDTO; }
    public void setCitoyen( CitoyenDTO cit) { citDTO=cit; }

    // ******************
    // Fonctions
    // ******************

}