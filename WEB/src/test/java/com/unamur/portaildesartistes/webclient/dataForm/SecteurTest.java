package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.SecteurDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class SecteurTest {

    private Secteur sect;
    private SecteurDTO secteur;

    @BeforeEach
    void setUp() {
        sect= new Secteur();
        secteur=new SecteurDTO();
        sect.setNomSecteur("Art plastique");
        sect.setId("a0d85124-9963-4b6e-b621-5b5d97636612");
    }

    @AfterEach
    void tearDown() {
        sect=null;
        secteur=null;
    }

    @DisplayName("TC, Test d'ajout d'un secteur, avec des données valides")
    @Test
    void testAjoutSecteurValide(){
        try {
            secteur = sect.getDTO();
        }catch (ParseException | IllegalArgumentException  e) {
            e.printStackTrace();
        }

        assertAll(
                ()->assertEquals(secteur.getId(),sect.convertUUID(sect.getId())),
                ()->assertEquals(secteur.getNomSecteur(),sect.getNomSecteur())
        );
    }

    @DisplayName("TC, Test d'ajout d'un secteur, ID vide")
    @Test
    void testAjoutSecteurNonValide1() {
        sect.setId("");
        assertThrows(IllegalArgumentException.class,()->sect.getDTO());
    }

    @DisplayName("TC, Test d'ajout d'un secteur, Nom de secteur vide")
    @Test
    void testAjoutSecteurNonValide2() {
        sect.setNomSecteur("");
        assertThrows(IllegalArgumentException.class,()->sect.getDTO());
    }

}