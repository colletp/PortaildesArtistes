package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.PrestationDTO;
import com.unamur.portaildesartistes.DTO.AdresseDTO;
import com.unamur.portaildesartistes.DTO.ActiviteDTO;
import com.unamur.portaildesartistes.DTO.DocArtisteDTO;
import com.unamur.portaildesartistes.DTO.CommanditaireDTO;

import org.apache.tomcat.jni.Address;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Prestation extends DataForm<PrestationDTO> {

    // ******************
    // Champs/propriétés
    // ******************

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date datePrest;

    @NumberFormat(pattern = "#,###,###,###")
    private int duree;

    @NumberFormat(pattern = "#,###,###,###.##")
    private Double montant;

    private String etat;

    private UUID activiteId;
    private UUID commanditaireId;
    private UUID docArtisteId;
    private UUID adresseId;

    private ActiviteDTO oActivite;
    private CommanditaireDTO oCommanditaire;
    private DocArtisteDTO oDocArtiste;
    private AdresseDTO oAdresse;


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

    public UUID getSeDerouleId() { return adresseId; }
    public void setSeDerouleId( UUID p_id) { this.adresseId = p_id; }
    public UUID getDocArtisteId() { return docArtisteId; }
    public void setDocArtisteId( UUID p_id) { this.docArtisteId = p_id; }
    public UUID getActiviteId() { return activiteId; }
    public void setActiviteId( UUID p_id) { this.activiteId= p_id; }
    public UUID getCommanditaireId() { return commanditaireId; }
    public void setCommanditaireId( UUID p_id) { this.commanditaireId= p_id; }


    public AdresseDTO getSeDeroule() { return oAdresse; }
    public void setSeDeroule( AdresseDTO oAdresse) { this.oAdresse = oAdresse; }
    public DocArtisteDTO getDocArtiste() { return oDocArtiste; }
    public void setDocArtiste( DocArtisteDTO oDocArtiste) { this.oDocArtiste = oDocArtiste; }
    public ActiviteDTO getActivite() { return oActivite; }
    public void setActivite( ActiviteDTO oActivite) { this.oActivite = oActivite; }
    public CommanditaireDTO getCommanditaire() { return oCommanditaire; }
    public void setCommanditaire( CommanditaireDTO oCommanditaire) { this.oCommanditaire = oCommanditaire; }

    public void setFromDTO(final PrestationDTO objDTO) {
        setId( (objDTO.getId()==null?"":objDTO.getId().toString()) );
        setDatePrest(objDTO.getDatePrest());
        setDuree(objDTO.getDuree());
        setMontant(objDTO.getMontant());
        setEtat(objDTO.getEtat());
        setCommanditaireId(objDTO.getCommanditaireId());
        setDocArtisteId(objDTO.getDocArtisteId());
        setActiviteId(objDTO.getActiviteId());
        setSeDerouleId(objDTO.getSeDerouleId());

        setCommanditaire(objDTO.getCommanditaire());
        setDocArtiste(objDTO.getDocArtiste());
        setActivite(objDTO.getActivite());
        setSeDeroule(objDTO.getSeDeroule());
    }
    // ******************
    // Fonctions
    // ******************
    protected Boolean isNotOverMontantMax(double montant, int duree) throws IllegalArgumentException {


        if (montant > 128.93 * duree ) {
            throw new IllegalArgumentException("Montant maximal d'une prestation dépassé");
        }
        return true;
    }

    protected Boolean isNotOverDureeMax(int duree) throws IllegalArgumentException {
        if (duree > 7) {
            throw new IllegalArgumentException("Durée maximale d'une prestation dépassée");
        }
        return true;
    }

    public PrestationDTO getDTO()throws ParseException {
        PrestationDTO dto = new PrestationDTO();
        if( getId()!=null && !getId().isEmpty())
        dto.setId( convertUUID(getId()) );

        //isNotEmpty(getSeDerouleId());
        dto.setSeDerouleId(getSeDerouleId());

        dto.setSeDeroule(getSeDeroule());

        //isNotEmpty(getDocArtisteId());
        dto.setDocArtisteId(getDocArtisteId());

        dto.setDocArtiste(getDocArtiste());

        // isNotEmpty(getEtat()); A ajouter?? (commentaire Bernard G)
        dto.setEtat(getEtat());

        //isNotEmpty(getMontant());
        //convertDouble(getMontant());
        isNotOverMontantMax(getMontant(), getDuree());
        dto.setMontant( getMontant());

        //isNotEmpty(getDuree().getTime());
        isNotOverDureeMax(getDuree());
        dto.setDuree( getDuree());

        //isNotEmpty(getActiviteId());
        dto.setActiviteId(getActiviteId());

        dto.setActivite(getActivite());

        //isNotEmpty(getDatePrest());
        dto.setDatePrest( getDatePrest() );

        //isNotEmpty(getCommanditaireId());
        dto.setCommanditaireId( getCommanditaireId());

        dto.setCommanditaire( getCommanditaire() );

        return dto;
    }



}