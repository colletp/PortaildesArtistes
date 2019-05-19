package com.unamur.portaildesartistes.DTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReponseDTO extends DTO {

    // ******************
    // Champs/propriétés
    // ******************

    private UUID trtId;
    private UUID citoyenId;
    private Timestamp dateReponse;

    private TraitementDTO trt;
    private CitoyenDTO citoyen;

    private List<DocArtisteDTO> lDocArt;
    // ******************
    // Constructeur
    // ******************

    public ReponseDTO(){
        citoyen = new CitoyenDTO();
        lDocArt = new ArrayList<>();
        trt = new TraitementDTO();
    }

    // ******************
    // Setter/Getter
    // ******************

    public UUID getTrtId(){ return trtId; }
    public void setTrtId( UUID p_id){ trtId = p_id; }
    public UUID getCitoyenId(){ return citoyenId; }
    public void setCitoyenId( UUID p_id){ citoyenId = p_id; }
    public Timestamp getDateReponse(){ return dateReponse; }
    public void setDateReponse(Timestamp p_date){ dateReponse = p_date; }

    public TraitementDTO getTrt(){ return trt; }
    public void setTrt( TraitementDTO p_trt){ trt = p_trt; }
    public CitoyenDTO getCitoyen(){ return citoyen; }
    public void setCitoyen( CitoyenDTO p_ut){ citoyen = p_ut; }

    public List<DocArtisteDTO> getDocArt(){ return lDocArt; }
    public void setDocArt( List<DocArtisteDTO> lDocArtDTO ){ lDocArt = lDocArtDTO; }

    // ******************
    // Fonctions
    // ******************

}