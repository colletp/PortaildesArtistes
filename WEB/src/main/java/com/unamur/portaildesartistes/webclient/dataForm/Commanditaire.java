package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.CommanditaireDTO;

import java.text.ParseException;
import java.util.UUID;

public class Commanditaire extends DataForm {

    // ******************
    // Champs/propriétés
    // ******************

    private String id;
    private String entrepriseId;
    private String citoyenId;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public UUID getId() { return (UUID)convert(id,UUID.class); }
    public void setId( String p_id) { this.id = p_id; }
    public UUID getEntrepriseId() { return (UUID)convert(entrepriseId,UUID.class); }
    public void setEntrepriseId( String p_id) { this.entrepriseId = p_id; }
    public UUID getCitoyenId() { return (UUID)convert(citoyenId,UUID.class); }
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