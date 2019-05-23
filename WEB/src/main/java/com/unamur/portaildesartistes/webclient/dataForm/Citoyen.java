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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void setFromDTO(final CitoyenDTO objDTO) {
        setId( (objDTO.getId()==null?"":objDTO.getId().toString()) );
        setNom(objDTO.getNom());
        setPrenom(objDTO.getPrenom());
        setDateNaissance(convertDate(objDTO.getDateNaissance()));
        setTel(objDTO.getTel());
        setGsm(objDTO.getGsm());
        setMail(objDTO.getMail());
        setNrn(objDTO.getNrn());
        setNation(objDTO.getNation());
        if(objDTO.getReside()!=null)
            setReside(objDTO.getReside().toString());
    }
    // ******************
    // Fonctions
    // ******************

    Boolean isValidNrn(String toValidate){
        //Contrôle de la validité de la valeur reprise dans le NRN

        if(toValidate.length()!=11)
            throw new IllegalArgumentException("Numéro de registre national incorrect");
        long nrn=Long.parseLong( toValidate );
        long val=nrn/100;
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateControle;
         try {
            dateControle = sdf.parse("31/12/1999");
        }catch(ParseException e){
            throw new IllegalArgumentException(e.getMessage());
        }
        if( convertDate(getDateNaissance()).after(dateControle) )
            val+=2000000000;
        long valControle = 97 - (val % 97);
        if(valControle!=(nrn%100))
            throw new IllegalArgumentException("Numéro de registre national incorrect");
        return true;
    }
    Boolean isMajor(Date dateNaissance){
        //Controle si le citoyen à plus de 18 ans
        if(dateNaissance==null){
            throw new IllegalArgumentException("Date absente");
        }
        Long resultat = new Date().getTime() - dateNaissance.getTime();
        Long majeur=Long.parseLong("567648000000");
        logger.error( Long.toString(resultat/1000/60/60/24/365 ) );
        if(resultat< majeur )
            throw new IllegalArgumentException("Citoyen n'est pas majeur ou n'est pas encore né");
        return true;
    }

    Boolean isTel(String toValidate){
        if(!toValidate.isEmpty()) {
            Pattern patternTel = Pattern.compile("[0-9]+/?[0-9]+$");
            Matcher testTel = patternTel.matcher(toValidate);
            if (!testTel.matches())
                throw new IllegalArgumentException("N° de tel ou de GSM non valide");
        }
        return true;
    }


    public CitoyenDTO getDTO(){
        CitoyenDTO dto = new CitoyenDTO();
        if( getId()!=null && !getId().isEmpty())
            dto.setId( convertUUID(getId()) );

        hasLengthMin(getNom(),2);
        containsOnlyLetters(getNom());
        dto.setNom( getNom() );

        hasLengthMin(getPrenom(),2);
        containsOnlyLetters(getPrenom());
        dto.setPrenom( getPrenom() );

        Date dateNais = convertDate(getDateNaissance());
        isMajor( dateNais );
        dto.setDateNaissance(dateNais);

        isTel(getTel());
        dto.setTel(getTel());

        isTel(getGsm());
        dto.setGsm(getGsm());

        isEmail(mail);
        dto.setMail(getMail());

        isValidNrn(nrn);
        dto.setNrn(getNrn());

        hasLengthMin(getNation(),3);
        containsOnlyLetters(getNation());
        dto.setNation(getNation());

        if(getReside()!=null && !getReside().isEmpty())
        dto.setReside( convertUUID(getReside()) );
        return dto;
    }
}