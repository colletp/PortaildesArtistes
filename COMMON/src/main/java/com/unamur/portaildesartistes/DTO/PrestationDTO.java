package com.unamur.portaildesartistes.DTO;

import java.util.Date;
import java.util.UUID;

public class PrestationDTO extends DTO {

    // ******************
    // Champs/propriétés
    // ******************

    private Date datePrest;
    private int duree;
    private Double montant;
    private String etat;
    private UUID commanditaireId;
    private UUID docArtisteId;
    private UUID activiteId;
    private UUID seDerouleId;

    private CommanditaireDTO commanditaire;
    private DocArtisteDTO docArtiste;
    private ActiviteDTO activite;
    private AdresseDTO seDeroule;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public Date getDatePrest() { return datePrest; }
    public void setDatePrest(Date p_date) { this.datePrest = p_date; }
    public int getDuree() { return duree; }
    public void setDuree( int p_duree) { this.duree = p_duree; }
    public Double getMontant() { return montant; }
    public void setMontant( Double p_montant) { this.montant = p_montant; }
    public String getEtat() { return etat; }
    public void setEtat( String p_etat) { this.etat = p_etat; }

    public UUID getCommanditaireId() { return commanditaireId; }
    public void setCommanditaireId( UUID p_id) { this.commanditaireId = p_id; }
    public UUID getDocArtisteId() { return docArtisteId; }
    public void setDocArtisteId( UUID p_id) { this.docArtisteId = p_id; }
    public UUID getActiviteId() { return activiteId; }
    public void setActiviteId( UUID p_id) { this.activiteId = p_id; }
    public UUID getSeDerouleId() { return seDerouleId; }
    public void setSeDerouleId( UUID p_id) { this.seDerouleId = p_id; }

    public CommanditaireDTO getCommanditaire() { return commanditaire; }
    public void setCommanditaire( CommanditaireDTO p_obj) { this.commanditaire = p_obj; }
    public DocArtisteDTO getDocArtiste() { return docArtiste; }
    public void setDocArtiste( DocArtisteDTO p_obj) { this.docArtiste = p_obj; }
    public ActiviteDTO getActivite() { return activite; }
    public void setActivite( ActiviteDTO p_obj) { this.activite = p_obj; }
    public AdresseDTO getSeDeroule() { return seDeroule; }
    public void setSeDeroule( AdresseDTO p_obj) { this.seDeroule = p_obj; }

    // ******************
    // Fonctions
    // ******************

}