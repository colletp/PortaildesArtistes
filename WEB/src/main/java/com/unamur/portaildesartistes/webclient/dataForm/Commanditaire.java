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

    public String getEntrepriseId() { return entrepriseId; }
    public void setEntrepriseId( String p_id) { this.entrepriseId = p_id; }
    public String getCitoyenId() { return citoyenId; }
    public void setCitoyenId( String p_id) { this.citoyenId = p_id; }

    public void setFromDTO(final CommanditaireDTO objDTO) {
        setId( (objDTO.getId()==null?"":objDTO.getId().toString()) );
        setEntrepriseId(objDTO.getEntrepriseId().toString());
        setCitoyenId(objDTO.getCitoyenId().toString());
    }
    // ******************
    // Fonctions
    // ******************
    public CommanditaireDTO getDTO()throws ParseException {
        CommanditaireDTO dto = new CommanditaireDTO();
        if( getId()!=null && !getId().isEmpty())
        dto.setId( convertUUID(getId()) );
        dto.setEntrepriseId( convertUUID(getEntrepriseId()));
        dto.setCitoyenId( convertUUID(getCitoyenId()));
        return dto;
    }

}