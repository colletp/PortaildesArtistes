package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.PrestationDTO;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

public class Prestation extends DataForm<PrestationDTO> {

    // ******************
    // Champs/propriétés
    // ******************

    private String datePrest;
    private String duree;
    private String montant;
    private String etat;
    private String commanditaireId;
    private String docArtisteId;
    private String activiteId;
    private String seDerouleId;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public Date getDatePrest() { return convertDate(datePrest); }
    public void setDatePrest(String p_date) { this.datePrest = p_date; }
    public Integer getDuree() { return convertInt(duree); }
    public void setDuree( String p_duree) { this.duree = p_duree; }
    public Double getMontant() { return convertDouble(montant); }
    public void setMontant( String p_montant) { this.montant = p_montant; }
    public String getEtat() { return etat; }
    public void setEtat( String p_etat) { this.etat = p_etat; }

    public UUID getSeDerouleId() { return convertUUID(seDerouleId); }
    public void setSeDerouleId( String p_id) { this.seDerouleId = p_id; }
    public UUID getDocArtisteId() { return convertUUID(docArtisteId); }
    public void setDocArtisteId( String p_id) { this.docArtisteId = p_id; }
    public UUID getActiviteId() { return convertUUID(activiteId); }
    public void setActiviteId( String p_id) { this.activiteId= p_id; }
    public UUID getCommanditaireId() { return convertUUID(commanditaireId); }
    public void setCommanditaireId( String p_id) { this.commanditaireId= p_id; }

    // ******************
    // Fonctions
    // ******************
    public PrestationDTO getDTO()throws ParseException {
        PrestationDTO dto = new PrestationDTO();
        dto.setId( getId() );
        dto.setSeDerouleId(getSeDerouleId());
        dto.setDocArtisteId(getDocArtisteId());
        dto.setEtat(getEtat());
        dto.setMontant(getMontant());
        dto.setDuree(getDuree());
        dto.setActiviteId(getActiviteId());
        dto.setDatePrest(Timestamp.from(getDatePrest().toInstant()) );
        dto.setCommanditaireId(getCommanditaireId());
        return dto;
    }

}