package com.unamur.portaildesartistes.DTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CitoyenDTO extends DTO {

    // ******************
    // Champs/propriétés
    // ******************

    private String nom;
    private String prenom;
    private Date date_naissance;
    private String tel;
    private String gsm;
    private String mail;
    private String nrn;
    private String nation;
    private UUID reside;

    private AdresseDTO resideAdr;
    private List<RoleDTO> roles;
    private List<DocArtisteDTO> docs;
    private List<FormulaireDTO> forms;
    private GestionnaireDTO gestDTO;
    // ******************
    // Constructeur
    // ******************


    // ******************
    // Setter/Getter
    // ******************

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

    public AdresseDTO getResideAdr() { return resideAdr; }
    public void setResideAdr(AdresseDTO p_reside) { this.resideAdr = p_reside; }

    public List<RoleDTO> getRoles() { return roles; }
    public void setRoles(List<RoleDTO> p_roles) { this.roles = p_roles; }

    public List<DocArtisteDTO> getDocArtistes() { return docs; }
    public void setDocArtistes(List<DocArtisteDTO> p_docs) { this.docs = p_docs; }

    public List<FormulaireDTO> getFormulaires() { return forms; }
    public void setFormulaires(List<FormulaireDTO> p_forms) { this.forms = p_forms; }

    public GestionnaireDTO getGest(){ return gestDTO; }
    public void setGest(GestionnaireDTO gest){ gestDTO = gest; }

    // ******************
    // Fonctions
    // ******************
}