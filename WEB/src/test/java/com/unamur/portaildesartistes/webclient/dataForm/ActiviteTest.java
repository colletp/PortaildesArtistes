package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ActiviteTest {

    private Activite activite;
    private ActiviteDTO act;

    @BeforeEach
    void setUp() {
        activite=new Activite();
        act = new ActiviteDTO();
        activite.setNomActivite("Peinture à l'huile");
        activite.setSecteurId("a0d85124-9963-4b6e-b621-5b5d97636612");
        activite.setDescription("une série de peinture");
    }

    @AfterEach
    void tearDown() {
        activite=null;
        act=null;
    }

    @DisplayName("TC 3.0.6, Test ajout avec des données valides")
    @Test
    void testAjoutActiviteValide(){
        try {
            act = activite.getDTO();
        }catch (ParseException | IllegalArgumentException  e) {
                e.printStackTrace();
        }
        assertAll(
                ()->assertEquals(act.getNomActivite(),activite.getNomActivite()),
                ()->assertEquals(act.getSecteurId(),activite.convertUUID(activite.getSecteurId())),
                ()->assertEquals(act.getDescription(),activite.getDescription())
        );
    }

    @DisplayName(("TC 3.0.5, Test ajout avec un secteur mais sans activité"))
    @Test
    void testAjoutActiviteNonValide305(){
        activite.setNomActivite("");
        assertThrows(IllegalArgumentException.class,()->activite.getDTO());
    }

    @DisplayName(("TC 3.0.4, Test ajout sans secteur"))
    @Test
    void testAjoutActiviteNonValide304(){
        activite.setSecteurId("");
        assertThrows(IllegalArgumentException.class,()->activite.getDTO());
    }

    @DisplayName(("TC , Test ajout sans description"))
    @Test
    void testAjoutActiviteValide3(){
        activite.setDescription("");
        try {
            act = activite.getDTO();
        }catch (ParseException | IllegalArgumentException  e) {
            e.printStackTrace();
        }
        assertAll(
                ()->assertEquals(act.getNomActivite(),activite.getNomActivite()),
                ()->assertEquals(act.getSecteurId(),activite.convertUUID(activite.getSecteurId())),
                ()->assertEquals(act.getDescription(),activite.getDescription())
        );
    }





}