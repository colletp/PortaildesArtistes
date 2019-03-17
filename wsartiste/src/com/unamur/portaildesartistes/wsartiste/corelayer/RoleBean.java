package com.unamur.portaildesartistes.wsartiste.corelayer;

import org.springframework.context.annotation.Bean;

import java.io.Serializable;

public class RoleBean implements Serializable {
    private Integer id;
    private String nomRole;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomRole() {
        return nomRole;
    }

    public void setNomRole(String nomRole) {
        this.nomRole = nomRole;
    }
}
