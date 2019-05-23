package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
import com.unamur.portaildesartistes.DTO.FormulaireDTO;
import com.unamur.portaildesartistes.DTO.SecteurDTO;

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
	private String aTraiter;
    private String nomArtiste;

    //private List<String> activitesId;

    private Collection<SecteurDTO> lSecteurs;

    private List<ActiviteDTO> lActToAddBySect;

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
    public void setCursusAc(List<String> ls){
        //if(cursusAc==null)cursusAc = new ArrayList<>();for(String s : ls)cursusAc.add(s);
        cursusAc = ls;}
    public List<String> getExpPro(){ return expPro;}
    public void setExpPro(List<String> ls){
        //if(expPro==null)expPro = new ArrayList<>();for(String s : ls)expPro.add(s);
        expPro=ls;
    }
    public List<String> getRessources(){ return ressources;}
    public void setRessources(List<String> ls){
        if(ressources==null)ressources = new ArrayList<>();for(String s : ls)ressources.add(s);
        ressources=ls;
    }

    public String getLangue(){ return langue;}
    public void setLangue(String s){ langue=s;}
    public String getCarte(){ return carte;}
    public void setCarte(String b){ carte=b;}
    public String getVisa(){ return visa;}
    public void setVisa(String b){ visa=b;}
    public String getATraiter(){ return aTraiter;}
    public void setATraiter(String b){ aTraiter=b;}
    public String getNomArtiste() { return nomArtiste; }
    public void setNomArtiste(String p_nom) { this.nomArtiste = p_nom; }

    //public List<String> getActivitesId(){ return activitesId; }
    //public void setActivitesId(List<String> lAct ){ activitesId=lAct; }

    public void setSecteurActivites(Collection<SecteurDTO> ls){ lSecteurs=ls; }
    public Collection<SecteurDTO> getSecteurActivites(){ return lSecteurs; }

    public void setActToAddBySect( List<ActiviteDTO> lAct ){
        lActToAddBySect = lAct;
    }
    public List<ActiviteDTO> getActToAddBySect(){
        return lActToAddBySect;
    }

    // ******************
    // Fonctions
    // ******************
    public Boolean notNullSameTime(String carte,String visa){
        if ( ( getCarte()!=null&&getCarte().equals("1") ) || (getVisa()!=null && getVisa().equals("1") ) ){
            return true;
        }
        throw new IllegalArgumentException("Vous devez choisir au moins une carte ou un visa");
    }

    public FormulaireDTO getDTO(){
        FormulaireDTO dto = new FormulaireDTO();
        if( getId()!=null && !getId().isEmpty())
            dto.setId( convertUUID(getId()) );

        notNullSameTime(getCarte(),getVisa());
        dto.setVisa( getVisa()!=null?getVisa().equals("1"):false );
        dto.setCarte( getCarte()!=null?getCarte().equals("1"):false );
        dto.setATraiter( getATraiter()!=null?getATraiter().equals("1"):false );

        isNotEmpty( getLangue() );
        if(!langue.equals("FR")&&!langue.equals("EN")){
            throw new IllegalArgumentException("FR ou EN");
        }
        dto.setLangue( getLangue() );

        dto.setRessources( getRessources() );
        dto.setExpPro( getExpPro() );
        dto.setCursusAc( getCursusAc() );

        //isNotEmpty(getDateDemande());
        dto.setDateDemande( convertDate(getDateDemande()) );

        //isNotEmpty(citoyenId);//complété par le citoyen courant lors de l'insertion
        if( getCitoyenId()!=null && !getCitoyenId().isEmpty())
            dto.setCitoyenId(convertUUID(getCitoyenId()));

        hasLengthMin(getNomArtiste(),2);
        dto.setNomArtiste(getNomArtiste());

        /*List<UUID> activitesId = new ArrayList<>();
        for( String act : getActivitesId() ){
            logger.error(act);
            activitesId.add( UUID.fromString(act) );
        }
        dto.setActivitesId(activitesId);*/
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
        setATraiter(objDTO.getATraiter()?"1":"0");
        setNomArtiste(objDTO.getNomArtiste());

        /*List<String> la = new ArrayList<>();
        if( objDTO.getActivitesId()!=null )
            for( UUID uuid : objDTO.getActivitesId() )
                la.add( uuid.toString() );
        setActivitesId( la );
        */

        setSecteurActivites( objDTO.getSecteurActivites() );
    }

}