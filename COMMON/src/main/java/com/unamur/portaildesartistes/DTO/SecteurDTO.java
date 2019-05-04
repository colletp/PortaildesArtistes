package com.unamur.portaildesartistes.DTO;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class SecteurDTO extends DTO {

    // ******************
    // Champs/propriétés
    // ******************

    private String nomSecteur;
    private List<ActiviteDTO> activites;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public String getNomSecteur() { return nomSecteur; }
    public void setNomSecteur(String p_secteur) { this.nomSecteur = p_secteur; }

    public void setActivites(List<ActiviteDTO> la ){ activites=la; }
    public List<ActiviteDTO> getActivites(){ return activites; }

    // ******************
    // Fonctions
    // ******************

}