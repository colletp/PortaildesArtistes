package com.unamur.portaildesartistes.DTO;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UtilisateurDTO implements DTO, UserDetails {
    private UUID id;
    private String login;
    private String password;

    private List<? extends GrantedAuthority> authorities = new ArrayList<>();

    private String sess;

    // ******************
    // Setter/Getter
    // ******************
    public UUID getId() { return id; }
    public void setId(UUID p_id) { this.id = p_id; }
    public String getUsername() {
        return login;
    }
    public void setUsername(String p_login) { this.login = p_login; }
    public String getPassword() { return password; }
    public void setPassword(String p_password) { this.password = p_password; }
    public List<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public void setAuthorities(List<? extends GrantedAuthority> auth ) {
        authorities = auth;
    }

    private CitoyenDTO citoyenDTO;
    public void setCitoyen(CitoyenDTO citoyenDTO) { this.citoyenDTO = citoyenDTO; }
    public CitoyenDTO getCitoyen() { return citoyenDTO; }

    // ******************
    // Fonctions
    // ******************
    public boolean isAccountNonExpired() {
        return false;
    }
    public boolean isAccountNonLocked() {
        return false;
    }
    public boolean isCredentialsNonExpired() {
        return false;
    }
    public boolean isEnabled() {
        return false;
    }

    public String getSession(){ return sess; }
    public void setSession(String s){ sess=s; }

}
