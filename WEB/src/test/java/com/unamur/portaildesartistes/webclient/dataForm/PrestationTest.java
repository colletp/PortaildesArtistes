package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.PrestationDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;


import static org.junit.jupiter.api.Assertions.*;

class PrestationTest {

    private Prestation prestation;

    @BeforeEach
    void setUp() {
        prestation = new Prestation();
        prestation.setDatePrest("01/06/2019 ");
        prestation.setDuree("2");
        prestation.setMontant("100.36");
        prestation.setEtat("Initiee");
        prestation.setCommanditaireId("98a95e7d-8231-4115-8ed9-612de5590d85");
        prestation.setDocArtisteId("98a95e7d-8231-4115-8ed9-612de5590d86");
        prestation.setActiviteId("98a95e7d-8231-4115-8ed9-612de5590d87");
        prestation.setSeDerouleId("98a95e7d-8231-4115-8ed9-612de5590d88");
    }

    @AfterEach
    void tearDown() {
        prestation = null;
    }

    @DisplayName("TC 6.1, Test ajout prestation avec valeurs valides")
    @Test
    void testAjoutPrestationValide() { //throws ParseException {

        try{
            final PrestationDTO prestDTO = prestation.getDTO();
            // pas sûr que ce soit la bonne manière de faire pour convertir la date en string
            assertEquals(new SimpleDateFormat("dd/MM/yyyy").format(prestDTO.getDatePrest()), prestation.getDatePrest() );
            assertEquals(prestDTO.getEtat(), prestation.getEtat());

            assertAll(
                    ()->assertEquals(prestDTO.getCommanditaireId().toString(),prestation.getCommanditaireId()),
                    ()->assertEquals(prestDTO.getDocArtisteId().toString(),   prestation.getDocArtisteId()),
                    ()->assertEquals(prestDTO.getActiviteId().toString(),     prestation.getActiviteId()),
                    ()->assertEquals(prestDTO.getSeDerouleId().toString(),    prestation.getSeDerouleId())
            );

            assertAll(
                    ()->assertEquals(prestDTO.getDuree().toString(),  prestation.getDuree()),
                    ()->assertEquals(prestDTO.getMontant().toString(),prestation.getMontant())
            );

        // Vérifier catch
        } catch (ParseException | IllegalArgumentException  e) {
            e.printStackTrace();
        }

    }

}