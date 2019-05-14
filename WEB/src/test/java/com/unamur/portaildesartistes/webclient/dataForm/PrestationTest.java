package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.PrestationDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import static org.junit.jupiter.api.Assertions.*;

class PrestationTest {

    private Prestation prestation;

    @BeforeEach
    void setUp() {
        prestation = new Prestation();
        prestation.setDatePrest(prestation.convertDate("01/06/2019 20:03:05"));
        prestation.setDuree(2);
        prestation.setMontant(100.36);
        prestation.setEtat("Initiee");
        prestation.setCommanditaireId(prestation.convertUUID("98a95e7d-8231-4115-8ed9-612de5590d85"));
        prestation.setDocArtisteId(prestation.convertUUID("98a95e7d-8231-4115-8ed9-612de5590d86"));
        prestation.setActiviteId(prestation.convertUUID("98a95e7d-8231-4115-8ed9-612de5590d87"));
        prestation.setSeDerouleId(prestation.convertUUID("98a95e7d-8231-4115-8ed9-612de5590d88"));
    }

    @AfterEach
    void tearDown() {
        prestation = null;
    }

    @DisplayName("TC 6.1, Test ajout prestation avec valeurs valides")
    @Test
    void testAjoutPrestationValide() {

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
                    ()->assertEquals(prestDTO.getDuree(),  prestation.getDuree()),
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
            assertThrows(IllegalArgumentException.class, ()-> prestDTO.getDatePrest(), "Montant null devrait générer une IllegalArgumentException");
        } catch (ParseException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("TC 6.3, Test ajout prestation avec montant manquant")
    @Test
    void testPrestationMontantManquant() {

        prestation.setMontant(0.0);

        try {
            final PrestationDTO prestDTO = prestation.getDTO();
            assertThrows(IllegalArgumentException.class, ()-> prestDTO.getMontant(), "Montant null devrait générer une IllegalArgumentException");
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
            assertThrows(IllegalArgumentException.class, ()-> prestDTO.getActiviteId(), "Activité null devrait générer une IllegalArgumentException");
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
            assertThrows(IllegalArgumentException.class, ()-> prestDTO.getCommanditaireId(), "Commanditaire null devrait générer une IllegalArgumentException");
        } catch (ParseException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


    @DisplayName("TC 6.7, Test ajout prestation avec durée manquante")
    @Test
    void testPrestationDureeManquante() {

        prestation.setDuree(0);

        try {
            final PrestationDTO prestDTO = prestation.getDTO();
            assertThrows(IllegalArgumentException.class, ()-> prestDTO.getDuree(), "Durée null devrait générer une IllegalArgumentException");
        } catch (ParseException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("TC 6.9, Test ajout prestation avec montant > 128.93€")
    @Test
    void testPrestationSupMontantMax() {

        prestation.setMontant(128.94);
        prestation.setDuree(1);
        assertThrows(IllegalArgumentException.class, ()-> prestation.getDTO(), "Montant > 128.93€ devrait générer une IllegalArgumentException");

        prestation.setMontant(644.66);
        prestation.setDuree(5);
        assertThrows(IllegalArgumentException.class, ()-> prestation.getDTO(), "Montant > 128.93€ * durée (nbr jours) devrait générer une IllegalArgumentException");
    }

    @DisplayName("TC 6.11, Test ajout prestation avec durée > 7 jours")
    @Test
    void testPrestationSupDureeMax() {

        prestation.setDuree(8);
        assertThrows(IllegalArgumentException.class, ()-> prestation.getDTO(), "Durée > 7 jours devrait générer une IllegalArgumentException");

    }

    @DisplayName("TC 6.13, Test ajout prestation avec format date non valide")
    @Test
    void testPrestationDateInvalide() {


        assertThrows(IllegalArgumentException.class, ()-> prestation.setDatePrest(prestation.convertDate("28.02.2019 20:30:00")), "Format date invalide (dd.mm.yyyy) devrait générer une IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, ()-> prestation.setDatePrest(prestation.convertDate("29/02/2019 20:30:00")), "Date invalide (29/02 d'une année non bissextile) devrait générer une IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, ()-> prestation.setDatePrest(prestation.convertDate("31/04/2019 20:30:00")), "Date invalide (jours inexistant) devrait générer une IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, ()-> prestation.setDatePrest(prestation.convertDate("28/13/2019 20:30:00")), "Date invalide (mois inexistant) devrait générer une IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, ()-> prestation.setDatePrest(prestation.convertDate("28/02/0000 20:30:00")), "Date invalide (année inexistante) devrait générer une IllegalArgumentException");

        //à corriger pour les heurers
        /*
        assertThrows(IllegalArgumentException.class, ()-> prestation.setDatePrest(prestation.convertDate("28/02/2019 25:30:00")), "Format date invalide devrait générer une IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, ()-> prestation.setDatePrest(prestation.convertDate("28/02/2019 20:60:00")), "Format date invalide devrait générer une IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, ()-> prestation.setDatePrest(prestation.convertDate("28/02/2019 20:30:60")), "Format date invalide devrait générer une IllegalArgumentException");
*/
    }

    //test ok pour le format car seul les Integers et les Doubles sont acceptés
/*
    @DisplayName("TC 6.14, Test ajout prestation avec format durée non valide")
    @Test
    void testPrestationDureeInvalide() {

        prestation.setDuree("2a5A");
        assertThrows(IllegalArgumentException.class, ()-> prestation.getDTO(), "Format durée invalide devrait générer une IllegalArgumentException");
    }


    @DisplayName("TC 6.15, Test ajout prestation avec format montant non valide")
    @Test
    void testPrestationMontantInvalide() {

        prestation.setMontant(6aD6);
        assertThrows(IllegalArgumentException.class, ()-> prestation.getDTO(), "Format montant invalide devrait générer une IllegalArgumentException");
    }*/


}