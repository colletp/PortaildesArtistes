package com.unamur.portaildesartistes.dtoArtiste.corelayer;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

@Component
public class RoleDTO implements Serializable {
    private UUID id;
    private String nomRole;
    private String lang;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomRole() {
        return nomRole;
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

}
