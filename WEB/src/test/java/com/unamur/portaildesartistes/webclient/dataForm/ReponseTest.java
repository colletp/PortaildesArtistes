package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.ReponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class ReponseTest {

    private ReponseDTO reponse;
    private Reponse resp;

    @BeforeEach
    void setUp() {
        reponse=new ReponseDTO();
        resp=new Reponse();
        resp.setTrtId("4bd56ccc-b5de-4322-8b1c-61faac490529");
        resp.setCitoyenId("98a95e7d-8231-4115-8ed9-612de5590d88");
        resp.setDateReponse("12/04/2019");
    }

    @AfterEach
    void tearDown() {
        resp=null;
        reponse=null;
    }

    @DisplayName("TC, Test d'ajout d'une réponse, avec des données valides")
    @Test
    void testAjoutReponseValide(){
        try {
            reponse = resp.getDTO();
        }catch (IllegalArgumentException  e) {
            e.printStackTrace();
        }

        assertAll(
                ()->assertEquals(reponse.getTrtId(),resp.convertUUID(resp.getTrtId())),
                ()->assertEquals(reponse.getCitoyenId(),resp.convertUUID(resp.getCitoyenId())),
                ()->assertEquals(reponse.getDateReponse(), Timestamp.from(resp.convertDate(resp.getDateReponse()).toInstant()))
        );
    }

    @DisplayName("TC, Test d'ajout d'une réponse, Citoyen ID vide")
    @Test
    void testAjoutReponseNonValide1() {
        resp.setCitoyenId("");
        assertThrows(IllegalArgumentException.class,()->resp.getDTO());
    }

    @DisplayName("TC, Test d'ajout d'une réponse, traitement ID vide")
    @Test
    void testAjoutReponseNonValide2() {
        resp.setTrtId("");
        assertThrows(IllegalArgumentException.class,()->resp.getDTO());
    }

    @DisplayName("TC, Test d'ajout d'une réponse, date de réponse vide")
    @Test
    void testAjoutReponseNonValide3() {
        resp.setDateReponse("");
        assertThrows(IllegalArgumentException.class,()->resp.getDTO());
    }
}