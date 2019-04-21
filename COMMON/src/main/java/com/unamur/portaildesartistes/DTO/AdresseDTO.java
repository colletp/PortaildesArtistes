package com.unamur.portaildesartistes.DTO;

import java.io.Serializable;
import java.util.UUID;

public class AdresseDTO implements Serializable {

    // ******************
    // Champs/propriétés
    // ******************

    private UUID id;
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

    public UUID getId() { return id; }
    public void setId( UUID p_id) { this.id = p_id; }
    public String getRue() { return rue; }
    public void setRue(String p_rue) { this.rue = p_rue; }
    public String getNumero() { return numero; }
    public void setNumero(String p_numero) { this.numero = p_numero; }
    public String getBoite() { return boite; }
    public void setBoite(String p_boite) { this.boite = p_boite; }
    public String getVille() { return ville; }
    public void setVille(String p_ville) { this.ville = p_ville; }

    public String toString() { return getRue()+", "+getNumero()+" "+getBoite()+" - "+getVille(); }

    // ******************
    // Fonctions
    // ******************

}