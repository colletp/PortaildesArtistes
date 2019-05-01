package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.SecteurDTO;

import java.text.ParseException;
import java.util.UUID;

public class Secteur extends DataForm {

    // ******************
    // Champs/propriétés
    // ******************

    private String id;
    private String nomSecteur;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public UUID getId() { return (UUID)convert(id,UUID.class); }
    public void setId( String p_id) { this.id = p_id; }
    public String getNomSecteur() { return nomSecteur; }
    public void setNomSecteur(String p_secteur) { this.nomSecteur = p_secteur; }

    // ******************
    // Fonctions
    // ******************
    public SecteurDTO getDTO()throws ParseException {
        SecteurDTO dto = new SecteurDTO();
        dto.setId( getId() );
        dto.setNomSecteur(getNomSecteur() );
        return dto;
    }

}