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

    public String getDatePrest() { return datePrest; }
    public void setDatePrest(String p_date) { this.datePrest = p_date; }
    public String getDuree() { return duree; }
    public void setDuree( String p_duree) { this.duree = p_duree; }
    public String getMontant() { return montant; }
    public void setMontant( String p_montant) { this.montant = p_montant; }
    public String getEtat() { return etat; }
    public void setEtat( String p_etat) { this.etat = p_etat; }

    public String getSeDerouleId() { return seDerouleId; }
    public void setSeDerouleId( String p_id) { this.seDerouleId = p_id; }
    public String getDocArtisteId() { return docArtisteId; }
    public void setDocArtisteId( String p_id) { this.docArtisteId = p_id; }
    public String getActiviteId() { return activiteId; }
    public void setActiviteId( String p_id) { this.activiteId= p_id; }
    public String getCommanditaireId() { return commanditaireId; }
    public void setCommanditaireId( String p_id) { this.commanditaireId= p_id; }


    protected Boolean isNotOverMontantMax(String p_montant) throws IllegalArgumentException {
        if (Double.parseDouble(p_montant) > 128.93) {
            throw new IllegalArgumentException("Montant maximal d'une prestation dépassé");
        }
        return true;
    }

    public void setFromDTO(final PrestationDTO objDTO) {
        setId( (objDTO.getId()==null?"":objDTO.getId().toString()) );
        setDatePrest(convertDate(objDTO.getDatePrest()));
        setDuree(objDTO.getDuree().toString());
        setMontant(objDTO.getMontant().toString());
        setEtat(objDTO.getEtat());
        setCommanditaireId(objDTO.getCommanditaireId().toString());
        setDocArtisteId(objDTO.getDocArtisteId().toString());
        setActiviteId(objDTO.getActiviteId().toString());
        setSeDerouleId(objDTO.getSeDerouleId().toString());
    }
    // ******************
    // Fonctions
    // ******************
    public PrestationDTO getDTO()throws ParseException {
        PrestationDTO dto = new PrestationDTO();
        if( getId()!=null && !getId().isEmpty())
        dto.setId( convertUUID(getId()) );

        isNotEmpty(getSeDerouleId());
        dto.setSeDerouleId(convertUUID(getSeDerouleId()));

        isNotEmpty(getDocArtisteId());
        dto.setDocArtisteId(convertUUID(getDocArtisteId()));

        // isNotEmpty(getEtat()); A ajouter?? (commentaire Bernard G)
        dto.setEtat(getEtat());

        isNotEmpty(getMontant());
        isNotOverMontantMax(getMontant());
        dto.setMontant( convertDouble(getMontant()) );

        isNotEmpty(getDuree());
        dto.setDuree( convertInt(getDuree()));

        isNotEmpty(getActiviteId());
        dto.setActiviteId(convertUUID(getActiviteId()));

        isNotEmpty(getDatePrest());
        dto.setDatePrest(Timestamp.from(convertDate(getDatePrest()).toInstant()) );

        isNotEmpty(getCommanditaireId());
        dto.setCommanditaireId( convertUUID(getCommanditaireId()));

        return dto;
    }

}