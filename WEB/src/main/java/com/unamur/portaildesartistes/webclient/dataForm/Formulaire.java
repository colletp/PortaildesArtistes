package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.FormulaireDTO;
import com.unamur.portaildesartistes.DTO.SecteurDTO;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

public class Formulaire extends DataForm<FormulaireDTO> {

    // ******************
    // Champs/propriétés
    // ******************

    private String citoyenId;
    private String dateDemande;
    private String cursusAc;
    private String expPro;
    private String ressources;
//    private List<String> cursusAc;
//    private List<String> expPro;
//    private List<String> ressources;
    private String langue;
    private String carte;
    private String visa;
    private List<String> activitesId;

//    private List<SecteurDTO> lSecteurs;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public String getCitoyenId() { return citoyenId; }
    public void setCitoyenId( String p_id) { citoyenId = p_id; }
    public String getDateDemande(){ return dateDemande;}
    public void setDateDemande(String d){ dateDemande=d;}
    public String getCursusAc(){ return cursusAc;}
    public void setCursusAc(String ls){ cursusAc=ls;}
    public String getExpPro(){ return expPro;}
    public void setExpPro(String ls){ expPro=ls;}
    public String getRessources(){ return ressources;}
    public void setRessources(String ls){ ressources=ls;}
//    public List<String> getCursusAc(){ return cursusAc;}
//    public void setCursusAc(List<String> ls){ this.cursusAc=ls;}
//    public List<String> getExpPro(){ return expPro;}
//    public void setExpPro(List<String> ls){ this.expPro=ls;}
//    public List<String> getRessources(){ return ressources;}
//    public void setRessources(List<String> ls){ this.ressources=ls;}
    public String getLangue(){ return langue;}
    public void setLangue(String s){ langue=s;}
    public String getCarte(){ return carte;}
    public void setCarte(String b){ carte=b;}
    public String getVisa(){ return visa;}
    public void setVisa(String b){ visa=b;}

    public List<String> getActivitesId(){ return activitesId; }
    public void setActivitesId(List<String> lAct ){ activitesId=lAct; }

//    public void setSecteurActivites(List<SecteurDTO> ls){ lSecteurs=ls; }
//    public List<SecteurDTO> getSecteurActivites(){ return lSecteurs; }
    // ******************
    // Fonctions
    // ******************
    public FormulaireDTO getDTO()throws ParseException {
        FormulaireDTO dto = new FormulaireDTO();
        if( getId()!=null && !getId().isEmpty())
            dto.setId( convertUUID(getId()) );

        dto.setVisa( getVisa()!=null );
        dto.setCarte( getCarte()!=null );

        isNotEmpty(getLangue());
        if(!langue.equals("FR")&&!langue.equals("EN")){
            throw new IllegalArgumentException("FR ou EN");
        }
        dto.setLangue( getLangue());

        dto.setRessources( Arrays.asList(getRessources()==null?"":getRessources()) );
        dto.setExpPro( Arrays.asList(getExpPro()==null?"":getExpPro()) );
        dto.setCursusAc( Arrays.asList(getCursusAc()==null?"":getCursusAc()) );

//        dto.setRessources(getRessources());
//        dto.setExpPro(getExpPro());
//        dto.setCursusAc(getCursusAc());

        /*
        isNotEmpty(getDateDemande());
        dto.setDateDemande( Timestamp.from(convertDate(getDateDemande()).toInstant()) );
        if( getCitoyenId()!=null && !getCitoyenId().isEmpty())
            dto.setCitoyenId(convertUUID(getCitoyenId()));
        */

        List<UUID> activitesId = new ArrayList<>();
        for( String act : getActivitesId() ){
            logger.error(act);
            activitesId.add( UUID.fromString(act) );
        }
        dto.setActivitesId(activitesId);
        return dto;
    }

    public void setFromDTO(final FormulaireDTO objDTO) {
        setId( (objDTO.getId()==null?"":objDTO.getId().toString()) );
        setCitoyenId(objDTO.getCitoyenId()==null?"":objDTO.getCitoyenId().toString());
        setDateDemande(convertDateTime(objDTO.getDateDemande()));
        setCursusAc(objDTO.getCursusAc()==null?"":objDTO.getCursusAc().toString());
        setExpPro(objDTO.getExpPro()==null?"":objDTO.getExpPro().toString());
        setRessources(objDTO.getRessources()==null?"":objDTO.getRessources().toString());
        setLangue(objDTO.getLangue());
        setCarte(objDTO.getCarte().toString());
        setVisa(objDTO.getVisa().toString());
        List<String> la = new ArrayList<>();
        for( UUID uuid : objDTO.getActivitesId() )
            la.add( uuid.toString() );
        setActivitesId( la );
//        setSecteurActivites( objDTO.getSecteurActivites() );
    }

}