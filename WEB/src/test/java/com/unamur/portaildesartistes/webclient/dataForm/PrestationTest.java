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

class PrestationTest {

    private Prestation prestation;

    @BeforeEach
    void setUp() {
        prestation = new Prestation();
        prestation.setDatePrest("01/06/2019");
        prestation.setDuree("2");
        prestation.setMontant("100.36");
        prestation.setEtat("Initiee");
        // Valeurs UUID à modifier
        prestation.setCommanditaireId("98a95e7d-8231-4115-8ed9-612de5590d85");
        prestation.setDocArtisteId("98a95e7d-8231-4115-8ed9-612de5590d86");
        prestation.setActiviteId("98a95e7d-8231-4115-8ed9-612de5590d87");
        prestation.setSeDerouleId("98a95e7d-8231-4115-8ed9-612de5590d88");
    }

    @AfterEach
    void tearDown() {
        prestation=null;
    }

    @DisplayName("TC 6.1, Test ajout prestation avec valeurs valides")
    @Test
    void testAjoutPrestationValide() throws ParseException {
        // Modifier les valeurs UUID
        UUID uuidCommanditaire = UUID.fromString("98a95e7d-8231-4115-8ed9-612de5590d85");
        UUID uuidDocArtiste    = UUID.fromString("98a95e7d-8231-4115-8ed9-612de5590d86");
        UUID uuidActivite      = UUID.fromString("98a95e7d-8231-4115-8ed9-612de5590d87");
        UUID uuidSeDeroule     = UUID.fromString("98a95e7d-8231-4115-8ed9-612de5590d88");

        Date datePrestation = new Date();
        String date = "01/06/2019";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try{
            datePrestation = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertEquals(    datePrestation, simpleDateFormat.parse(prestation.getDatePrest()));
        assertEquals("Initiee", prestation.getEtat());

        assertAll(
                ()->assertEquals(uuidCommanditaire,UUID.fromString(prestation.getCommanditaireId())),
                ()->assertEquals(uuidDocArtiste,UUID.fromString(prestation.getDocArtisteId())),
                ()->assertEquals(uuidActivite,UUID.fromString(prestation.getActiviteId())),
                ()->assertEquals(uuidSeDeroule,UUID.fromString(prestation.getSeDerouleId()))
        );

        assertAll(
                ()->assertEquals(2,Integer.valueOf(prestation.getDuree())),
                ()->assertEquals(100.36,Double.valueOf(prestation.getMontant()))
        );
    }


}