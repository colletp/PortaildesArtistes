package com.unamur.portaildesartistes.DTO;

import java.io.Serializable;
import java.util.*;

public class DocArtisteDTO extends DTO {

    // ******************
    // Champs/propriétés
    // ******************

    private UUID citoyenId;
    private UUID reponseId;
    private String noDoc;
    private Date datePeremption;
    private String typeDocArtiste;

    private CitoyenDTO citoyen;
    private ReponseDTO reponse;

    //private List<ActiviteDTO> lActivites;
    private List<PrestationDTO> lPrest;

    private List<UUID> lActivitesId;
    private Collection<SecteurDTO> lSecteurs;

    // ******************
    // Constructeur
    // ******************

    public DocArtisteDTO(){
        lPrest = new ArrayList<>();
        lActivitesId = new ArrayList<>();
        lSecteurs = new ArrayList<>();
    }

    // ******************
    // Setter/Getter
    // ******************

    public UUID getCitoyenId() { return citoyenId; }
    public void setCitoyenId( UUID p_id) { this.citoyenId = p_id; }
    public UUID getReponseId() { return reponseId; }
    public void setReponseId( UUID p_id) { this.reponseId = p_id; }
    public String getNoDoc() { return noDoc; }
    public void setNoDoc(String p_noDoc) { this.noDoc = p_noDoc; }
    public Date getDatePeremption() { return datePeremption; }
    public void setDatePeremption(Date p_date) { this.datePeremption = p_date; }
    public String getTypeDocArtiste() { return typeDocArtiste; }
    public void setTypeDocArtiste(String p_type) { this.typeDocArtiste = p_type; }

    public CitoyenDTO getCitoyen() { return citoyen; }
    public void setCitoyen( CitoyenDTO p_ut) { this.citoyen = p_ut; }
    public ReponseDTO getReponse() { return reponse; }
    public void setReponse( ReponseDTO p_re) { this.reponse = p_re; }

    //public void setActivites(List<ActiviteDTO> l){this.lActivites=l;}
    //public List<ActiviteDTO> getActivites(){return this.lActivites;}

    public void setPrestations(List<PrestationDTO> l){this.lPrest=l;}
    public List<PrestationDTO> getPrestations(){return this.lPrest;}

    public void setActivitesId(List<UUID> l){lActivitesId=l;}
    public List<UUID> getActivitesId(){return lActivitesId;}
    public void setSecteurActivites(Collection<SecteurDTO> ls){lSecteurs=ls;}
    public Collection<SecteurDTO> getSecteurActivites(){return lSecteurs;}

    // ******************
    // Fonctions
    // ******************

}