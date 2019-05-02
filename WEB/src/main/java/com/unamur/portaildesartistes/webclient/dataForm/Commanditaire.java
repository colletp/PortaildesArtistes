package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.CommanditaireDTO;

import java.text.ParseException;
import java.util.UUID;

public class Commanditaire extends DataForm<CommanditaireDTO> {

    // ******************
    // Champs/propriétés
    // ******************

    private String entrepriseId;
    private String citoyenId;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public UUID getEntrepriseId() { return convertUUID(entrepriseId); }
    public void setEntrepriseId( String p_id) { this.entrepriseId = p_id; }
    public UUID getCitoyenId() { return convertUUID(citoyenId); }
    public void setCitoyenId( String p_id) { this.citoyenId = p_id; }

    // ******************
    // Fonctions
    // ******************
    public CommanditaireDTO getDTO()throws ParseException {
        CommanditaireDTO dto = new CommanditaireDTO();
        dto.setId( getId() );
        dto.setEntrepriseId(getEntrepriseId());
        dto.setCitoyenId(getCitoyenId());
        return dto;
    }

}