package com.unamur.portaildesartistes.webclient.corelayer;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.unamur.portaildesartistes.DTO.AdresseDTO;
import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Adresse;
import com.unamur.portaildesartistes.webclient.dataForm.Citoyen;
import com.unamur.portaildesartistes.webclient.dataForm.Utilisateur;
import com.unamur.portaildesartistes.webclient.dataForm.UtilisateurInscript;
import org.apache.el.util.Validation;
import org.junit.jupiter.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class InscriptionControlerTest {

    private InscriptionControler inscCtrl;
    private UtilisateurInscript usrInscr;

    @BeforeEach
    void setUp() {
        inscCtrl=new InscriptionControler();

        Utilisateur usr;
        Citoyen citoyen;
        Adresse adresse;

        usr=new Utilisateur();
        usr.setUsername("teSt2");
        usr.setPassword("i5Ts");
        citoyen=new Citoyen();
        usrInscr.getCitoyen().setNom("Dupont");
        usrInscr.getCitoyen().setPrenom("Henri");
        usrInscr.getCitoyen().setDateNaissance("01/01/2000");
        adresse = new Adresse();
        usrInscr.getAdresse().setRue("Petite rue d'en face");
        usrInscr.getAdresse().setNumero("6");
        usrInscr.getAdresse().setVille("Namur");
        usrInscr.getCitoyen().setNrn("01010100522");
        usrInscr.getCitoyen().setNation("Belge");
        usrInscr.getCitoyen().setTel("081010155");
        usrInscr.getCitoyen().setGsm("0495010155");
        usrInscr.getCitoyen().setMail("dupont.henry@bosso.be");
        usrInscr.setUtilisateur(usr);
        usrInscr.setCitoyen(citoyen);
        usrInscr.setAdresse(adresse);
    }

    @DisplayName("TC 2.1, Test d’inscription avec des données valides")
    @Test
    void testInscriptValide21() {
        assertDoesNotThrow( ()->usrInscr.getDTO() );
        //assertEquals(true,inscCtrl.ValideInscript(usrInscr));
    }

    @DisplayName("TC 2.2, Test d’inscription avec un mots de passe existant")
    @Test
    void testInscriptValide22() {
        usrInscr.getUtilisateur().setPassword("i234");
        assertDoesNotThrow( ()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.3, Test d’inscription avec un identifiant existant")
    @Test
    void testInscriptNonValide23() {
        usrInscr.getUtilisateur().setUsername("test1");
        assertDoesNotThrow( ()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.4, Test d’inscription sans identifiant")
    @Test
    void testInscriptNonValide24() {
        usrInscr.getUtilisateur().setUsername("");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO());
    }

    @DisplayName("TC 2.5, Test d’inscription avec un identifiant non conforme")
    @Test
    void testInscriptNonValide25() {
        usrInscr.getUtilisateur().setUsername("T");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.6, Test d’inscription sans mots de passe")
    @Test
    void testInscriptNonValide26() {
        usrInscr.getUtilisateur().setPassword("");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.7, Test d’inscription avec un mots de passe trop long")
    @Test
    void testInscriptNonValide27() {
        usrInscr.getUtilisateur().setPassword("1235476800ss0");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.8, Test d’inscription avec un mots de passe trop faible")
    @Test
    void testInscriptNonValide28() {
        usrInscr.getUtilisateur().setPassword("0000");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.9, Test d’inscription avec un mots de passe trop faible")
    @Test
    void testInscriptNonValide29() {
        usrInscr.getUtilisateur().setPassword("1234");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.10, Test sur le nom, valeur absente")
    @Test
    void testInscriptNonValide210() {
        usrInscr.getCitoyen().setNom("");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.11, Test sur le prénom, valeur absente")
    @Test
    void testInscriptNonValide211() {
        usrInscr.getCitoyen().setPrenom("");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.12, Test sur le rue, valeur absente")
    @Test
    void testInscriptNonValide212() {
        usrInscr.getAdresse().setRue("");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.13 Test sur le numéro de rue, valeur absente")
    @Test
    void testInscriptNonValide213() {
        usrInscr.getAdresse().setNumero("");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.14, Test sur la localité, valeur absente")
    @Test
    void testInscriptNonValide214() {
        usrInscr.getAdresse().setVille("");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.15, Test sur la nationalité, valeur absente")
    @Test
    void testInscriptNonValide215() {
        usrInscr.getCitoyen().setNation("");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.16, Test sur la NRN, valeur absente, ")
    @Test
    void testInscriptNonValide216() {
        usrInscr.getCitoyen().setNrn("");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.17, Test sur la NRN, format des valeurs non valide")
    @Test
    void testInscriptNonValide217() {
        usrInscr.getCitoyen().setNrn("");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }
    @DisplayName("TC 2.18, Test sur la NRN, valeur erronée")
    @Test
    void testInscriptNonValide218() {
        usrInscr.getCitoyen().setNrn("11111111111");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.19, Test sur la NRN, valeur existancte")
    @Test
    void testInscriptNonValide219() {
        usrInscr.getCitoyen().setNrn("1212721501");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.20, Test sur le numéro de rue, valeur non valide")
    @Test
    void testInscriptNonValide220() {
        usrInscr.getAdresse().setNumero("Ab-c");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.21, Test sur la localité, valeur non valide")
    @Test
    void testInscriptNonValide221() {
        usrInscr.getAdresse().setVille("123");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.22, Test sur le numéro de téléphone, format des valeurs non valide")
    @Test
    void testInscriptNonValide222() {
        usrInscr.getCitoyen().setTel("Ab55486-6");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.23, Test sur le numéro de GSM, format des  valeurs non valide")
    @Test
    void testInscriptNonValide223() {
        usrInscr.getCitoyen().setGsm("Ab55482-6");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.24, Test sur le mail, manque le @")
    @Test
    void testInscriptNonValide224() {
        usrInscr.getCitoyen().setMail("abc-b.com");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }

    @DisplayName("TC 2.25, Test sur le mail, manque le .com ou autre")
    @Test
    void testInscriptNonValide225() {
        usrInscr.getCitoyen().setMail("abc@b");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }
/*
    @DisplayName("TC 2.26, Test sur la date de naissance, valeur absente")
    @Test
    void testInscriptNonValide226() {
        Date dateNaissance=new Date();
        dateNaissance=null;
        usrInscr.getCitoyen().setDateNaissance(dateNaissance);
        assertThrows(IllegalArgumentException.class,()->usrInscr.getCitoyen().ValideInscript(usrInscr));
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
        usrInscr.getCitoyen().setDateNaissance(dateNaissance);
        assertThrows(IllegalArgumentException.class,()->usrInscr.getCitoyen().ValideInscript(usrInscr));
    }*/

    @DisplayName("TC 2.28, Test sur la date de naissance, date invalide")
    @Test
    void testInscriptNonValide228() {
        usrInscr.getCitoyen().setDateNaissance("01/01/2025");
        assertThrows(IllegalArgumentException.class,()->usrInscr.getDTO() );
    }
    @AfterEach
    void tearDown() {
        usrInscr=null;
        inscCtrl=null;
    }


}