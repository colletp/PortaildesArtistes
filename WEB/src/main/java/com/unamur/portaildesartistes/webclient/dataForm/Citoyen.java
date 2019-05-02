package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import javassist.NotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

public class Citoyen extends DataForm<CitoyenDTO> {

    // ******************
    // Champs/propriétés
    // ******************

    private String nom;
    private String prenom;
    private String date_naissance;
    private String tel;
    private String gsm;
    private String mail;
    private String nrn;
    private String nation;
    private String reside;
    private String resideAdr;

    // ******************
    // Constructeur
    // ******************


    // ******************
    // Setter/Getter
    // ******************

    public String getNom() {
        hasLengthMin(nom,2);
		containsOnlyLetters(nom);
        return nom; }
    public void setNom(String p_nom) { this.nom = p_nom; }
    public String getPrenom() {
        hasLengthMin(prenom,2);
		containsOnlyLetters(prenom);
        return prenom;
    }
    public void setPrenom(String p_prenom) { this.prenom = p_prenom; }
    public Date getDateNaissance() {
        Date date = convertDate(date_naissance);
        isMajor( date );
        return date;
    }
    public void setDateNaissance(String p_date_naissance) { this.date_naissance= p_date_naissance; }
    public String getTel() {
        isTel(tel);
        return tel;
    }
    public void setTel(String p_tel) { this.tel = p_tel; }
    public String getGsm() {
        isTel(gsm);
        return gsm;
    }
    public void setGsm(String p_gsm) { this.gsm = p_gsm; }
    public String getMail() {
        isEmail(mail);
        return mail;
    }
    public void setMail(String p_mail) { this.mail = p_mail; }
    public String getNrn()throws ParseException {
        isValidNrn(nrn);
        return nrn;
    }
    public void setNrn(String p_nrn) { this.nrn = p_nrn; }
    public String getNation() {
        hasLengthMin(nation,3);
        containsOnlyLetters(nation);
		return nation;
    }
    public void setNation(String p_nation) { this.nation = p_nation; }
    public UUID getReside(){return convertUUID( reside  );}
    public void setReside(String p_reside) { this.reside = p_reside; }

    // ******************
    // Fonctions
    // ******************

    Boolean isValidNrn(String toValidate)throws ParseException{
        //Contrôle de la validité de la valeur reprise dans le NRN

        if(toValidate.length()!=11)
            throw new IllegalArgumentException("Numéro de registre national incorrect");
        int nrn=Integer.parseInt( toValidate );
        int val=nrn/100;
        Date dateControle = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/1999");
        if( getDateNaissance().after(dateControle) )
            val+=2000000000;
        int valControle = 97 - (val % 97);
       //TODO: attention, val%97 varie de 0 à 96 => valControle n'est jamais == 0 . Vérifier la formule
        if (valControle==0)
            valControle=97;
        if(valControle!=(nrn%100))
            throw new IllegalArgumentException("Numéro de registre national incorrect");
        return true;
    }
    Boolean isMajor(Date dateNaissance){
        //Controle si le citoyen à plus de 18 ans
        long resultat;
        resultat = ChronoUnit.YEARS.between( (new Date()).toInstant() , dateNaissance.toInstant());
        if(resultat<18)
            throw new IllegalArgumentException("Citoyen n'est pas majeur ou n'est pas encore né");
        return true;
    }

    public CitoyenDTO getDTO()throws ParseException {
        CitoyenDTO cit = new CitoyenDTO();
        cit.setId( getId() );
        cit.setNom( getNom() );
        cit.setDateNaissance(getDateNaissance());
        cit.setTel(getTel());
        cit.setGsm(getGsm());
        cit.setMail(getMail());
        cit.setNrn(getNrn());
        cit.setNation(getNation());
        cit.setReside(getReside());
        return cit;
    }
}