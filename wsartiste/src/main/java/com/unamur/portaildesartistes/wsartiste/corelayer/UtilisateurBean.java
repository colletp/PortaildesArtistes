package com.unamur.portaildesartistes.wsartiste.corelayer;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//@Component

//@Entity
//@Table(name = "citoyen")
public class UtilisateurBean implements Serializable, UserDetails {

        //@Id
        //@GeneratedValue(strategy = GenerationType.IDENTITY)
/*        private Integer userId;
        private String username;
        private String password;
        public Integer getUserId() {
            return userId;
        }
        public void setUserId(Integer userId) {
            this.userId = userId;
        }*/
        public String getUsername() {
            return login;
        }

        @Override
        public boolean isAccountNonExpired() {
            return false;
        }
        @Override
        public boolean isAccountNonLocked() {
            return false;
        }
        @Override
        public boolean isCredentialsNonExpired() {
            return false;
        }
        @Override
        public boolean isEnabled() {
            return false;
        }
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }


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

    private AdresseBean resideAdr;
    private List<RoleBean> roles;
    //private boolean actif;

    // ******************
    // Constructeur
    // ******************


    // ******************
    // Setter/Getter
    // ******************

    public UUID getId() { return id; }
    public void setId( UUID p_id) { this.id = p_id; }

    public String getNom() { return nom; }
    public void setNom(String p_nom) { this.login = p_nom; }
    public String getPrenom() { return login; }
    public void setPrenom(String p_prenom) { this.login = p_prenom; }
    public Date getDateNaissance() { return date_naissance; }
    public void setDateNaissance(Date p_date_naissance) { this.date_naissance= p_date_naissance; }
    public String getTel() { return tel; }
    public void setTel(String p_tel) { this.login = p_tel; }
    public String getGsm() { return gsm; }
    public void setGsm(String p_gsm) { this.login = p_gsm; }
    public String getMail() { return mail; }
    public void setMail(String p_mail) { this.mail = p_mail; }
    public String getNrn() { return nrn; }
    public void setNrn(String p_nrn) { this.login = p_nrn; }
    public String getNation() { return nation; }
    public void setNation(String p_nation) { this.nation = p_nation; }
    public String getLogin() { return login; }
    public void setLogin(String p_login) { this.login = p_login; }
    public String getPassword() { return password; }
    public void setPassword(String p_password) { this.password = p_password; }
    public UUID getReside() { return reside; }
    public void setReside(UUID p_reside) { this.reside = p_reside; }

    public AdresseBean getResideAdr() { return resideAdr; }
    public void setResideAdr(AdresseBean p_reside) { this.resideAdr = p_reside; }

    public List<RoleBean> getRoles() { return roles; }
    public void setRoles(List<RoleBean> p_roles) { this.roles = p_roles; }

    // ******************
    // Fonctions
    // ******************

}