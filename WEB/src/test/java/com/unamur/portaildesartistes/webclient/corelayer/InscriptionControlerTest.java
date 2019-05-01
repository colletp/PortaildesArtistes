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

    @DisplayName("TC 2.1, Test d’inscription avec des données valides")
    @Test
    void testInscriptValide21() {

        assertEquals(true,citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.2, Test d’inscription avec un mots de passe existant")
    @Test
    void testInscriptValide22() {
        user.setPassword("i234");
        assertEquals(true,citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.3, Test d’inscription avec un identifiant existant")
    @Test
    void testInscriptNonValide23() {
        user.setUsername("test1");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.4, Test d’inscription sans identifiant")
    @Test
    void testInscriptNonValide24() {
        user.setUsername("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.5, Test d’inscription avec un identifiant non conforme")
    @Test
    void testInscriptNonValide25() {
        user.setUsername("T");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.6, Test d’inscription sans mots de passe")
    @Test
    void testInscriptNonValide26() {
        user.setPassword("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.7, Test d’inscription avec un mots de passe trop long")
    @Test
    void testInscriptNonValide27() {
        user.setPassword("1235476800ss0");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.8, Test d’inscription avec un mots de passe trop faible")
    @Test
    void testInscriptNonValide28() {
        user.setPassword("0000");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.9, Test d’inscription avec un mots de passe trop faible")
    @Test
    void testInscriptNonValide29() {
        user.setPassword("1234");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.10, Test sur le nom, valeur absente")
    @Test
    void testInscriptNonValide210() {
        user.getCitoyen().setNom("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.11, Test sur le prénom, valeur absente")
    @Test
    void testInscriptNonValide211() {
        user.getCitoyen().setPrenom("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.12, Test sur le rue, valeur absente")
    @Test
    void testInscriptNonValide212() {
        user.getCitoyen().getResideAdr().setRue("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.13 Test sur le numéro de rue, valeur absente")
    @Test
    void testInscriptNonValide213() {
        user.getCitoyen().getResideAdr().setNumero("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.14, Test sur la localité, valeur absente")
    @Test
    void testInscriptNonValide214() {
        user.getCitoyen().getResideAdr().setVille("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.15, Test sur la nationalité, valeur absente")
    @Test
    void testInscriptNonValide215() {
        user.getCitoyen().setNation("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.16, Test sur la NRN, valeur absente, ")
    @Test
    void testInscriptNonValide216() {
        user.getCitoyen().setNrn("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.17, Test sur la NRN, format des valeurs non valide")
    @Test
    void testInscriptNonValide217() {
        user.getCitoyen().setNrn("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }
    @DisplayName("TC 2.18, Test sur la NRN, valeur erronée")
    @Test
    void testInscriptNonValide218() {
        user.getCitoyen().setNrn("11111111111");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.19, Test sur la NRN, valeur existancte")
    @Test
    void testInscriptNonValide219() {
        user.getCitoyen().setNrn("1212721501");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.20, Test sur le numéro de rue, valeur non valide")
    @Test
    void testInscriptNonValide220() {
        user.getCitoyen().getResideAdr().setNumero("Ab-c");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.21, Test sur la localité, valeur non valide")
    @Test
    void testInscriptNonValide221() {
        user.getCitoyen().getResideAdr().setVille("123");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.22, Test sur le numéro de téléphone, format des valeurs non valide")
    @Test
    void testInscriptNonValide222() {
        user.getCitoyen().setTel("Ab55486-6");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.23, Test sur le numéro de GSM, format des  valeurs non valide")
    @Test
    void testInscriptNonValide223() {
        user.getCitoyen().setGsm("Ab55482-6");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.24, Test sur le mail, manque le @")
    @Test
    void testInscriptNonValide224() {
        user.getCitoyen().setMail("abc-b.com");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.25, Test sur le mail, manque le .com ou autre")
    @Test
    void testInscriptNonValide225() {
        user.getCitoyen().setMail("abc@b");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }
/*
    @DisplayName("TC 2.26, Test sur la date de naissance, valeur absente")
    @Test
    void testInscriptNonValide226() {
        Date dateNaissance=new Date();
        dateNaissance=null;
        user.getCitoyen().setDateNaissance(dateNaissance);
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("TC 2.27, Test sur la date de naissance, format non conforme")
    @Test
    void testInscriptNonValide227() {
        Date dateNaissance=new Date();
        String dateNaiss = "32 13 AnIX";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try{
            dateNaissance = simpleDateFormat.parse(dateNaiss);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.getCitoyen().setDateNaissance(dateNaissance);
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }*/

    @DisplayName("TC 2.28, Test sur la date de naissance, date invalide")
    @Test
    void testInscriptNonValide228() {
        Date dateNaissance=new Date();
        String dateNaiss = "01/01/2025";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try{
            dateNaissance = simpleDateFormat.parse(dateNaiss);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.getCitoyen().setDateNaissance(dateNaissance);
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }
    @AfterEach
    void tearDown() {
        user=null;
        citoyen=null;
    }


}