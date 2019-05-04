package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Utilisateur;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginControlerTest {

    private LoginControler connect;
    private Utilisateur user;
    private UtilisateurDTO userDTO;

    @BeforeEach
    void setUp() {
        connect=new LoginControler();
        user=new Utilisateur();
        user.setUsername("test1");
        user.setPassword("i234");
    }

    @DisplayName("TC 1.1, Test de connexion avec des données valides")
    @Test
    void testConnectValide11() {

        assertEquals(true,connect.ValideConnect(user));
    }

    @DisplayName("TC 1.2, Test de connexion avec un identifiant non valide")
    @Test
    void testConnectNonValide12() {
        user.setUsername("test");
        assertThrows(IllegalArgumentException.class,()->connect.ValideConnect(user));
    }

    @DisplayName("TC 1.3, Test de connexion avec un identifiant non valide")
    @Test
    void testConnectNonValide13() {
        user.setUsername("test12");
        assertThrows(IllegalArgumentException.class,()->connect.ValideConnect(user));
    }

    @DisplayName("TC 1.4, Test de connexion avec un identifiant non valide")
    @Test
    void testConnectNonValide14() {
        user.setUsername("Test1");
        assertThrows(IllegalArgumentException.class,()->connect.ValideConnect(user));
    }

    @DisplayName("TC 1.5, Test de connexion avec un mot de passe non valide")
    @Test
    void testConnectNonValide15() {
        user.setPassword("1234");
        assertThrows(IllegalArgumentException.class,()->connect.ValideConnect(user));
    }

    @DisplayName("TC 1.6, Test de connexion avec un mot de passe non valide")
    @Test
    void testConnectNonValide16() {
        user.setPassword("I234");
        assertThrows(IllegalArgumentException.class,()->connect.ValideConnect(user));
    }

    @DisplayName("TC 1.7, Test de connexion avec un mot de passe non valide")
    @Test
    void testConnectNonValide17() {
        user.setPassword("234");
        assertThrows(IllegalArgumentException.class,()->connect.ValideConnect(user));
    }

    @DisplayName("TC 1.8, Test de connexion avec un mot de passe non valide")
    @Test
    void testConnectNonValide18() {
        user.setPassword("i2345678");
        assertThrows(IllegalArgumentException.class,()->connect.ValideConnect(user));
    }

    @DisplayName("TC 1.9, Test de connexion sans un mot de passe")
    @Test
    void testConnectNonValide19() {
        user.setPassword("");
        assertThrows(IllegalArgumentException.class,()->connect.ValideConnect(user));
    }

    @DisplayName("TC 1.10, Test de connexion sans identifiant")
    @Test
    void testConnectNonValide110() {
        user.setUsername("");
        user.setPassword("");
        assertThrows(IllegalArgumentException.class,()->connect.ValideConnect(user));
    }


    @AfterEach
    void tearDown() {
        user=null;
        connect=null;
    }
}