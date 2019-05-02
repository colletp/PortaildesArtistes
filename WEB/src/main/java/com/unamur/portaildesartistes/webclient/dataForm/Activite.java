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

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public UUID getSecteurId() { isNotEmpty(secteurId);return convertUUID(secteurId); }
    public void setSecteurId( String p_id) { this.secteurId = p_id; }
    public String getNomActivite() { isNotEmpty(nomActivite);return nomActivite; }
    public void setNomActivite(String p_activite) { this.nomActivite = p_activite; }

    // ******************
    // Fonctions
    // ******************
    public ActiviteDTO getDTO()throws ParseException {
        ActiviteDTO dto = new ActiviteDTO();
        dto.setId( getId() );
        dto.setSecteurId(getSecteurId());
        dto.setNomActivite(getNomActivite());
        return dto;
    }

}