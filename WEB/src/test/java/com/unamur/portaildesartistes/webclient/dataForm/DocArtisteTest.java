package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.DocArtisteDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DocArtisteTest {

    private DocArtiste doc;
    private DocArtisteDTO docArt;

    @BeforeEach
    void setUp() {
        doc=new DocArtiste();
        docArt=new DocArtisteDTO();
        doc.setCitoyenId("98a95e7d-8231-4115-8ed9-612de5590d88");
        doc.setReponseId("c91dce79-20a4-431f-957f-b6d09b9b876b");
        doc.setDatePeremption("12/04/2019");
        doc.setNomArtiste("Nico");
        doc.setNoDoc("123456");
        doc.setTypeDocArtiste("Carte artiste");
    }

    @AfterEach
    void tearDown() {
        doc=null;
        docArt=null;
    }

    @DisplayName("TC , Test de création Carte Artiste avec valeur valide")
    @Test
    void testCreationCarteArtisteValide() {
        try {
            docArt = doc.getDTO();
        }catch(ParseException e){
            e=new ParseException("Echec getDTO",3);
        }
        assertAll(
                ()->assertEquals(docArt.getDatePeremption(),doc.convertDate(doc.getDatePeremption())),
                ()->assertEquals(docArt.getNomArtiste(),doc.getNomArtiste()),
                ()->assertEquals(docArt.getNoDoc(),doc.getNoDoc()),
                ()->assertEquals(docArt.getTypeDocArtiste(),doc.getTypeDocArtiste()),
                ()->assertEquals(docArt.getCitoyenId(),doc.convertUUID(doc.getCitoyenId())),
                ()->assertEquals(docArt.getReponseId(),doc.convertUUID(doc.getReponseId()))
        );
    }

    @DisplayName("TC , Test de création Visa Artiste avec valeur valide")
    @Test
    void testCreationVisaArtisteValide() {
        doc.setTypeDocArtiste("Visa artiste");
        try {
            docArt = doc.getDTO();
        }catch(ParseException e){
            e=new ParseException("Echec getDTO",3);
        }
        assertAll(
                ()->assertEquals(docArt.getDatePeremption(),doc.convertDate(doc.getDatePeremption())),
                ()->assertEquals(docArt.getNomArtiste(),doc.getNomArtiste()),
                ()->assertEquals(docArt.getNoDoc(),doc.getNoDoc()),
                ()->assertEquals(docArt.getTypeDocArtiste(),doc.getTypeDocArtiste()),
                ()->assertEquals(docArt.getCitoyenId(),doc.convertUUID(doc.getCitoyenId())),
                ()->assertEquals(docArt.getReponseId(),doc.convertUUID(doc.getReponseId()))
        );
    }

    @DisplayName("TC , Test de création Doc Artiste avec un nom d'artiste absent")
    @Test
    void testCreationDocArtisteValide() {
        doc.setNomArtiste("");
        try {
            docArt = doc.getDTO();
        }catch(ParseException e){
            e=new ParseException("Echec getDTO",3);
        }
        assertAll(
                ()->assertEquals(docArt.getDatePeremption(),doc.convertDate(doc.getDatePeremption())),
                ()->assertEquals(docArt.getNomArtiste(),doc.getNomArtiste()),
                ()->assertEquals(docArt.getNoDoc(),doc.getNoDoc()),
                ()->assertEquals(docArt.getTypeDocArtiste(),doc.getTypeDocArtiste()),
                ()->assertEquals(docArt.getCitoyenId(),doc.convertUUID(doc.getCitoyenId())),
                ()->assertEquals(docArt.getReponseId(),doc.convertUUID(doc.getReponseId()))
        );
    }

    @DisplayName("TC , Test de création Doc Artiste avec Type de Doc Artiste non valide")
    @Test
    void testCreationDocArtisteNonValide1() {
        doc.setTypeDocArtiste("artiste");
        assertThrows(IllegalArgumentException.class,()->doc.getDTO());
    }

    @DisplayName("TC , Test de création Doc Artiste avec Type de Doc Artiste absent")
    @Test
    void testCreationDocArtisteNonValide2() {
        doc.setTypeDocArtiste("");
        assertThrows(IllegalArgumentException.class,()->doc.getDTO());
    }

    @DisplayName("TC , Test de création Doc Artiste avec date de péremption absent")
    @Test
    void testCreationDocArtisteNonValide3() {
        doc.setDatePeremption("");
        assertThrows(IllegalArgumentException.class,()->doc.getDTO());
    }

    @DisplayName("TC , Test de création Doc Artiste avec numéro de document absent")
    @Test
    void testCreationDocArtisteNonValide4() {
        doc.setNoDoc("");
        assertThrows(IllegalArgumentException.class,()->doc.getDTO());
    }

    @DisplayName("TC , Test de création Doc Artiste avec Reponse ID absent")
    @Test
    void testCreationDocArtisteNonValide5() {
        doc.setReponseId("");
        assertThrows(IllegalArgumentException.class,()->doc.getDTO());
    }

    @DisplayName("TC , Test de création Doc Artiste avec Citoyen ID absent")
    @Test
    void testCreationDocArtisteNonValide6() {
        doc.setCitoyenId("");
        assertThrows(IllegalArgumentException.class,()->doc.getDTO());
    }

}