package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.FormulaireDTO;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Formulaire extends DataForm<FormulaireDTO> {

    // ******************
    // Champs/propriétés
    // ******************

    private String citoyenId;
    private String dateDemande;
    private List<String> cursurAc;
    private List<String> expPro;
    private List<String> ressources;
    private String langue;
    private String carte;
    private String visa;

    private List<String> lActivites;


    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public String getCitoyenId() { return citoyenId; }
    public void setCitoyenId( String p_id) { this.citoyenId = p_id; }
    public String getDateDemande(){ return dateDemande;}
    public void setDateDemande(String d){ this.dateDemande=d;}
    public List<String> getCursusAc(){ return cursurAc;}
    public void setCursurAc(List<String> ls){ this.cursurAc=ls;}
    public List<String> getExpPro(){ return expPro;}
    public void setExpPro(List<String> ls){ this.expPro=ls;}
    public List<String> getRessources(){ return ressources;}
    public void setRessources(List<String> ls){ this.ressources=ls;}
    public String getLangue(){ return langue;}
    public void setLangue(String s){ this.langue=s;}
    public String getCarte(){ return carte;}
    public void setCarte(String b){ this.carte=b;}
    public String getVisa(){ return visa;}
    public void setVisa(String b){ this.visa=b;}

    public List<String> getActivitesId(){ return lActivites; }
    public void setActivitesId(List<String> l ){ lActivites=l; }

    // ******************
    // Fonctions
    // ******************
    public FormulaireDTO getDTO()throws ParseException {
        FormulaireDTO dto = new FormulaireDTO();
        if( getId()!=null && !getId().isEmpty())
        dto.setId( convertUUID(getId()) );
        dto.setVisa( getVisa().equals("1") );
        dto.setCarte( getCarte().equals("1") );

        isNotEmpty(getLangue());
        dto.setLangue( getLangue());

        dto.setRessources(getRessources());
        dto.setExpPro(getExpPro());
        dto.setCursurAc(getCursusAc());

        isNotEmpty(getDateDemande());
        dto.setDateDemande( Timestamp.from(convertDate(getDateDemande()).toInstant()) );

        isNotEmpty(getCitoyenId());
        dto.setCitoyenId(convertUUID(getCitoyenId()));
        return dto;
    }

}