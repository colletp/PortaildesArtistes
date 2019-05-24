package com.unamur.portaildesartistes.DTO;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class RoleDTO extends DTO implements GrantedAuthority {

    private String nomRole;
    private String lang;
    //private FormulaireDTO.Lang langue;

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

    @Override
    public String getAuthority() {
        return getNomRole()+(getLang()==null?"":" "+getLang());
    }
}
