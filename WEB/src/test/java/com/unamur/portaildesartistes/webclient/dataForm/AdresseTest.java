package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.AdresseDTO;
import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class AdresseTest {

    private Adresse adresse;
    private AdresseDTO adr;

    @BeforeEach
    void setUp() {
        adresse=new Adresse();
        adr=new AdresseDTO();
        adresse.setRue("Petite rue d'en face");
        adresse.setNumero("6");
        adresse.setVille("Namur");
    }

    @AfterEach
    void tearDown() {
        adresse=null;
    }


    @DisplayName("TC 2.1, Test d’inscription avec des données valides")
    @Test
    void testInscriptValide21() {
        try {
            adr = adresse.getDTO();
        }catch (IllegalArgumentException  e) {
            e.printStackTrace();
        }

        assertAll(
                ()->assertEquals(adr.getRue(),adresse.getRue()),
                ()->assertEquals(adr.getNumero(),adresse.getNumero()),
                ()->assertEquals(adr.getVille(),adresse.getVille()));
    }

    @DisplayName("TC 2.12, Test sur la rue, valeur absente")
    @Test
    void testInscriptNonValide212() {
       adresse.setRue("");
       assertThrows(IllegalArgumentException.class,()->adresse.getDTO());
    }

    @DisplayName("TC 2.13 Test sur le numéro de rue, valeur absente")
    @Test
    void testInscriptNonValide213() {
        adresse.setNumero("");
        assertThrows(IllegalArgumentException.class,()->adresse.getDTO());
    }

    @DisplayName("TC 2.14, Test sur la localité, valeur absente")
    @Test
    void testInscriptNonValide214() {
        adresse.setVille("");
        assertThrows(IllegalArgumentException.class,()->adresse.getDTO());
    }

    @DisplayName("TC 2.20, Test sur le numéro de rue, valeur non valide")
    @Test
    void testInscriptNonValide220() {
        adresse.setNumero("Ab-c");
        assertThrows(IllegalArgumentException.class,()->adresse.getDTO());
    }

    @DisplayName("TC 2.21, Test sur la localité, valeur non valide")
    @Test
    void testInscriptNonValide221() {
        adresse.setVille("1234");
        assertThrows(IllegalArgumentException.class,()->adresse.getDTO());
    }

    @DisplayName("TC 2.35, Test sur le numéro de rue, valeur valide")
    @Test
    void testInscriptValide235() {
        adresse.setNumero("123B");
        try {
            adr = adresse.getDTO();
        }catch (IllegalArgumentException  e) {
            e.printStackTrace();
        }
        assertEquals(adr.getNumero(),adresse.getNumero());
    }

}