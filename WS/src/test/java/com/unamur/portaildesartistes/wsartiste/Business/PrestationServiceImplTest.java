package com.unamur.portaildesartistes.wsartiste.Business;

import com.unamur.portaildesartistes.DTO.*;
import com.unamur.portaildesartistes.wsartiste.security.WebSecurityConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PrestationServiceImplTest {

    @Autowired
    private PrestationServiceImpl prestationService;

    //@Autowired
    //WebSecurityConfig cfg;

    private PrestationDTO prestation;
    private UUID prestId;

    @BeforeEach
    void setUp() {

        prestation=new PrestationDTO();

        ActiviteDTO activite=new ActiviteDTO();
        CommanditaireDTO commanditaire=new CommanditaireDTO();
        commanditaire=null;
        DocArtisteDTO docArtiste=new DocArtisteDTO();
        AdresseDTO adresse=new AdresseDTO();
        SecteurDTO secteur=new SecteurDTO();

        prestation.setActivite(activite);
        activite.setSecteur(secteur);
        secteur.setNomSecteur("Peinture");
        activite.setNomActivite("Jonglage");
        activite.setDescription("Representation");

        prestation.setCommanditaire(commanditaire);

        prestation.setDocArtiste(docArtiste);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            docArtiste.setDatePeremption(sdf.parse("30/06/2019"));
        }catch(ParseException e){}
        docArtiste.setTypeDocArtiste("carte");

        prestation.setSeDeroule(adresse);
        adresse.setRue("Rue de la manivelle");
        adresse.setNumero("9");
        adresse.setVille("Jambes");
        adresse.setBoite("");

        try {
            prestation.setDatePrest(sdf.parse("25/05/2019"));
        }catch(ParseException e){}
        prestation.setDuree(2);
        prestation.setMontant(100.00);
        prestation.setEtat("nouveau");



    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void list() {
    }

    @Test
    void listByTypeId() {
    }

    @Test
    void getById() {
    }

    @Test
    void update() {
    }

    @DisplayName("Test sur l'insertion d'une prestation")
    @Test
    void insert() {
        prestId = prestationService.insert(prestation);
        PrestationDTO newPrest = prestationService.getById(prestId);

        prestationService.delete(prestId);
    }


    @Test
    void delete() {
    }
}