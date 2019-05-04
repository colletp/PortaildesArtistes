package com.unamur.portaildesartistes.webclient.dataForm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ActiviteTest {

    private Activite activite;

    @BeforeEach
    void setUp() {
        activite=new Activite();
        activite.setNomActivite("Peinture à l'huile");
        activite.setSecteurId("a0d85124-9963-4b6e-b621-5b5d97636612");
    }

    @AfterEach
    void tearDown() {
        activite=null;
    }

    @DisplayName("TC 3.0.6, Test ajout avec des données valides")
    @Test
    void testAjoutActiviteValide(){
        assertEquals("Peinture à l'huile",activite.getNomActivite());
        UUID uuid = UUID.fromString("a0d85124-9963-4b6e-b621-5b5d97636612");
        assertEquals(uuid,activite.getSecteurId());
    }

    @DisplayName(("TC 3.0.5, Test ajout avec un secteur mais sans activité"))
    @Test
    void testAjoutActiviteNonValide305(){
        activite.setNomActivite("");
        assertThrows(IllegalArgumentException.class,()->activite.getNomActivite());
    }

    @DisplayName(("TC 3.0.4, Test ajout sans secteur"))
    @Test
    void testAjoutActiviteNonValide304(){
        activite.setSecteurId("");
        assertThrows(IllegalArgumentException.class,()->activite.getSecteurId());
    }





}