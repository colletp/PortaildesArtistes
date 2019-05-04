package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

public class Citoyen extends DataForm<CitoyenDTO> {
    private static final Logger logger = LoggerFactory.getLogger(com.unamur.portaildesartistes.webclient.dataForm.Citoyen.class);

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

    public String getNom() { return nom; }
    public void setNom(String p_nom) { this.nom = p_nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String p_prenom) { this.prenom = p_prenom; }
    public String getDateNaissance() { return date_naissance; }
    public void setDateNaissance(String p_date_naissance) { this.date_naissance= p_date_naissance; }
    public String getTel() { return tel; }
    public void setTel(String p_tel) { this.tel = p_tel; }
    public String getGsm() { return gsm; }
    public void setGsm(String p_gsm) { this.gsm = p_gsm; }
    public String getMail() { return mail; }
    public void setMail(String p_mail) { this.mail = p_mail; }
    public String getNrn(){ return nrn; }
    public void setNrn(String p_nrn) { this.nrn = p_nrn; }
    public String getNation() { return nation; }
    public void setNation(String p_nation) { this.nation = p_nation; }
    public String getReside(){return reside;}
    public void setReside(String p_reside) { this.reside = p_reside; }

    // ******************
    // Fonctions
    // ******************

    Boolean isValidNrn(String toValidate)throws ParseException{
        //Contrôle de la validité de la valeur reprise dans le NRN
        if(toValidate.length()!=11)
            throw new IllegalArgumentException("Numéro de registre national incorrect");
/*        int nrn=Integer.parseInt( toValidate );
        int val=nrn/100;
        Date dateControle = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/1999");
        if( convertDate(getDateNaissance()).after(dateControle) )
            val+=2000000000;
        int valControle = 97 - (val % 97);
       //TODO: attention, val%97 varie de 0 à 96 => valControle n'est jamais == 0 . Vérifier la formule
        if (valControle==0)
            valControle=97;
        if(valControle!=(nrn%100))
            throw new IllegalArgumentException("Numéro de registre national incorrect");*/
        return true;
    }
    Boolean isMajor(Date dateNaissance){
        //Controle si le citoyen à plus de 18 ans
        Long resultat = new Date().getTime() - dateNaissance.getTime();
        logger.error( Long.toString(resultat/1000/60/60/24/365 ) );
        if(resultat< (18*365*24*60*60*1000) )
            throw new IllegalArgumentException("Citoyen n'est pas majeur ou n'est pas encore né");
        return true;
    }

    public CitoyenDTO getDTO()throws ParseException {
        logger.error("getDTO");
        CitoyenDTO dto = new CitoyenDTO();
        if( getId()!=null && !getId().isEmpty())
        dto.setId( convertUUID(getId()) );
        logger.error("id OK");

        hasLengthMin(getNom(),2);
        containsOnlyLetters(getNom());
        dto.setNom( getNom() );
        logger.error("nom OK");

        hasLengthMin(getPrenom(),2);
        containsOnlyLetters(getPrenom());
        dto.setPrenom( getPrenom() );
        logger.error("prenom OK");

        Date dateNais = convertDate(getDateNaissance());
        isMajor( dateNais );
        dto.setDateNaissance(dateNais);
        logger.error("dateNais OK");

        isTel(getTel());
        dto.setTel(getTel());
        logger.error("telOK");

        isTel(getGsm());
        dto.setGsm(getGsm());
        logger.error("gsm OK");

        isEmail(mail);
        dto.setMail(getMail());
        logger.error("mail OK");

        isValidNrn(nrn);
        dto.setNrn(getNrn());
        logger.error("nrn OK");

        hasLengthMin(getNation(),3);
        containsOnlyLetters(getNation());
        dto.setNation(getNation());
        logger.error("nation OK");

        if(getReside()!=null && !getReside().isEmpty())
        dto.setReside( convertUUID(getReside()) );
        logger.error("reside OK");
        return dto;
    }
}