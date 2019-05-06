package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CitoyenTest {

    private Citoyen citoyen;
    private CitoyenDTO cit;

    @BeforeEach
    void setUp() {
        citoyen=new Citoyen();
        cit=null;
        citoyen.setNom("Dupont");
        citoyen.setPrenom("Henri");
        citoyen.setDateNaissance("01/01/2001");
        citoyen.setNrn("01010100522");
        citoyen.setNation("Belge");
        citoyen.setTel("081010155");
        citoyen.setGsm("0495010155");
        citoyen.setMail("dupont.henry@bosso.be");
    }

    @AfterEach
    void tearDown() {
        citoyen=null;
        cit=null;
    }

    @DisplayName("TC 2.1, Test d’inscription avec des données valides")
    @Test
    void testInscriptValide21() {
        try {
            cit = citoyen.getDTO();
        }catch (ParseException | IllegalArgumentException  e) {
            e.printStackTrace();
        }

        assertEquals(cit.getDateNaissance(),citoyen.convertDate(citoyen.getDateNaissance()));
        assertEquals(cit.getNom(),citoyen.getNom());
        assertEquals(cit.getPrenom(),citoyen.getPrenom());
        assertEquals(cit.getNrn(),citoyen.getNrn());
        assertEquals(cit.getNation(),citoyen.getNation());
        assertEquals(cit.getTel(),citoyen.getTel());
        assertEquals(cit.getGsm(),citoyen.getGsm());
        assertEquals(cit.getMail(),citoyen.getMail());
    }

    @DisplayName("TC 2.10, Test sur le nom, valeur absente")
    @Test
    void testInscriptNonValide210() {
        citoyen.setNom("");

        assertThrows(IllegalArgumentException.class,()-> citoyen.getDTO() );
    }

    @DisplayName("TC 2.11, Test sur le prénom, valeur absente")
    @Test
    void testInscriptNonValide211() {
        citoyen.setPrenom("");
        assertThrows(IllegalArgumentException.class,()->citoyen.getDTO());
    }



    @DisplayName("TC 2.15, Test sur la nationalité, valeur absente")
    @Test
    void testInscriptNonValide215() {
        citoyen.setNation("");
        assertThrows(IllegalArgumentException.class,()->citoyen.getDTO());
    }

    @DisplayName("TC 2.16, Test sur la NRN, valeur absente, ")
    @Test
    void testInscriptNonValide216() {
        citoyen.setNrn("");
        assertThrows(IllegalArgumentException.class,()->citoyen.getDTO());
    }

    @DisplayName("TC 2.17, Test sur la NRN, format des valeurs non valide")
    @Test
    void testInscriptNonValide217() {
        citoyen.setNrn("12721A5a2");
        assertThrows(IllegalArgumentException.class,()->citoyen.getDTO());
    }
    @DisplayName("TC 2.18, Test sur la NRN, valeur erronée")
    @Test
    void testInscriptNonValide218() {
        citoyen.setNrn("11111111111");
        assertThrows(IllegalArgumentException.class,()->citoyen.getDTO());
    }

    @DisplayName("TC 2.19, Test sur la NRN, valeur existante")
    @Test
    void testInscriptNonValide219() {
        citoyen.setNrn("1212721501");
        assertThrows(IllegalArgumentException.class,()->citoyen.getDTO());
    }



    @DisplayName("TC 2.22, Test sur le numéro de téléphone, format des valeurs non valide")
    @Test
    void testInscriptNonValide222() {
        citoyen.setTel("Ab55486-6");
        assertThrows(IllegalArgumentException.class,()->citoyen.getDTO());
    }

    @DisplayName("TC 2.23, Test sur le numéro de GSM, format des  valeurs non valide")
    @Test
    void testInscriptNonValide223() {
        citoyen.setGsm("Ab55482-6");
        assertThrows(IllegalArgumentException.class,()->citoyen.getDTO());
    }

    @DisplayName("TC 2.29, Test sur le mail, valeur absente")
    @Test
    void testInscriptValide229() {
        citoyen.setMail("");
            try {
                cit = citoyen.getDTO();
            }catch (ParseException | IllegalArgumentException  e) {
                e.printStackTrace();
            }
        assertEquals(cit.getMail(),citoyen.getMail());
    }

    @DisplayName("TC 2.24, Test sur le mail, manque le @")
    @Test
    void testInscriptNonValide224() {
        citoyen.setMail("abc-b.com");
        assertThrows(IllegalArgumentException.class,()->citoyen.getDTO());
    }

    @DisplayName("TC 2.25, Test sur le mail, manque le .com ou autre")
    @Test
    void testInscriptNonValide225() {
        citoyen.setMail("abc@b");
        assertThrows(IllegalArgumentException.class,()->citoyen.getDTO());
    }

    @DisplayName("TC 2.26, Test sur la date de naissance, valeur absente")
    @Test
    void testInscriptNonValide226() {
        citoyen.setDateNaissance("");
        assertThrows(IllegalArgumentException.class,()->citoyen.getDTO());
    }

    @DisplayName("TC 2.27, Test sur la date de naissance, format non conforme")
    @Test
    void testInscriptNonValide227() {
        citoyen.setDateNaissance("32 13 AnIX");
        assertThrows(IllegalArgumentException.class,()->citoyen.getDTO());
    }

    @DisplayName("TC 2.28, Test sur la date de naissance, date invalide")
    @Test
    void testInscriptNonValide228() {
        citoyen.setDateNaissance("01/01/2025");
        assertThrows(IllegalArgumentException.class,()->citoyen.getDTO());
    }

    @DisplayName("TC 2.30, Test sur le numéro de téléphone, valeur absente")
    @Test
    void testInscriptValide230() {
        citoyen.setTel("");
        try {
            cit = citoyen.getDTO();
        }catch (ParseException | IllegalArgumentException  e) {
            e.printStackTrace();
        }
        assertEquals(cit.getTel(),citoyen.getTel());
    }

    @DisplayName("TC 2.31, Test sur le numéro de Gsm, valeur absente")
    @Test
    void testInscriptValide231() {
        citoyen.setGsm("");
        try {
            cit = citoyen.getDTO();
        }catch (ParseException | IllegalArgumentException  e) {
            e.printStackTrace();
        }
        assertEquals(cit.getGsm(),citoyen.getGsm());
    }

    @DisplayName("TC 2.32, Test sur le téléphone, valeur valide")
    @Test
    void testInscriptValide232() {
        citoyen.setTel("081/010155");
        try {
            cit = citoyen.getDTO();
        }catch (ParseException | IllegalArgumentException  e) {
            e.printStackTrace();
        }
        assertEquals(cit.getNrn(),citoyen.getNrn());
    }

    @DisplayName("TC 2.33, Test sur le GSM, valeur valide")
    @Test
    void testInscriptValide233() {
        citoyen.setGsm("0495/010155");
        try {
            cit = citoyen.getDTO();
        }catch (ParseException | IllegalArgumentException  e) {
            e.printStackTrace();
        }
        assertEquals(cit.getNrn(),citoyen.getNrn());
    }

    @DisplayName("TC 2.34, Test sur le NRN, date de naissance avant le 01 01 2000")
    @Test
    void testInscriptValide234() {
        citoyen.setNrn("81030133301");
        citoyen.setDateNaissance("01/03/1981");
        try {
            cit = citoyen.getDTO();
        }catch (ParseException | IllegalArgumentException  e) {
            e.printStackTrace();
        }
        assertEquals(cit.getNrn(),citoyen.getNrn());
    }
}