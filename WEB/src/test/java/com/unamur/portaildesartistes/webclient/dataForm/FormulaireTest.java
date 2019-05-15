package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.FormulaireDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FormulaireTest {

    private Formulaire formulaire;
    private FormulaireDTO form;

    @BeforeEach
    void setUp() {
        formulaire=new Formulaire();
        form=new FormulaireDTO();
        formulaire.setCitoyenId("98a95e7d-8231-4115-8ed9-612de5590d88");
        formulaire.setDateDemande("12/04/2019");
        formulaire.setCursusAc(null);
        formulaire.setExpPro(null);
        formulaire.setRessources(null);
        formulaire.setLangue("FR");
        formulaire.setCarte("1");
        formulaire.setVisa("0");
        List <String> tab=new ArrayList<>();
        formulaire.setActivitesId(tab);
    }

    @AfterEach
    void tearDown() {
        formulaire=null;
        form=null;
    }

    @DisplayName("TC, Test de formulaire carte artiste avec des données valides")
    @Test
    void testFormulaireCarteValide(){
        try {
            form = formulaire.getDTO();
        }catch (IllegalArgumentException  e) {
            e.printStackTrace();
        }

        assertAll(
                ()->assertEquals(form.getCursusAc(),formulaire.getCursusAc()),
                ()->assertEquals(form.getExpPro(),formulaire.getExpPro()),
                ()->assertEquals(form.getRessources(),formulaire.getRessources()),
                ()->assertEquals(form.getLangue(),formulaire.getLangue()),
                ()->assertEquals(form.getCarte(),formulaire.getCarte()),
                ()->assertEquals(form.getVisa(),formulaire.getVisa()),
                ()->assertEquals(form.getDateDemande(),formulaire.convertDate(formulaire.getDateDemande())),
                ()->assertEquals(form.getCitoyenId(),formulaire.convertUUID(formulaire.getCitoyenId())),
                ()->assertEquals(form.getActivitesId(),formulaire.getActivitesId())
        );
    }

    @DisplayName("TC, Test de formulaire visa artiste avec des données valides")
    @Test
    void testFormulaireVisaValide() {
        formulaire.setVisa("1");
        formulaire.setCarte("0");
        formulaire.setLangue("EN");
        try {
            form = formulaire.getDTO();
        }catch (IllegalArgumentException  e) {
            e.printStackTrace();
        }

        assertAll(
                ()->assertEquals(form.getCursusAc(),formulaire.getCursusAc()),
                ()->assertEquals(form.getExpPro(),formulaire.getExpPro()),
                ()->assertEquals(form.getRessources(),formulaire.getRessources()),
                ()->assertEquals(form.getLangue(),formulaire.getLangue()),
                ()->assertEquals(form.getCarte(),formulaire.getCarte()),
                ()->assertEquals(form.getVisa(),formulaire.getVisa()),
                ()->assertEquals(form.getDateDemande(),formulaire.convertDate(formulaire.getDateDemande())),
                ()->assertEquals(form.getCitoyenId(),formulaire.convertUUID(formulaire.getCitoyenId())),
                ()->assertEquals(form.getActivitesId(),formulaire.getActivitesId())
        );
    }

    @DisplayName("TC, Test de formulaire carte artiste et visa artiste false")
    @Test
    void testFormulaireNonValide2() {
        formulaire.setVisa(null);
        formulaire.setCarte(null);
        assertThrows(IllegalArgumentException.class,()->formulaire.getDTO());
    }

    @DisplayName("TC, Test de formulaire artiste, langue invalide")
    @Test
    void testFormulaireNonValide3() {
        formulaire.setLangue("");
        assertThrows(IllegalArgumentException.class,()->formulaire.getDTO());
    }

    @DisplayName("TC, Test de formulaire artiste, date de demande vide")
    @Test
    void testFormulaireNonValide4() {
        formulaire.setDateDemande("");
        assertThrows(IllegalArgumentException.class,()->formulaire.getDTO());
    }

    @DisplayName("TC, Test de formulaire artiste, Citoyen ID vide")
    @Test
    void testFormulaireNonValide5() {
        formulaire.setCitoyenId("");
        assertThrows(IllegalArgumentException.class,()->formulaire.getDTO());
    }
}