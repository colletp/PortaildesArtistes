package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.TraitementDTO;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

public class Traitement extends DataForm<TraitementDTO> {

    // ******************
    // Champs/propriétés
    // ******************

    private String dateTrt;
    private String appreciation;
    private String roleId;
    private String gestId;
    private String formId;
    private String citoyenPrestId;
    private String typeRole;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public Date getDateTrt() { isNotEmpty(dateTrt); return convertDate(dateTrt); }
    public void setDateTrt(String p_date) { this.dateTrt = p_date; }
    public String getAppreciation() { return appreciation; }
    public void setAppreciation( String p_ap) { this.appreciation = p_ap; }
    public UUID getRoleId() { isNotEmpty(roleId); return convertUUID(roleId); }
    public void setRoleId( String p_id) { this.roleId = p_id; }
    public UUID getGestId() { isNotEmpty(gestId); return convertUUID(gestId); }
    public void setGestId( String p_id) { this.gestId = p_id; }
    public UUID getFormId() { isNotEmpty(formId); return convertUUID(formId); }
    public void setFormId( String p_id) { this.formId = p_id; }
    public UUID getCitoyenPrestId() { isNotEmpty(citoyenPrestId); return convertUUID(citoyenPrestId); }
    public void setCitoyenPrestId( String p_id) { this.citoyenPrestId = p_id; }
    public String getTypeRole() { return typeRole; }
    public void setTypeRole( String p_tr) { this.typeRole = p_tr; }

    // ******************
    // Fonctions
    // ******************
    public TraitementDTO getDTO()throws ParseException {
        TraitementDTO dto = new TraitementDTO();
        dto.setId( getId() );
        dto.setGestId(getGestId() );
        dto.setFormId(getFormId());
        dto.setCitoyenPrestId(getCitoyenPrestId());
        dto.setRoleId(getRoleId());
        dto.setDateTrt(Timestamp.from(getDateTrt().toInstant()));
        dto.setAppreciation(getAppreciation());
        dto.setTypeRole(getTypeRole());
        return dto;
    }

}