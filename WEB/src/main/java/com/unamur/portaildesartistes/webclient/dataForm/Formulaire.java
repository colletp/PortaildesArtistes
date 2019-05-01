package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.FormulaireDTO;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Formulaire extends DataForm {

    // ******************
    // Champs/propriétés
    // ******************

    private String id;
    private String citoyenId;
    private String dateDemande;
    private List<String> cursurAc;
    private List<String> expPro;
    private List<String> ressources;
    private String langue;
    private Boolean carte;
    private Boolean visa;

    private List<Activite> lActivites;


    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public UUID getId() { return (UUID)convert(id,UUID.class); }
    public void setId( String p_id) { this.id = p_id; }
    public UUID getCitoyenId() { isNotEmpty(citoyenId);return (UUID)convert(citoyenId,UUID.class); }
    public void setCitoyenId( String p_id) { this.citoyenId = p_id; }
    public Date getDateDemande(){ isNotEmpty(dateDemande);return (Date)convert(dateDemande,Date.class);}
    public void setDateDemande(String d){ this.dateDemande=d;}
    public List<String> getCursusAc(){ return cursurAc;}
    public void setCursurAc(List<String> ls){ this.cursurAc=ls;}
    public List<String> getExpPro(){ return expPro;}
    public void setExpPro(List<String> ls){ this.expPro=ls;}
    public List<String> getRessources(){ return ressources;}
    public void setRessources(List<String> ls){ this.ressources=ls;}
    public String getLangue(){ isNotEmpty(langue);return langue;}
    public void setLangue(String s){ this.langue=s;}
    public Boolean getCarte(){ return carte;}
    public void setCarte(Boolean b){ this.carte=b;}
    public Boolean getVisa(){ return visa;}
    public void setVisa(Boolean b){ this.visa=b;}

    // ******************
    // Fonctions
    // ******************
    public FormulaireDTO getDTO()throws ParseException {
        FormulaireDTO dto = new FormulaireDTO();
        dto.setId( getId() );
        dto.setVisa(getVisa());
        dto.setCarte(getCarte());
        dto.setLangue(getLangue());
        dto.setRessources(getRessources());
        dto.setExpPro(getExpPro());
        dto.setCursurAc(getCursusAc());
        dto.setDateDemande( Timestamp.from(getDateDemande().toInstant()) );
        dto.setCitoyenId(getCitoyenId());
        return dto;
    }

}