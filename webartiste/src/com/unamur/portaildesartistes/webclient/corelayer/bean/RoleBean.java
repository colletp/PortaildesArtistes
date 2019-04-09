package com.unamur.portaildesartistes.webclient.corelayer.bean;

public class RoleBean implements java.io.Serializable {
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
