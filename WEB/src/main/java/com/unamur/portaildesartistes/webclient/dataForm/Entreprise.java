package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.EntrepriseDTO;

import java.text.ParseException;
import java.util.UUID;

public class Entreprise extends DataForm<EntrepriseDTO> {

    // ******************
    // Champs/propriétés
    // ******************

    private String contactId;
    private String siegeId;
    private String bce;
    private String denomination;
    private String statutLegal;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public UUID getContactId() { isNotEmpty(contactId);return convertUUID(contactId); }
    public void setContactId( String p_id) { this.contactId = p_id; }
    public UUID getSiegeId() { isNotEmpty(siegeId);return convertUUID(siegeId); }
    public void setSiegeId( String p_id) { this.siegeId = p_id; }

    public String getBce() { isNotEmpty(bce);isValidBce(bce);return bce; }
    public void setBce(String p_bce) { this.bce = p_bce; }
    public String getDenomination() { isNotEmpty(denomination);return denomination; }
    public void setDenomination(String p_denom) { this.denomination = p_denom; }
    public String getStatutLegal() { isNotEmpty(statutLegal);return statutLegal; }
    public void setStatutLegal(String p_sta) { this.statutLegal = p_sta; }

    // ******************
    // Fonctions
    // ******************
    Boolean isValidBce(String toValidate){
        if( hasLengthMin(toValidate,10) )
            throw new IllegalArgumentException("Num BCE incorrect");
        return true;
    }
    public EntrepriseDTO getDTO()throws ParseException {
        EntrepriseDTO dto = new EntrepriseDTO();
        dto.setId( getId() );
        dto.setContactId(getContactId());
        dto.setStatutLegal(getStatutLegal());
        dto.setDenomination(getDenomination());
        dto.setBce(getBce());
        dto.setSiegeId(getSiegeId());
        return dto;
    }

}