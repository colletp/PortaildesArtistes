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

    public UUID getCitoyenId() { isNotEmpty(citoyenId);return convertUUID(citoyenId); }
    public void setCitoyenId( String p_id) { this.citoyenId = p_id; }
    public UUID getReponseId() { isNotEmpty(reponseId);return convertUUID(reponseId); }
    public void setReponseId( String p_id) { this.reponseId = p_id; }
    public String getNoDoc() { isNotEmpty(noDoc);return noDoc; }
    public void setNoDoc(String p_noDoc) { this.noDoc = p_noDoc; }
    public String getNomArtiste() { hasLengthMin(nomArtiste,2);return nomArtiste; }
    public void setNomArtiste(String p_nom) { this.nomArtiste = p_nom; }
    public Date getDatePeremption() { isNotEmpty(datePeremption);return convertDate(datePeremption); }
    public void setDatePeremption(String p_date) { this.datePeremption = p_date; }
    public String getTypeDocArtiste() { isNotEmpty(typeDocArtiste);return typeDocArtiste; }
    public void setTypeDocArtiste(String p_type) { this.typeDocArtiste = p_type; }

    // ******************
    // Fonctions
    // ******************
    public DocArtisteDTO getDTO()throws ParseException {
        DocArtisteDTO dto = new DocArtisteDTO();
        dto.setId( getId() );
        dto.setDatePeremption(getDatePeremption());
        dto.setCitoyenId(getCitoyenId());
        dto.setTypeDocArtiste(getTypeDocArtiste());
        dto.setNomArtiste(getNomArtiste());
        dto.setNoDoc(getNoDoc());
        dto.setReponseId(getReponseId());
        return dto;
    }

}