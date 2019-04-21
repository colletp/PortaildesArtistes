package com.unamur.portaildesartistes.DTO;

import java.io.Serializable;
import java.util.UUID;

public class GestionnaireDTO implements Serializable {

    // ******************
    // Champs/propriétés
    // ******************

    private UUID id;
    private UUID citoyenId;
    private UUID travailleId;
    private String matricule;
    private String bureau;

    private CitoyenDTO citoyen;
    private AdresseDTO travaille;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public UUID getId() { return id; }
    public void setId( UUID p_id) { this.id = p_id; }
    public UUID getCitoyenId() { return citoyenId; }
    public void setCitoyenId( UUID p_id) { this.citoyenId = p_id; }
    public UUID getTravailleId() { return travailleId; }
    public void setTravailleId( UUID p_id) { this.travailleId = p_id; }
    public String getMatricule() { return matricule; }
    public void setMatricule(String p_mat) { this.matricule = p_mat; }
    public String getBureau() { return bureau; }
    public void setBureau(String p_bur) { this.bureau = p_bur; }

    public CitoyenDTO getCitoyen() { return citoyen; }
    public void setCitoyen( CitoyenDTO p_ut) { this.citoyen = p_ut; }
    public AdresseDTO getTravaille() { return travaille; }
    public void setTravaille( AdresseDTO p_adr) { this.travaille = p_adr; }

    // ******************
    // Fonctions
    // ******************

}