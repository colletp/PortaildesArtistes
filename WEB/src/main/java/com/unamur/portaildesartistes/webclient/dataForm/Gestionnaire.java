package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.GestionnaireDTO;

import java.text.ParseException;
import java.util.UUID;

public class Gestionnaire extends DataForm<GestionnaireDTO> {

    // ******************
    // Champs/propriétés
    // ******************

    private String citoyenId;
    private String travailleId;
    private String matricule;
    private String bureau;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************


    public UUID getCitoyenId(){ return convertUUID(citoyenId); }
    public void setCitoyenId(String p_id) { this.citoyenId = p_id; }
    public UUID getTravailleId() { return convertUUID(travailleId); }
    public void setTravailleId(String p_id) { this.travailleId = p_id; }
    public String getMatricule() { return matricule; }
    public void setMatricule(String p_mat) { this.matricule = p_mat; }
    public String getBureau() { return bureau; }
    public void setBureau(String p_bur) { this.bureau = p_bur; }

    // ******************
    // Fonctions
    // ******************
    public GestionnaireDTO getDTO() throws ParseException {
        GestionnaireDTO dto = new GestionnaireDTO();
        dto.setId(getId());
        dto.setMatricule(getMatricule());
        dto.setBureau(getBureau());
        dto.setCitoyenId(getCitoyenId());
        dto.setTravailleId(getTravailleId());
        return dto;
    }
}