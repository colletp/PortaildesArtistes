package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.util.*;

public class Prestation extends DataForm<PrestationDTO> {

    // ******************
    // Champs/propriétés
    // ******************

    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date datePrest;

    @NumberFormat(pattern = "#,###,###,###")
    private Integer duree;

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

    private Collection<SecteurDTO> lSecteurs;

    private List<String> lstActiviteID;
    private List<ActiviteDTO> lActToAddBySect;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public Date getDatePrest() { return datePrest; }
    public void setDatePrest(Date p_date) { this.datePrest = p_date; }
    public Integer getDuree() { return duree; }
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

    public List<String> getActivitesId(){ return lstActiviteID; }
    public void setActivitesId(List<String> lAct ){ lstActiviteID=lAct; }

    public void setSecteurActivites(Collection<SecteurDTO> ls){ lSecteurs=ls; }
    public Collection<SecteurDTO> getSecteurActivites(){ return lSecteurs; }

    public void setActToAddBySect( List<ActiviteDTO> lAct ){
        lActToAddBySect = lAct;
    }
    public List<ActiviteDTO> getActToAddBySect(){
        return lActToAddBySect;
    }

    public void setFromDTO(final PrestationDTO objDTO){
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

        List<String> la = new ArrayList<>();
        if( objDTO.getActivitesId()!=null )
            for( UUID uuid : objDTO.getActivitesId() )
                la.add( uuid.toString() );
        setActivitesId( la );

    }
    // ******************
    // Fonctions
    // ******************
    protected Boolean isNotOverMontantMax(double montant, int duree) throws IllegalArgumentException {
        if (montant > 128.93 * duree ){
            throw new IllegalArgumentException("maxprix");
        }
        return true;
    }

    protected Boolean isNotOverDureeMax(int duree) throws IllegalArgumentException {
        if (duree > 7){
            throw new IllegalArgumentException("dureemax");
        }
        return true;
    }

    public PrestationDTO getDTO(){
        PrestationDTO dto = new PrestationDTO();
        if( getId()!=null && !getId().isEmpty())
        dto.setId( convertUUID(getId()) );

        isNotEmpty(getSeDerouleId());
        dto.setSeDerouleId(getSeDerouleId());

        dto.setSeDeroule(getSeDeroule());

        isNotEmpty(getDocArtisteId());
        dto.setDocArtisteId(getDocArtisteId());

        dto.setDocArtiste(getDocArtiste());

        dto.setEtat( getEtat().isEmpty()?"Nouvelle":getEtat() );

        isNotEmpty( getMontant() );
        //convertDouble(getMontant());
        isNotOverMontantMax( getMontant(), getDuree() );
        dto.setMontant( getMontant());

        isNotEmpty( getDuree() );
        isNotOverDureeMax(getDuree());
        dto.setDuree( getDuree());

        isNotEmpty(getActiviteId());
        dto.setActiviteId(getActiviteId());

        dto.setActivite(getActivite());

        isNotEmpty(getDatePrest());
        dto.setDatePrest( getDatePrest() );

        isNotEmpty(getCommanditaireId());
        dto.setCommanditaireId( getCommanditaireId());

        dto.setCommanditaire( getCommanditaire() );

        List<UUID> activitesId = new ArrayList<>();
        for( String act : getActivitesId() ){
            logger.error(act);
            activitesId.add( UUID.fromString(act) );
        }

        return dto;
    }
}