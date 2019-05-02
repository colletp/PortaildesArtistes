package com.unamur.portaildesartistes.DTO;

import java.io.Serializable;
import java.util.UUID;

public class EntrepriseDTO extends DTO {

    // ******************
    // Champs/propriétés
    // ******************

    private UUID contactId;
    private UUID siegeId;
    private String bce;
    private String denomination;
    private String statutLegal;

    private CitoyenDTO contact;
    private AdresseDTO siege;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public UUID getContactId() { return contactId; }
    public void setContactId( UUID p_id) { this.contactId = p_id; }
    public UUID getSiegeId() { return siegeId; }
    public void setSiegeId( UUID p_id) { this.siegeId = p_id; }

    public String getBce() { return bce; }
    public void setBce(String p_bce) { this.bce = p_bce; }
    public String getDenomination() { return denomination; }
    public void setDenomination(String p_denom) { this.denomination = p_denom; }
    public String getStatutLegal() { return statutLegal; }
    public void setStatutLegal(String p_sta) { this.statutLegal = p_sta; }

    public CitoyenDTO getContact() { return contact; }
    public void setContact( CitoyenDTO p_ut) { this.contact = p_ut; }
    public AdresseDTO getSiege() { return siege; }
    public void setSiege( AdresseDTO p_adr) { this.siege = p_adr; }

    // ******************
    // Fonctions
    // ******************

}