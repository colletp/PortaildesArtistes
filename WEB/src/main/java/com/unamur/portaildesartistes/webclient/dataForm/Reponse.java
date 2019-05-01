package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.ReponseDTO;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

public class Reponse extends DataForm {

    // ******************
    // Champs/propriétés
    // ******************

    private String id;
    private String trtId;
    private String citoyenId;
    private String dateReponse;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public UUID getId() { return (UUID)convert(id,UUID.class); }
    public void setId( String p_id) { this.id = p_id; }
    public UUID getTrtId(){ return (UUID)convert(trtId,UUID.class); }
    public void setTrtId( String p_id){ this.trtId = p_id; }
    public UUID getCitoyenId(){ return (UUID)convert(citoyenId,UUID.class); }
    public void setCitoyenId( String p_id){ this.citoyenId = p_id; }
    public Date getDateReponse() { return (Date)convert(dateReponse,Date.class); }
    public void setDateReponse(String p_date) { this.dateReponse = p_date; }

    // ******************
    // Fonctions
    // ******************
    public ReponseDTO getDTO()throws ParseException {
        ReponseDTO dto = new ReponseDTO();
        dto.setId( getId() );
        dto.setDateReponse(Timestamp.from(getDateReponse().toInstant()) );
        dto.setCitoyenId(getCitoyenId());
        dto.setTrtId(getTrtId());
        return dto;
    }

}