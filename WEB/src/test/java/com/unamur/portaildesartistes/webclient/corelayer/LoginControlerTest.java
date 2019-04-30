package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginControlerTest {

    private LoginControler connect;
    private UtilisateurDTO user;

    @BeforeEach
    void setUp() {
        connect=new LoginControler();
        user=new UtilisateurDTO();
        user.setUsername("test1");
        user.setPassword("i234");
    }

    @DisplayName("Test de connexion avec des données valides, TC 1.1")
    @Test
    void testConnectValide11() {

        assertEquals(true,connect.ValideConnect(user));
    }

    @DisplayName("Test de connexion avec un identifiant non valide, TC 1.2")
    @Test
    void testConnectNonValide12() {
        user.setUsername("test");
        assertThrows(IllegalArgumentException.class,()->connect.ValideConnect(user));
    }

    @DisplayName("Test de connexion avec un identifiant non valide, TC 1.3")
    @Test
    void testConnectNonValide13() {
        user.setUsername("test12");
        assertThrows(IllegalArgumentException.class,()->connect.ValideConnect(user));
    }

    @DisplayName("Test de connexion avec un identifiant non valide, TC 1.4")
    @Test
    void testConnectNonValide14() {
        user.setUsername("Test1");
        assertThrows(IllegalArgumentException.class,()->connect.ValideConnect(user));
    }

    @DisplayName("Test de connexion avec un mot de passe non valide, TC 1.5")
    @Test
    void testConnectNonValide15() {
        user.setPassword("1234");
        assertThrows(IllegalArgumentException.class,()->connect.ValideConnect(user));
    }

    @DisplayName("Test de connexion avec un mot de passe non valide, TC 1.6")
    @Test
    void testConnectNonValide16() {
        user.setPassword("I234");
        assertThrows(IllegalArgumentException.class,()->connect.ValideConnect(user));
    }

    @DisplayName("Test de connexion avec un mot de passe non valide, TC 1.7")
    @Test
    void testConnectNonValide17() {
        user.setPassword("234");
        assertThrows(IllegalArgumentException.class,()->connect.ValideConnect(user));
    }

    @DisplayName("Test de connexion avec un mot de passe non valide, TC 1.8")
    @Test
    void testConnectNonValide18() {
        user.setPassword("i2345678");
        assertThrows(IllegalArgumentException.class,()->connect.ValideConnect(user));
    }

    @DisplayName("Test de connexion sans un mot de passe, TC 1.9")
    @Test
    void testConnectNonValide19() {
        user.setPassword("");
        assertThrows(IllegalArgumentException.class,()->connect.ValideConnect(user));
    }

    @DisplayName("Test de connexion sans identifiant, TC 1.10")
    @Test
    void testConnectNonValide110() {
        user.setUsername("");
        user.setPassword("");
        assertThrows(IllegalArgumentException.class,()->connect.ValideConnect(user));
    }



    @Test
    void handleHttpMediaTypeNotAcceptableException() {
    }

    @Test
    void css() {
    }

    @Test
    void loginView() {
    }

    @Test
    void authenticate() {
    }

    @Test
    void userList() {
    }

    @Test
    void logout() {
    }

    @AfterEach
    void tearDown() {
        user=null;
        connect=null;
    }
}