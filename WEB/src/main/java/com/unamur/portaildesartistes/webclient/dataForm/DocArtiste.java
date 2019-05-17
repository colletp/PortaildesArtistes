package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.DocArtisteDTO;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

public class DocArtiste extends DataForm<DocArtisteDTO> {

    // ******************
    // Champs/propriétés
    // ******************

    private String citoyenId;
    private String reponseId;
    private String noDoc;
    private String nomArtiste;
    private String datePeremption;
    private String typeDocArtiste;

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
    public String getNomArtiste() { return nomArtiste; }
    public void setNomArtiste(String p_nom) { this.nomArtiste = p_nom; }
    public String getDatePeremption() { return datePeremption; }
    public void setDatePeremption(String p_date) { this.datePeremption = p_date; }
    public String getTypeDocArtiste() { return typeDocArtiste; }
    public void setTypeDocArtiste(String p_type) { this.typeDocArtiste = p_type; }

    public void setFromDTO(final DocArtisteDTO objDTO) {
        setId( (objDTO.getId()==null?"":objDTO.getId().toString()) );
        setCitoyenId(objDTO.getCitoyenId().toString());
        setReponseId(objDTO.getReponseId().toString());
        setNoDoc(objDTO.getNoDoc());
        setNomArtiste(objDTO.getNomArtiste());
        setDatePeremption(convertDate(objDTO.getDatePeremption()));
        setTypeDocArtiste(objDTO.getTypeDocArtiste());
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

        //hasLengthMin(getNomArtiste(),2);
        dto.setNomArtiste(getNomArtiste());

        hasLengthMin(getNoDoc(),2);
        dto.setNoDoc(getNoDoc());

        isNotEmpty(getReponseId());
        dto.setReponseId( convertUUID(getReponseId()));
        return dto;
    }

}