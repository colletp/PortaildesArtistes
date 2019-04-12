package com.unamur.portaildesartistes.DTO;

import java.io.Serializable;
import java.util.UUID;

public class ActiviteDTO implements Serializable {

    // ******************
    // Champs/propriétés
    // ******************

    private UUID id;
    private UUID idSecteur;
    private String nomActivite;

    private SecteurDTO sectDTO;
    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public UUID getId() { return id; }
    public void setId( UUID p_id) { this.id = p_id; }
    public UUID getIdSecteur() { return idSecteur; }
    public void setIdSecteur( UUID p_id) { this.idSecteur = p_id; }
    public String getNomActivite() { return nomActivite; }
    public void setNomActivite(String p_activite) { this.nomActivite = p_activite; }

    public SecteurDTO getSecteur() { return sectDTO; }
    public void setSecteur(SecteurDTO p_sect) { this.sectDTO = p_sect; }
    // ******************
    // Fonctions
    // ******************

}