package com.unamur.portaildesartistes.webclient.dataForm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireTest {

    private Gestionnaire gestionnaire;

    @BeforeEach
    void setUp() {
        gestionnaire=new Gestionnaire();
        gestionnaire.setCitoyenId("98a95e7d-8231-4115-8ed9-612de5590d88");
        gestionnaire.setTravailleId("75e10d8b-a716-4d00-b131-79ccf90923d1");
        gestionnaire.setMatricule("123456");
        gestionnaire.setBureau("01");
    }

    @AfterEach
    void tearDown() {
        gestionnaire=null;
    }

    @DisplayName("TC, Test d'ajout d'un gestionnaire, avec des données valides")
    @Test
    void testAjoutGestionnaireValide(){
        UUID uuidCitoyen = UUID.fromString("98a95e7d-8231-4115-8ed9-612de5590d88");
        UUID uuidTravailleur = UUID.fromString("75e10d8b-a716-4d00-b131-79ccf90923d1");
        assertEquals(uuidTravailleur,gestionnaire.getTravailleId());
        assertEquals(uuidCitoyen,gestionnaire.getCitoyenId());
        assertAll(
                ()->assertEquals("123456",gestionnaire.getMatricule()),
                ()->assertEquals("01",gestionnaire.getBureau())
        );
    }

    @DisplayName("TC, Test d'ajout d'un gestionnaire, Citoyen ID vide")
    @Test
    void testAjoutGestionnaireNonValide1() {
        gestionnaire.setCitoyenId("");
        assertThrows(IllegalArgumentException.class,()->gestionnaire.getCitoyenId());
    }

    @DisplayName("TC, Test d'ajout d'un gestionnaire, Travailleur ID vide")
    @Test
    void testAjoutGestionnaireNonValide2() {
        gestionnaire.setTravailleId("");
        assertThrows(IllegalArgumentException.class,()->gestionnaire.getTravailleId());
    }

    @DisplayName("TC, Test d'ajout d'un gestionnaire, matricule vide")
    @Test
    void testAjoutGestionnaireNonValide3() {
        gestionnaire.setMatricule("");
        assertThrows(IllegalArgumentException.class,()->gestionnaire.getMatricule());
    }

    @DisplayName("TC, Test d'ajout d'un gestionnaire, bureau vide")
    @Test
    void testAjoutGestionnaireNonValide4() {
        gestionnaire.setBureau("");
        assertThrows(IllegalArgumentException.class,()->gestionnaire.getBureau());
    }
}