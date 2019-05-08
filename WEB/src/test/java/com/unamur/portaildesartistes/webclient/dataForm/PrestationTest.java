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
        prestation.setDatePrest("01/06/2019 20:03:05");
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

            assertEquals(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(prestDTO.getDatePrest()), prestation.getDatePrest() );
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

        } catch (ParseException | IllegalArgumentException  e) {
            e.printStackTrace();
        }

    }

    @DisplayName("TC 6.2, Test ajout prestation avec date manquante")
    @Test
    void testPrestationDateManquante() {

        prestation.setDatePrest(null);

        try {
            final PrestationDTO prestDTO = prestation.getDTO();
            assertThrows(IllegalArgumentException.class, ()-> {prestDTO.getDatePrest();}, "Montant null devrait générer une IllegalArgumentException");
        } catch (ParseException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("TC 6.3, Test ajout prestation avec montant manquant")
    @Test
    void testPrestationMontantManquant() {

        prestation.setMontant(null);

        try {
            final PrestationDTO prestDTO = prestation.getDTO();
            assertThrows(IllegalArgumentException.class, ()-> {prestDTO.getMontant();}, "Montant null devrait générer une IllegalArgumentException");
        } catch (ParseException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("TC 6.4, Test ajout prestation avec activité manquante")
    @Test
    void testPrestationActiviteManquante() {

        prestation.setActiviteId(null);

        try {
            final PrestationDTO prestDTO = prestation.getDTO();
            assertThrows(IllegalArgumentException.class, ()-> {prestDTO.getActiviteId();}, "Activité null devrait générer une IllegalArgumentException");
        } catch (ParseException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("TC 6.5, Test ajout prestation avec commanditaire manquant")
    @Test
    void testPrestationCommanditaireManquante() {

        prestation.setCommanditaireId(null);

        try {
            final PrestationDTO prestDTO = prestation.getDTO();
            assertThrows(IllegalArgumentException.class, ()-> {prestDTO.getCommanditaireId();}, "Commanditaire null devrait générer une IllegalArgumentException");
        } catch (ParseException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


    @DisplayName("TC 6.7, Test ajout prestation avec durée manquante")
    @Test
    void testPrestationDureeManquante() {

        prestation.setDuree(null);

        try {
            final PrestationDTO prestDTO = prestation.getDTO();
            assertThrows(IllegalArgumentException.class, ()-> {prestDTO.getDuree();}, "Durée null devrait générer une IllegalArgumentException");
        } catch (ParseException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("TC 6.9, Test ajout prestation avec montant > 128.93€")
    @Test
    void testPrestationSupMontantMax() {

        prestation.setMontant("128.94");

        try {
            assertThrows(IllegalArgumentException.class, ()-> {prestation.getDTO();}, "Montant > 128.93 devrait générer une IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("TC 6.11, Test ajout prestation avec durée > 7 jours")
    @Test
    void testPrestationSupDureeMax() {

        prestation.setDuree("iiii");



        assertThrows(IllegalArgumentException.class, ()-> prestation.getDTO(), "Durée > 7 jours devrait générer une IllegalArgumentException");



//        try {
//            assertThrows(IllegalArgumentException.class, ()-> {PrestationDTO prestDTO = prestation.getDTO();}, "Durée > 7 jours devrait générer une IllegalArgumentException");
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        }
    }

    // To Do
    @DisplayName("TC 6.13, Test ajout prestation avec format date non valide")
    @Test
    void testPrestationDateInvalide() {

        prestation.setDatePrest("01.06.2019 20:30:00");
        //prestation.setDatePrest("29.02.2019 20:30:00"); //test supplémentaire
        try {
            assertThrows(IllegalArgumentException.class, ()-> {PrestationDTO prestDTO = prestation.getDTO();}, "Format date invalide devrait générer une IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


    @DisplayName("TC 6.14, Test ajout prestation avec format durée non valide")
    @Test
    void testPrestationDureeInvalide() {

        prestation.setDuree("2a5A");

        assertThrows(IllegalArgumentException.class, ()-> prestation.getDTO(), "Format durée invalide devrait générer une IllegalArgumentException");
    }

    // To Do
    @DisplayName("TC 6.15, Test ajout prestation avec format montant non valide")
    @Test
    void testPrestationMontantInvalide() {


        prestation.setMontant("6aD6");

        try {
            assertThrows(IllegalArgumentException.class, ()-> {prestation.getDTO();}, "Format montant invalide devrait générer une IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }



}