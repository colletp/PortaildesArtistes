package com.unamur.portaildesartistes.DTO;

import java.io.Serializable;
import java.util.UUID;

public class ActiviteDTO extends DTO {

    // ******************
    // Champs/propriétés
    // ******************

    private UUID secteurId;
    private String nomActivite;

    private SecteurDTO sectDTO;
    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public UUID getSecteurId() { return secteurId; }
    public void setSecteurId( UUID p_id) { this.secteurId = p_id; }
    public String getNomActivite() { return nomActivite; }
    public void setNomActivite(String p_activite) { this.nomActivite = p_activite; }

    public SecteurDTO getSecteur() { return sectDTO; }
    public void setSecteur(SecteurDTO p_sect) { this.sectDTO = p_sect; }
    // ******************
    // Fonctions
    // ******************

}