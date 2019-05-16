package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.SecteurDTO;

import java.text.ParseException;
import java.util.UUID;

public class Secteur extends DataForm<SecteurDTO> {

    // ******************
    // Champs/propriétés
    // ******************

    private String nomSecteur;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public String getNomSecteur() { return nomSecteur; }
    public void setNomSecteur(String p_secteur) { nomSecteur = p_secteur; }

    public void setFromDTO(final SecteurDTO objDTO) {
        setId( (objDTO.getId()==null?"":objDTO.getId().toString()) );
        setNomSecteur(objDTO.getNomSecteur());
    }
    // ******************
    // Fonctions
    // ******************
    public SecteurDTO getDTO(){
        SecteurDTO dto = new SecteurDTO();
        if( getId()!=null && !getId().isEmpty())

        isNotEmpty(getId());
        dto.setId( convertUUID(getId()) );

        isNotEmpty(getNomSecteur());
        dto.setNomSecteur(getNomSecteur() );
        return dto;
    }

}