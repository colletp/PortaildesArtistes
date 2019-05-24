package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.TraitementDTO;

public class Traitement extends DataForm<TraitementDTO> {

    // ******************
    // Champs/propriétés
    // ******************

    private String dateTrt;
    private String appreciation;
    private String gestId;
    private String formId;
    private String citoyenPrestId;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public String getDateTrt() { return dateTrt; }
    public void setDateTrt(String p_date) { dateTrt = p_date; }
    public String getAppreciation() { return appreciation; }
    public void setAppreciation( String p_ap) { appreciation = p_ap; }
    public String getGestId() {return gestId; }
    public void setGestId( String p_id) { gestId = p_id; }
    public String getFormId() { return formId; }
    public void setFormId( String p_id) { formId = p_id; }
    public String getCitoyenPrestId() { return citoyenPrestId; }
    public void setCitoyenPrestId( String p_id) { citoyenPrestId = p_id; }

    public void setFromDTO(final TraitementDTO objDTO){
        setId( (objDTO.getId()==null?"":objDTO.getId().toString()) );
        setDateTrt( convertDateTime(objDTO.getDateTrt()) );
        setAppreciation(objDTO.getAppreciation());
        setGestId(objDTO.getGestId().toString());
        setFormId(objDTO.getFormId().toString());
        setCitoyenPrestId(objDTO.getCitoyenPrestId()==null?null:objDTO.getCitoyenPrestId().toString());
    }
    // ******************
    // Fonctions
    // ******************
    public TraitementDTO getDTO(){
        TraitementDTO dto = new TraitementDTO();
        if( getId()!=null && !getId().isEmpty())
        dto.setId( convertUUID(getId()) );

        //isNotEmpty(getGestId()); //--> ajouté lors de l'insertion avec le gestionnaire courant
        dto.setGestId( convertUUID(getGestId()) );

        isNotEmpty(getFormId());
        dto.setFormId( convertUUID(getFormId()) );

        //isNotEmpty(getCitoyenPrestId());//pas besoin de ce champ (inutile pour le traitement d'un formulaire)
        dto.setCitoyenPrestId( convertUUID(getCitoyenPrestId()) );

        //isNotEmpty(getDateTrt());//--> ajouté lors de l'insertion avec le gestionnaire courant
        dto.setDateTrt( convertDate(getDateTrt()) );

        hasLengthMin(getAppreciation(),10);
        dto.setAppreciation(getAppreciation());

        return dto;
    }

}