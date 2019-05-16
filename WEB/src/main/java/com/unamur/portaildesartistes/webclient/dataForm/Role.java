package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.RoleDTO;

import java.text.ParseException;
import java.util.UUID;

public class Role extends DataForm<RoleDTO> {
    private String nomRole;
    private String lang;

    public String getNomRole() { isNotEmpty(nomRole); return nomRole; }
    public void setNomRole(String nomRole) { this.nomRole = nomRole; }
    public String getLang() { return lang; }
    public void setLang(String lang) { this.lang = lang; }

    public void setFromDTO(final RoleDTO objDTO) {
        setId( (objDTO.getId()==null?"":objDTO.getId().toString()) );
        setNomRole(objDTO.getNomRole());
        setLang(objDTO.getLang());
    }


    public RoleDTO getDTO(){
        RoleDTO dto = new RoleDTO();
        if( getId()!=null && !getId().isEmpty())
        dto.setId( convertUUID(getId()) );
        dto.setLang(getLang() );
        dto.setNomRole(getNomRole());
        return dto;
    }

}
