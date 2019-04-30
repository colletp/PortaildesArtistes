package com.unamur.portaildesartistes.DTO;

import java.io.Serializable;
import java.util.UUID;

public class SecteurDTO implements DTO {

    // ******************
    // Champs/propriétés
    // ******************

    private UUID id;
    private String nomSecteur;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public UUID getId() { return id; }
    public void setId( UUID p_id) { this.id = p_id; }
    public String getNomSecteur() { return nomSecteur; }
    public void setNomSecteur(String p_secteur) { this.nomSecteur = p_secteur; }

    // ******************
    // Fonctions
    // ******************

}