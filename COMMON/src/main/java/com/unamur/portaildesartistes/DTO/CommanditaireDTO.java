package com.unamur.portaildesartistes.DTO;

import java.io.Serializable;
import java.util.UUID;

public class CommanditaireDTO extends DTO {

    // ******************
    // Champs/propriétés
    // ******************

    private UUID entrepriseId;
    private UUID citoyenId;

    private EntrepriseDTO entreprise;
    private CitoyenDTO citoyen;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public UUID getEntrepriseId() { return entrepriseId; }
    public void setEntrepriseId( UUID p_id) { this.entrepriseId = p_id; }
    public UUID getCitoyenId() { return citoyenId; }
    public void setCitoyenId( UUID p_id) { this.citoyenId = p_id; }

    public EntrepriseDTO getEntreprise() { return entreprise; }
    public void setEntreprise( EntrepriseDTO p_id) { this.entreprise = p_id; }
     public CitoyenDTO getCitoyen() { return citoyen; }
    public void setCitoyen( CitoyenDTO p_id) { this.citoyen = p_id; }

    // ******************
    // Fonctions
    // ******************

}