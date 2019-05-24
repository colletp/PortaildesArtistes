package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.ReponseDTO;

import java.util.Date;
import java.util.UUID;
import java.util.Date;

public class Reponse extends DataForm<ReponseDTO> {

    // ******************
    // Champs/propriétés
    // ******************

    private String trtId;
    //private String citoyenId;
    private String dateReponse;
    private String reponse;
    private String reponsePositive;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public String getTrtId(){ return trtId; }
    public void setTrtId( String p_id){ trtId = p_id; }
    //public String getCitoyenId(){ return citoyenId; }
    //public void setCitoyenId( String p_id){ citoyenId = p_id; }
    public String getDateReponse() { return dateReponse; }
    public void setDateReponse(String p_date) { dateReponse = p_date; }

    public String getReponse() { return reponse; }
    public void setReponse(String p_rep) { reponse = p_rep; }

    public String getReponsePositive() { return reponsePositive; }
    public void setReponsePositive(String p_rep) { reponsePositive = p_rep; }


    public void setFromDTO(final ReponseDTO objDTO){
        setId( (objDTO.getId()==null?"":objDTO.getId().toString()) );
        setTrtId(objDTO.getTrtId().toString());
        //UUID citId = objDTO.getTrt().getCitoyenPrestId()!=null
        //            ?objDTO.getTrt().getCitoyenPrestId()
        //            :objDTO.getTrt().getForm().getCitoyenId();
        //setCitoyenId( citId.toString() );
        setDateReponse(convertDateTime(objDTO.getDateReponse()));
    }
    // ******************
    // Fonctions
    // ******************
    public ReponseDTO getDTO(){
        ReponseDTO dto = new ReponseDTO();
        if( getId()!=null && !getId().isEmpty())
        dto.setId( convertUUID(getId()) );
        //isNotEmpty(getDateReponse());
        dto.setDateReponse( convertDate(getDateReponse()) );
        dto.setReponse( getReponse() );

        dto.setReponsePositive( getReponsePositive().equals("1") );

        //isNotEmpty(getCitoyenId());
        //dto.setCitoyenId(convertUUID(getCitoyenId()));
        isNotEmpty(getTrtId());
        dto.setTrtId(convertUUID(getTrtId()));
        return dto;
    }

}