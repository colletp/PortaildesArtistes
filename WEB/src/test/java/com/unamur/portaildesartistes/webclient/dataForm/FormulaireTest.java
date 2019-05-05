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

class FormulaireTest {

    private Formulaire formulaire;

    @BeforeEach
    void setUp() {
        formulaire=new Formulaire();
        formulaire.setCitoyenId("98a95e7d-8231-4115-8ed9-612de5590d88");
        formulaire.setDateDemande("12/04/2019");
        formulaire.setCursusAc(null);
        formulaire.setExpPro(null);
        formulaire.setRessources(null);
        formulaire.setLangue("FR");
        formulaire.setCarte("1");
        formulaire.setVisa(null);
    }

    @AfterEach
    void tearDown() {
        formulaire=null;
    }

    @DisplayName("TC, Test de formulaire carte artiste avec des données valides")
    @Test
    void testFormulaireCarteValide(){
        UUID uuid = UUID.fromString("98a95e7d-8231-4115-8ed9-612de5590d88");
        Date dateDemande=new Date();
        String date = "12/04/2019";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try{
            dateDemande = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(dateDemande,formulaire.getDateDemande());
        assertEquals(uuid,formulaire.getCitoyenId());
        assertAll(
                ()->assertEquals(null,formulaire.getCursusAc()),
                ()->assertEquals(null,formulaire.getExpPro()),
                ()->assertEquals(null,formulaire.getRessources()),
                ()->assertEquals("FR",formulaire.getLangue()),
                ()->assertEquals("1",formulaire.getCarte()),
                ()->assertEquals(null,formulaire.getVisa())
        );
    }

    @DisplayName("TC, Test de formulaire visa artiste avec des données valides")
    @Test
    void testFormulaireVisaValide() {
        formulaire.setVisa("1");
        formulaire.setCarte(null);
        formulaire.setLangue("EN");
        UUID uuid = UUID.fromString("98a95e7d-8231-4115-8ed9-612de5590d88");
        Date dateDemande = new Date();
        String date = "12/04/2019";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateDemande = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(dateDemande, formulaire.getDateDemande());
        assertEquals(uuid, formulaire.getCitoyenId());
        assertAll(
                () -> assertEquals(null, formulaire.getCursusAc()),
                () -> assertEquals(null, formulaire.getExpPro()),
                () -> assertEquals(null, formulaire.getRessources()),
                () -> assertEquals("EN", formulaire.getLangue()),
                () -> assertEquals(null, formulaire.getCarte()),
                () -> assertEquals("1", formulaire.getVisa())
        );
    }

    //Voir si nécessaire?
    @DisplayName("TC, Test de formulaire carte artiste et visa artiste true")
    @Test
    void testFormulaireNonValide1() {
        formulaire.setVisa("1");
        formulaire.setCarte("1");
        assertEquals(null, formulaire.getCarte());
        assertEquals("1", formulaire.getVisa());
    }

    //Voir si nécessaire?
    @DisplayName("TC, Test de formulaire carte artiste et visa artiste false")
    @Test
    void testFormulaireNonValide2() {
        formulaire.setVisa(null);
        formulaire.setCarte(null);
        assertEquals(false, formulaire.getCarte());
        assertEquals(true, formulaire.getVisa());
    }

    @DisplayName("TC, Test de formulaire artiste, langue invalide")
    @Test
    void testFormulaireNonValide3() {
        formulaire.setLangue("");
        assertThrows(IllegalArgumentException.class,()->formulaire.getLangue());
    }

    @DisplayName("TC, Test de formulaire artiste, date de demande vide")
    @Test
    void testFormulaireNonValide4() {
        formulaire.setDateDemande("");
        assertThrows(IllegalArgumentException.class,()->formulaire.getDateDemande());
    }

    @DisplayName("TC, Test de formulaire artiste, Citoyen ID vide")
    @Test
    void testFormulaireNonValide5() {
        formulaire.setCitoyenId("");
        assertThrows(IllegalArgumentException.class,()->formulaire.getCitoyenId());
    }
}