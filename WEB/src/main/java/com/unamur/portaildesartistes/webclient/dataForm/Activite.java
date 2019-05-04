package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;

import java.text.ParseException;
import java.util.UUID;

public class Activite extends DataForm<ActiviteDTO> {

    // ******************
    // Champs/propriétés
    // ******************

    private String secteurId;
    private String nomActivite;
    private String description;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public String getSecteurId() { return secteurId; }
    public void setSecteurId( String p_id) { this.secteurId = p_id; }
    public String getNomActivite() { return nomActivite; }
    public void setNomActivite(String p_activite) { this.nomActivite = p_activite; }
    public String getDescription(){ return description; }
    public void setDescription(String desc){this.description=desc; }
    // ******************
    // Fonctions
    // ******************
    public ActiviteDTO getDTO()throws ParseException {
        ActiviteDTO dto = new ActiviteDTO();
        if( getId()!=null && !getId().isEmpty())
        dto.setId( convertUUID(getId()) );

        isNotEmpty(getSecteurId());
        dto.setSecteurId( convertUUID(getSecteurId()));

        isNotEmpty( getNomActivite() );
        dto.setNomActivite( getNomActivite() );

        dto.setDescription( getDescription() );

        return dto;
    }

}