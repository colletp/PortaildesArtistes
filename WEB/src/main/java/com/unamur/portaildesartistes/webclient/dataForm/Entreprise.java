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

    public String getContactId() { return contactId; }
    public void setContactId( String p_id) { this.contactId = p_id; }
    public String getSiegeId() { return siegeId; }
    public void setSiegeId( String p_id) { this.siegeId = p_id; }

    public String getBce() { return bce; }
    public void setBce(String p_bce) { this.bce = p_bce; }
    public String getDenomination() { isNotEmpty(denomination);return denomination; }
    public void setDenomination(String p_denom) { this.denomination = p_denom; }
    public String getStatutLegal() { isNotEmpty(statutLegal);return statutLegal; }
    public void setStatutLegal(String p_sta) { this.statutLegal = p_sta; }

    public void setFromDTO(final EntrepriseDTO objDTO) {
        setId( (objDTO.getId()==null?"":objDTO.getId().toString()) );
        setContactId(objDTO.getContactId().toString());
        setSiegeId(objDTO.getSiegeId().toString());
        setBce(objDTO.getBce());
        setDenomination(objDTO.getDenomination());
        setStatutLegal(objDTO.getStatutLegal());

    }

    // ******************
    // Fonctions
    // ******************
    Boolean isValidBce(String toValidate){
        if( hasLengthMin(toValidate,10) )
            throw new IllegalArgumentException("bceko");
        return true;
    }
    public EntrepriseDTO getDTO(){
        EntrepriseDTO dto = new EntrepriseDTO();
        if( getId()!=null && !getId().isEmpty())
        dto.setId( convertUUID(getId()) );

        isNotEmpty(getContactId());
        dto.setContactId(convertUUID(getContactId()));

        isNotEmpty(getStatutLegal());
        dto.setStatutLegal(getStatutLegal());

        hasLengthMin(getDenomination(),2);
        dto.setDenomination(getDenomination());

        isNotEmpty(getBce());isValidBce(getBce());
        dto.setBce(getBce());

        dto.setSiegeId(convertUUID(getSiegeId()));
        return dto;
    }

}