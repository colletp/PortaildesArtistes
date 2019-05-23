package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.DocArtisteDTO;
import com.unamur.portaildesartistes.DTO.ReponseDTO;
import com.unamur.portaildesartistes.DTO.SecteurDTO;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DocArtiste extends DataForm<DocArtisteDTO> {

    // ******************
    // Champs/propriétés
    // ******************

    private String citoyenId;
    private String reponseId;
    private String noDoc;
    private String datePeremption;
    private String typeDocArtiste;

    private CitoyenDTO oCitoyen;
    private ReponseDTO oReponse;

    private List<String> activitesId;

    private Collection<SecteurDTO> lSecteurs;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public String getCitoyenId() { return citoyenId; }
    public void setCitoyenId( String p_id) { this.citoyenId = p_id; }
    public String getReponseId() { return reponseId; }
    public void setReponseId( String p_id) { this.reponseId = p_id; }
    public String getNoDoc() { isNotEmpty(noDoc);return noDoc; }
    public void setNoDoc(String p_noDoc) { this.noDoc = p_noDoc; }
    public String getDatePeremption() { return datePeremption; }
    public void setDatePeremption(String p_date) { this.datePeremption = p_date; }
    public String getTypeDocArtiste() { return typeDocArtiste; }
    public void setTypeDocArtiste(String p_type) { this.typeDocArtiste = p_type; }


    public CitoyenDTO getCitoyen() {
        return oCitoyen;
    }
    public void setCitoyen(CitoyenDTO oCitoyen) {
        this.oCitoyen = oCitoyen;
    }
    public ReponseDTO getReponse() {
        return oReponse;
    }
    public void setReponse(ReponseDTO oReponse) {
        this.oReponse = oReponse;
    }
    public List<String> getActivitesId(){ return activitesId; }
    public void setActivitesId(List<String> lAct ){ activitesId=lAct; }
    public void setSecteurActivites(Collection<SecteurDTO> ls){ lSecteurs=ls; }
    public Collection<SecteurDTO> getSecteurActivites(){ return lSecteurs; }

    public void setFromDTO(final DocArtisteDTO objDTO) {
        setId( (objDTO.getId()==null?"":objDTO.getId().toString()) );
        setCitoyenId(objDTO.getCitoyenId().toString());
        setReponseId(objDTO.getReponseId().toString());
        setNoDoc(objDTO.getNoDoc());
        setDatePeremption(convertDate(objDTO.getDatePeremption()));
        setTypeDocArtiste(objDTO.getTypeDocArtiste());

        setCitoyen(objDTO.getCitoyen());
        setReponse(objDTO.getReponse());
    }
    // ******************
    // Fonctions
    // ******************
    public DocArtisteDTO getDTO(){
        DocArtisteDTO dto = new DocArtisteDTO();
        if( getId()!=null && !getId().isEmpty())
        dto.setId( convertUUID(getId()) );

        isNotEmpty(getDatePeremption());
        dto.setDatePeremption(convertDate(getDatePeremption()));

        isNotEmpty(getCitoyenId());
        dto.setCitoyenId( convertUUID(getCitoyenId()));

        isNotEmpty(getTypeDocArtiste());
        if(   !getTypeDocArtiste().equals("Carte artiste")
            &&!getTypeDocArtiste().equals("Visa artiste")){
            throw new IllegalArgumentException("Carte artiste ou Visa artiste");
        }
        dto.setTypeDocArtiste(getTypeDocArtiste());

        hasLengthMin(getNoDoc(),2);
        dto.setNoDoc(getNoDoc());

        isNotEmpty(getReponseId());
        dto.setReponseId( convertUUID(getReponseId()));

        isNotEmpty(getCitoyenId());
        dto.setCitoyen(getCitoyen());

        isNotEmpty(getReponseId());
        dto.setReponse(getReponse());

        return dto;
    }

}