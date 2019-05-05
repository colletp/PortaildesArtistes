package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.AdresseDTO;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Adresse extends DataForm<AdresseDTO> {

    // ******************
    // Champs/propriétés
    // ******************

    private String rue;
    private String numero;
    private String boite;
    private String ville;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public String getRue() { return rue; }
    public void setRue(String p_rue) { this.rue = p_rue; }
    public String getNumero() { ;return numero; }
    public void setNumero(String p_numero) { this.numero = p_numero; }
    public String getBoite() { return boite; }
    public void setBoite(String p_boite) { this.boite = p_boite; }
    public String getVille() { return ville; }
    public void setVille(String p_ville) { this.ville = p_ville; }

    public String toString() { return getRue()+", "+getNumero()+" "+getBoite()+" - "+getVille(); }

    public void setFromDTO(final AdresseDTO objDTO) {
        super.setFromDTO(objDTO);
        setRue(objDTO.getRue());
        setNumero(objDTO.getNumero());
        setBoite(objDTO.getBoite());
        setVille(objDTO.getVille());
    }
    // ******************
    // Fonctions
    // ******************

    Boolean isNumRue(String toValidate){
        Pattern patternNumRue = Pattern.compile("[0-9]+[A-Z]?$");
        Matcher testNumRue = patternNumRue.matcher(toValidate);
        if (!testNumRue.matches())
            throw new IllegalArgumentException("Numéro de rue non valide");
        return true;
    }

    public AdresseDTO getDTO()throws ParseException {
        AdresseDTO dto = new AdresseDTO();
        if( getId()!=null && !getId().isEmpty())
        dto.setId( convertUUID(getId()) );

        isNotEmpty(getRue());
        dto.setRue(getRue());

        isNumRue(getNumero());
        dto.setNumero(getNumero());

        dto.setBoite(getBoite());

        isNotEmpty(getVille());
        containsOnlyLetters(getVille());
        dto.setVille(getVille());

        return dto;
    }

}