package com.unamur.portaildesartistes.DTO;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurDTO extends DTO implements UserDetails {
    private String login;
    private String password;

    private List<RoleDTO> authorities;

    private String sess;

    // ******************
    // Setter/Getter
    // ******************
    public String getUsername() {
        return login;
    }
    public void setUsername(String p_login) { this.login = p_login; }
    public String getPassword() { return password; }
    public void setPassword(String p_password) { this.password = p_password; }
    public List<RoleDTO> getAuthorities() {
        return authorities;
    }
    public void setAuthorities(List<RoleDTO> auth ){ if(authorities==null)authorities = new ArrayList<>(); authorities = auth; }

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

    @Override
    public boolean equals(Object obj){
        if( obj.getClass().getSimpleName().equals( this.getClass().getSimpleName() ) ){
            return this.equals( (UtilisateurDTO) obj );
        }

        return false;
    }
    private boolean equals(UtilisateurDTO usr){
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        if( ! this.getUsername().equals(usr.getUsername() ))return false;
        boolean passwordsMatch1 = encoder.matches( this.getPassword() // mot de passe qu'on a défini (non crypté)
                , usr.getPassword() //mot de passe repris de la DB (cryypté)
        );
        boolean passwordsMatch2 = encoder.matches( usr.getPassword() // mot de passe qu'on a défini (non crypté)
                , this.getPassword() //mot de passe repris de la DB (cryypté)
        );
        if( !passwordsMatch1 && !passwordsMatch2 )return false;

        // TODO tester le reste

        return true;
    }
}
