package com.unamur.portaildesartistes.DTO;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CitoyenDTO implements Serializable, UserDetails {

    // ******************
    // Champs/propriétés
    // ******************

    private UUID id;
    private String nom;
    private String prenom;
    private Date date_naissance;
    private String tel;
    private String gsm;
    private String mail;
    private String nrn;
    private String nation;
    private String login;
    private String password;
    private UUID reside;

    private AdresseDTO resideAdr;
    private List<RoleDTO> roles;

    // ******************
    // Constructeur
    // ******************


    // ******************
    // Setter/Getter
    // ******************

    public UUID getId() { return id; }
    public void setId( UUID p_id) { this.id = p_id; }

    public String getNom() { return nom; }
    public void setNom(String p_nom) { this.nom = p_nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String p_prenom) { this.prenom = p_prenom; }
    public Date getDateNaissance() { return date_naissance; }
    public void setDateNaissance(Date p_date_naissance) { this.date_naissance= p_date_naissance; }
    public String getTel() { return tel; }
    public void setTel(String p_tel) { this.tel = p_tel; }
    public String getGsm() { return gsm; }
    public void setGsm(String p_gsm) { this.gsm = p_gsm; }
    public String getMail() { return mail; }
    public void setMail(String p_mail) { this.mail = p_mail; }
    public String getNrn() { return nrn; }
    public void setNrn(String p_nrn) { this.nrn = p_nrn; }
    public String getNation() { return nation; }
    public void setNation(String p_nation) { this.nation = p_nation; }
    public UUID getReside() { return reside; }
    public void setReside(UUID p_reside) { this.reside = p_reside; }

    public void setUserName(String p_login) { this.login = p_login; }
    public void setPassword(String p_password) { this.password = p_password; }

    public AdresseDTO getResideAdr() { return resideAdr; }
    public void setResideAdr(AdresseDTO p_reside) { this.resideAdr = p_reside; }

    public List<RoleDTO> getRoles() { return roles; }
    public void setRoles(List<RoleDTO> p_roles) { this.roles = p_roles; }

    // ******************
    // Fonctions
    // ******************
    public String getPassword() { return password; }
    public String getUsername() {
        return login;
    }
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}