package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.RoleDTO;

import java.text.ParseException;
import java.util.UUID;

public class Role extends DataForm {
    private String id;
    private String nomRole;
    private String lang;

    public UUID getId() {
        return (UUID)convert(id,UUID.class);
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNomRole() {
        isNotEmpty(nomRole); return nomRole;
    }
    public void setNomRole(String nomRole) {
        this.nomRole = nomRole;
    }
    public String getLang() {
        return lang;
    }
    public void setLang(String lang) {
        this.lang = lang;
    }


    public RoleDTO getDTO()throws ParseException {
        RoleDTO dto = new RoleDTO();
        dto.setId( getId() );
        dto.setLang(getLang() );
        dto.setNomRole(getNomRole());
        return dto;
    }

}
