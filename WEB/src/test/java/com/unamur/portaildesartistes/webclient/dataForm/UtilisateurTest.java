package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class UtilisateurTest {

    private Utilisateur user;
    private UtilisateurDTO usr;

    @BeforeEach
    void setUp() {
    user=new Utilisateur();
    usr=new UtilisateurDTO();
    user.setUsername("teSt2");
    user.setPassword("i5Ts");
    }

    @AfterEach
    void tearDown() {
        user=null;
        usr=null;
    }


    @DisplayName("TC 2.1, Test d’inscription avec des données valides")
    @Test
    void testInscriptValide21() {
        try {
            usr = user.getDTO();
        }catch (IllegalArgumentException  e) {
            e.printStackTrace();
        }

        assertAll(
                ()->assertEquals(usr.getUsername(),user.getUsername()),
                ()->assertEquals(usr.getPassword(),user.getPassword()));
    }

    @DisplayName("TC 2.2, Test d’inscription avec un mots de passe existant")
    @Test
    void testInscriptValide22() {
        user.setPassword("i234");
        try {
            usr = user.getDTO();
        }catch (IllegalArgumentException  e) {
            e.printStackTrace();
        }

        assertEquals(usr.getPassword(),user.getPassword());
    }

    @DisplayName("TC 2.3, Test d’inscription avec un identifiant existant")
    @Test
    void testInscriptNonValide23() {
        user.setUsername("nico");
        assertThrows(IllegalArgumentException.class,()->user.getDTO());
    }

    @DisplayName("TC 2.4, Test d’inscription sans identifiant")
    @Test
    void testInscriptNonValide24() {
        user.setUsername("");
        assertThrows(IllegalArgumentException.class,()->user.getDTO());
    }

    @DisplayName("TC 2.5, Test d’inscription avec un identifiant non conforme")
    @Test
    void testInscriptNonValide25() {
        user.setUsername("T");
        assertThrows(IllegalArgumentException.class,()->user.getDTO());
    }

    @DisplayName("TC 2.6, Test d’inscription sans mots de passe")
    @Test
    void testInscriptNonValide26() {
        user.setPassword("");
        assertThrows(IllegalArgumentException.class,()->user.getDTO());
    }

    @DisplayName("TC 2.7, Test d’inscription avec un mots de passe trop long")
    @Test
    void testInscriptNonValide27() {
        user.setPassword("1235476800ss0");
        assertThrows(IllegalArgumentException.class,()->user.getDTO());
    }

    @DisplayName("TC 2.8, Test d’inscription avec un mots de passe trop faible")
    @Test
    void testInscriptNonValide28() {
        user.setPassword("0000");
        assertThrows(IllegalArgumentException.class,()->user.getDTO());
    }

    @DisplayName("TC 2.9, Test d’inscription avec un mots de passe trop faible")
    @Test
    void testInscriptNonValide29() {
        user.setPassword("1234");
        assertThrows(IllegalArgumentException.class,()->user.getDTO());
    }


}