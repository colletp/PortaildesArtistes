package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.CommanditaireDTO;
import com.unamur.portaildesartistes.DTO.EntrepriseDTO;

import java.text.ParseException;
import java.util.UUID;

public class Commanditaire extends DataForm<CommanditaireDTO> {

    // ******************
    // Champs/propriétés
    // ******************

    private String entrepriseId;
    private String citoyenId;

    private EntrepriseDTO oEntreprise;
    private CitoyenDTO oCitoyen;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public String getEntrepriseId() { return entrepriseId; }
    public void setEntrepriseId( String p_id) { this.entrepriseId = p_id; }
    public String getCitoyenId() { return citoyenId; }
    public void setCitoyenId( String p_id) { this.citoyenId = p_id; }

    public CitoyenDTO getCitoyen() {
        return oCitoyen;
    }
    public void setCitoyen(CitoyenDTO oCitoyen) {
        this.oCitoyen = oCitoyen;
    }

    public void setFromDTO(final CommanditaireDTO objDTO) {
        setId( (objDTO.getId()==null?"":objDTO.getId().toString()) );
        setEntrepriseId(objDTO.getEntrepriseId().toString());
        setCitoyenId(objDTO.getCitoyenId().toString());

        setCitoyen(objDTO.getCitoyen());
        setEntreprise(objDTO.getEntreprise());

    }
    // ******************
    // Fonctions
    // ******************
    public CommanditaireDTO getDTO(){
        CommanditaireDTO dto = new CommanditaireDTO();
        if( getId()!=null && !getId().isEmpty())
        dto.setId( convertUUID(getId()) );
        dto.setEntrepriseId( convertUUID(getEntrepriseId()));
        dto.setCitoyenId( convertUUID(getCitoyenId()));

        isNotEmpty(getCitoyenId());
        dto.setCitoyen(getCitoyen());

        isNotEmpty(getEntrepriseId());
        dto.setEntreprise(getEntreprise());


        return dto;
    }

    public EntrepriseDTO getEntreprise() {
        return oEntreprise;
    }

    public void setEntreprise(EntrepriseDTO oEntreprise) {
        this.oEntreprise = oEntreprise;
    }


}