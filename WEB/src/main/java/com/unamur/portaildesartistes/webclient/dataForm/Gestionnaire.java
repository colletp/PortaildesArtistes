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
    public String getCitoyenId(){ return citoyenId; }
    public void setCitoyenId(String p_id) { this.citoyenId = p_id; }
    public String getTravailleId() { return travailleId; }
    public void setTravailleId(String p_id) { this.travailleId = p_id; }
    public String getMatricule() { return matricule; }
    public void setMatricule(String p_mat) { this.matricule = p_mat; }
    public String getBureau() { return bureau; }
    public void setBureau(String p_bur) { this.bureau = p_bur; }

    public void setFromDTO(final GestionnaireDTO objDTO) {
        super.setFromDTO(objDTO);
        setCitoyenId(objDTO.getCitoyenId().toString());
        setTravailleId(objDTO.getTravailleId().toString());
        setMatricule(objDTO.getMatricule());
        setBureau(objDTO.getBureau());
    }
    // ******************
    // Fonctions
    // ******************
    public GestionnaireDTO getDTO() throws ParseException {
        GestionnaireDTO dto = new GestionnaireDTO();
        if( getId()!=null && !getId().isEmpty())
        dto.setId( convertUUID(getId()) );

        isNotEmpty(getMatricule());
        dto.setMatricule(getMatricule());

        isNotEmpty(getBureau());
        dto.setBureau(getBureau());

        isNotEmpty(getCitoyenId());
        dto.setCitoyenId(convertUUID(getCitoyenId()));

        isNotEmpty(getTravailleId());
        dto.setTravailleId(convertUUID(getTravailleId()));
        return dto;
    }
}