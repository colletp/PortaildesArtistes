package com.unamur.portaildesartistes.webclient.corelayer;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.unamur.portaildesartistes.DTO.AdresseDTO;
import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import org.apache.el.util.Validation;
import org.junit.jupiter.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class InscriptionControlerTest {

    private InscriptionControler citoyen;
    private UtilisateurDTO user;

    @BeforeEach
    void setUp() {
        citoyen=new InscriptionControler();
        user=new UtilisateurDTO();
        user.setUsername("teSt2");
        user.setPassword("i5Ts");
        user.setCitoyen(new CitoyenDTO());
        user.getCitoyen().setNom("Dupont");
        user.getCitoyen().setPrenom("Henri");
        Date dateNaissance=new Date();
        String dateNaiss = "01/01/2000";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try{
            dateNaissance = simpleDateFormat.parse(dateNaiss);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.getCitoyen().setDateNaissance(dateNaissance);
        user.getCitoyen().setResideAdr(new AdresseDTO());
        user.getCitoyen().getResideAdr().setRue("Petite rue d'en face");
        user.getCitoyen().getResideAdr().setNumero("6");
        user.getCitoyen().getResideAdr().setVille("Namur");
        user.getCitoyen().setNrn("01010100522");
        user.getCitoyen().setNation("Belge");
        user.getCitoyen().setTel("081010155");
        user.getCitoyen().setGsm("0495010155");
        user.getCitoyen().setMail("dupont.henry@bosso.be");
    }

    @AfterEach
    void tearDown() {
        user=null;
        citoyen=null;
    }


}