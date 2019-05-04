package com.unamur.portaildesartistes.webclient.dataForm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilisateurTest {

    private Utilisateur user;

    @BeforeEach
    void setUp() {
    user=new Utilisateur();
    user.setUsername("teSt2");
    user.setPassword("i5Ts");
    }

    @AfterEach
    void tearDown() {
        user=null;
    }


    @DisplayName("TC 2.1, Test d’inscription avec des données valides")
    @Test
    void testInscriptValide21() {
        assertAll(
                ()->assertEquals("teSt2",user.getUsername()),
                ()->assertEquals("i5Ts",user.getPassword()));
    }

    @DisplayName("TC 2.2, Test d’inscription avec un mots de passe existant")
    @Test
    void testInscriptValide22() {
        user.setPassword("i234");
        assertEquals("i234",user.getPassword());
    }

    @DisplayName("TC 2.3, Test d’inscription avec un identifiant existant")
    @Test
    void testInscriptNonValide23() {
        user.setUsername("nico");
        assertThrows(IllegalArgumentException.class,()->user.getUsername());
    }

    @DisplayName("TC 2.4, Test d’inscription sans identifiant")
    @Test
    void testInscriptNonValide24() {
        user.setUsername("");
        assertThrows(IllegalArgumentException.class,()->user.getUsername());
    }

    @DisplayName("TC 2.5, Test d’inscription avec un identifiant non conforme")
    @Test
    void testInscriptNonValide25() {
        user.setUsername("T");
        assertThrows(IllegalArgumentException.class,()->user.getUsername());
    }

    @DisplayName("TC 2.6, Test d’inscription sans mots de passe")
    @Test
    void testInscriptNonValide26() {
        user.setPassword("");
        assertThrows(IllegalArgumentException.class,()->user.getPassword());
    }

    @DisplayName("TC 2.7, Test d’inscription avec un mots de passe trop long")
    @Test
    void testInscriptNonValide27() {
        user.setPassword("1235476800ss0");
        assertThrows(IllegalArgumentException.class,()->user.getPassword());
    }

    @DisplayName("TC 2.8, Test d’inscription avec un mots de passe trop faible")
    @Test
    void testInscriptNonValide28() {
        user.setPassword("0000");
        assertThrows(IllegalArgumentException.class,()->user.getPassword());
    }

    @DisplayName("TC 2.9, Test d’inscription avec un mots de passe trop faible")
    @Test
    void testInscriptNonValide29() {
        user.setPassword("1234");
        assertThrows(IllegalArgumentException.class,()->user.getPassword());
    }


}