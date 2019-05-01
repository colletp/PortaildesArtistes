package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.PrestationDTO;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

public class Prestation extends DataForm {

    // ******************
    // Champs/propriétés
    // ******************

    private String id;
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

    public UUID getId() { return (UUID)convert(id,UUID.class); }
    public void setId( String p_id) { this.id = p_id; }
    public Date getDatePrest() { return (Date)convert(datePrest,Date.class); }
    public void setDatePrest(String p_date) { this.datePrest = p_date; }
    public Integer getDuree() { return (Integer)convert(duree,Integer.class); }
    public void setDuree( String p_duree) { this.duree = p_duree; }
    public Double getMontant() { return (Double)convert(montant,Double.class); }
    public void setMontant( String p_montant) { this.montant = p_montant; }
    public String getEtat() { return etat; }
    public void setEtat( String p_etat) { this.etat = p_etat; }

    public UUID getSeDerouleId() { return (UUID)convert(seDerouleId,UUID.class); }
    public void setSeDerouleId( String p_id) { this.seDerouleId = p_id; }
    public UUID getDocArtisteId() { return (UUID)convert(docArtisteId,UUID.class); }
    public void setDocArtisteId( String p_id) { this.docArtisteId = p_id; }
    public UUID getActiviteId() { return (UUID)convert(activiteId,UUID.class); }
    public void setActiviteId( String p_id) { this.activiteId= p_id; }
    public UUID getCommanditaireId() { return (UUID)convert(commanditaireId,UUID.class); }
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