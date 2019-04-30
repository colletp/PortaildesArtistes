package com.unamur.portaildesartistes.DTO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

public class ReponseDTO implements DTO {

    // ******************
    // Champs/propriétés
    // ******************

    private UUID id;
    private UUID trtId;
    private UUID citoyenId;
    private Timestamp dateReponse;

    private TraitementDTO trt;
    private CitoyenDTO citoyen;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public UUID getId() { return id; }
    public void setId( UUID p_id) { this.id = p_id; }
    public UUID getTrtId(){ return trtId; }
    public void setTrtId( UUID p_id){ this.trtId = p_id; }
    public UUID getCitoyenId(){ return citoyenId; }
    public void setCitoyenId( UUID p_id){ this.citoyenId = p_id; }
    public Timestamp getDateReponse() { return dateReponse; }
    public void setDateReponse(Timestamp p_date) { this.dateReponse = p_date; }

    public TraitementDTO getTrt() { return trt; }
    public void setTrt( TraitementDTO p_trt) { this.trt = p_trt; }
    public CitoyenDTO getCitoyen() { return citoyen; }
    public void setCitoyen( CitoyenDTO p_ut) { this.citoyen = p_ut; }

    // ******************
    // Fonctions
    // ******************

}