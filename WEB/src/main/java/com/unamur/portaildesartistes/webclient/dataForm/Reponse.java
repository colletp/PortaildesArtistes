package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.ReponseDTO;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

public class Reponse extends DataForm<ReponseDTO> {

    // ******************
    // Champs/propriétés
    // ******************

    private String trtId;
    private String citoyenId;
    private String dateReponse;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public String getTrtId(){ return trtId; }
    public void setTrtId( String p_id){ this.trtId = p_id; }
    public String getCitoyenId(){ return citoyenId; }
    public void setCitoyenId( String p_id){ this.citoyenId = p_id; }
    public String getDateReponse() { return dateReponse; }
    public void setDateReponse(String p_date) { this.dateReponse = p_date; }

    // ******************
    // Fonctions
    // ******************
    public ReponseDTO getDTO()throws ParseException {
        ReponseDTO dto = new ReponseDTO();
        if( getId()!=null && !getId().isEmpty())
        dto.setId( convertUUID(getId()) );

        isNotEmpty(getDateReponse());
        dto.setDateReponse(Timestamp.from(convertDate(getDateReponse()).toInstant()) );

        isNotEmpty(getCitoyenId());
        dto.setCitoyenId(convertUUID(getCitoyenId()));

        isNotEmpty(getTrtId());
        dto.setTrtId(convertUUID(getTrtId()));
        return dto;
    }

}