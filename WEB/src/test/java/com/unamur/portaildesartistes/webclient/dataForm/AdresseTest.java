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

    @BeforeEach
    void setUp() {
        adresse=new Adresse();
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
        assertAll(
                ()->assertEquals("Petite rue d'en face",adresse.getRue()),
                ()->assertEquals("6",adresse.getNumero()),
                ()->assertEquals("Namur",adresse.getVille()));
    }

    @DisplayName("TC 2.12, Test sur la rue, valeur absente")
    @Test
    void testInscriptNonValide212() {
       adresse.setRue("");
       assertThrows(IllegalArgumentException.class,()->adresse.getRue());
    }

    @DisplayName("TC 2.13 Test sur le numéro de rue, valeur absente")
    @Test
    void testInscriptNonValide213() {
        adresse.setNumero("");
        assertThrows(IllegalArgumentException.class,()->adresse.getNumero());
    }

    @DisplayName("TC 2.14, Test sur la localité, valeur absente")
    @Test
    void testInscriptNonValide214() {
        adresse.setVille("");
        assertThrows(IllegalArgumentException.class,()->adresse.getVille());
    }
/*  Annuler car possible 175B
    @DisplayName("TC 2.20, Test sur le numéro de rue, valeur non valide")
    @Test
    void testInscriptNonValide220() {
        adresse.setNumero("Ab-c");
        assertThrows(IllegalArgumentException.class,()->adresse.getNumero());
    }*/

    @DisplayName("TC 2.21, Test sur la localité, valeur non valide")
    @Test
    void testInscriptNonValide221() {
        adresse.setVille("123");
        assertThrows(IllegalArgumentException.class,()->adresse.getVille());
    }

}