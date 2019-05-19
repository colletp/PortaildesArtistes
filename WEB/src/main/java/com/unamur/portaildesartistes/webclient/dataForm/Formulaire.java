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
    private List<String> cursusAc;
    private List<String> expPro;
    private List<String> ressources;
    private String langue;
    private String carte;
    private String visa;

    private List<String> activitesId;

    private Collection<SecteurDTO> lSecteurs;

    // ******************
    // Constructeur
    // ******************
    public Formulaire(){
    }

    public Formulaire(FormulaireDTO formDTO){
        super(formDTO);
    }
    // ******************
    // Setter/Getter
    // ******************

    public String getCitoyenId(){ return citoyenId; }
    public void setCitoyenId(String p_id){ citoyenId = p_id; }
    public String getDateDemande(){ return dateDemande; }
    public void setDateDemande(String d){ dateDemande=d; }
    public List<String> getCursusAc(){ return cursusAc;}
    public void setCursusAc(List<String> ls){if(cursusAc==null)cursusAc = new ArrayList<>();for(String s : ls)cursusAc.add(s);}
    public List<String> getExpPro(){ return expPro;}
    public void setExpPro(List<String> ls){if(expPro==null)expPro = new ArrayList<>();for(String s : ls)expPro.add(s);}
    public List<String> getRessources(){ return ressources;}
    public void setRessources(List<String> ls){if(ressources==null)ressources = new ArrayList<>();for(String s : ls)ressources.add(s);}

    public String getLangue(){ return langue;}
    public void setLangue(String s){ langue=s;}
    public String getCarte(){ return carte;}
    public void setCarte(String b){ carte=b;}
    public String getVisa(){ return visa;}
    public void setVisa(String b){ visa=b;}

    public List<String> getActivitesId(){ return activitesId; }
    public void setActivitesId(List<String> lAct ){ activitesId=lAct; }

    public void setSecteurActivites(Collection<SecteurDTO> ls){ lSecteurs=ls; }
    public Collection<SecteurDTO> getSecteurActivites(){ return lSecteurs; }
    // ******************
    // Fonctions
    // ******************
    public Boolean notNullSameTime(String carte,String visa){
        if (carte!="1"&&visa!="1"){
            throw new IllegalArgumentException("Vous devez choisir au moins une carte ou un visa");
        }
        return true;
    }

    public FormulaireDTO getDTO(){
        FormulaireDTO dto = new FormulaireDTO();
        if( getId()!=null && !getId().isEmpty())
            dto.setId( convertUUID(getId()) );

        notNullSameTime(carte,visa);
        dto.setVisa( getVisa()!=null?getVisa().equals("1"):false );
        dto.setCarte( getCarte()!=null?getCarte().equals("1"):false );

        isNotEmpty( getLangue() );
        if(!langue.equals("FR")&&!langue.equals("EN")){
            throw new IllegalArgumentException("FR ou EN");
        }
        dto.setLangue( getLangue() );

        dto.setRessources( getRessources() );
        dto.setExpPro( getExpPro() );
        dto.setCursusAc( getCursusAc() );

        isNotEmpty(getDateDemande());
        dto.setDateDemande( Timestamp.from(convertDate(getDateDemande()).toInstant()) );

        isNotEmpty(citoyenId);
        if( getCitoyenId()!=null && !getCitoyenId().isEmpty())
            dto.setCitoyenId(convertUUID(getCitoyenId()));

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
        setCursusAc(objDTO.getCursusAc()==null?Arrays.asList():objDTO.getCursusAc());
        setExpPro(objDTO.getExpPro()==null?Arrays.asList():objDTO.getExpPro());
        setRessources(objDTO.getRessources()==null?Arrays.asList():objDTO.getRessources());
        setLangue(objDTO.getLangue());
        setCarte(objDTO.getCarte()?"1":"0");
        setVisa(objDTO.getVisa()?"1":"0");

        List<String> la = new ArrayList<>();
        if( objDTO.getActivitesId()!=null )
            for( UUID uuid : objDTO.getActivitesId() )
                la.add( uuid.toString() );
        setActivitesId( la );

        setSecteurActivites( objDTO.getSecteurActivites() );
    }

}