package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.AdresseDTO;

import java.text.ParseException;

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

    // ******************
    // Fonctions
    // ******************
    public AdresseDTO getDTO()throws ParseException {
        AdresseDTO dto = new AdresseDTO();
        if( getId()!=null && !getId().isEmpty())
        dto.setId( convertUUID(getId()) );

        isNotEmpty(getRue());
        dto.setRue(getRue());

        isNotEmpty(getNumero());
        dto.setNumero(getNumero());

        dto.setBoite(getBoite());

        isNotEmpty(getVille());
        dto.setVille(getVille());

        return dto;
    }

}