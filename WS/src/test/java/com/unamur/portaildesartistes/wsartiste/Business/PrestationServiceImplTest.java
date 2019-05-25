package com.unamur.portaildesartistes.wsartiste.Business;

import com.unamur.portaildesartistes.DTO.*;
import com.unamur.portaildesartistes.wsartiste.security.WebSecurityConfig;
import com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat.SecteurServiceFront;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PrestationServiceImplTest {

    @Autowired
    private PrestationServiceImpl prestationService;

    @Autowired
    private DocArtisteServiceImpl docArtServ;

    @Autowired
    private CitoyenServiceImpl citServ;

    @Autowired
    private UtilisateurServiceImpl userServ;

    @Autowired
    private ReponseServiceImpl repServ;

    @Autowired
    private TraitementServiceImpl trtServ;

    @Autowired
    private GestionnaireServiceImpl gestServ;

    @Autowired
    private FormulaireServiceImpl formServ;

    @Autowired
    private ActiviteServiceImpl actServ;

    @Autowired
    private AdresseServiceImpl adrServ;

    //@Autowired
    //WebSecurityConfig cfg;

    private PrestationDTO prestation;
    private UtilisateurDTO user;
    private CitoyenDTO cit;
    private ReponseDTO rep;
    private TraitementDTO trt;
    private GestionnaireDTO gest;
    private FormulaireDTO form;
    private AdresseDTO adresse;
    private SecteurDTO secteur;
    private ActiviteDTO activite;
    private DocArtisteDTO docArtiste;


    private UUID prestId;
    private UUID docArtId;
    private UUID citId;
    private UUID userId;
    private UUID repId;
    private UUID trtId;
    private UUID gestId;
    private UUID formId;
    private UUID actId;
    private UUID sectId;
    private UUID adrId;


    @BeforeEach
    void setUp() {

        //un utilisateur
        user = new UtilisateurDTO();
        user.setUsername("teSt2");
        user.setPassword("i5Ts");

        cit=new CitoyenDTO();
        user.setCitoyen(cit);
        cit.setNom("Dupont");
        cit.setPrenom("Henri");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            cit.setDateNaissance(sdf.parse("01/01/2001"));
        }catch(ParseException e){}
        cit.setNrn("01010100522");
        cit.setNation("Belge");
        cit.setTel("081010155");
        cit.setGsm("0495010155");
        cit.setMail("dupont.henry@bosso.be");

        AdresseDTO adresseCit = new AdresseDTO();
        cit.setResideAdr(adresseCit);
        adresseCit.setRue("Petite rue d'en face");
        adresseCit.setNumero("6");
        adresseCit.setVille("Namur");

        try {
            userId=userServ.insert(user);
            //reponse, formulaire et gestionnaire
            rep=new ReponseDTO();
            trt=new TraitementDTO();
            //formulaire
            form=new FormulaireDTO();
            form.setCitoyenId(citId);
            form.setLangue("FR");
            form.setCarte(true);
            try {
                form.setDateDemande(Timestamp.from(sdf.parse("23/05/2019").toInstant()));
            }catch(ParseException e){}
            form.setVisa(false);
            formId=formServ.insert(form);
            gest=new GestionnaireDTO();
            UtilisateurServiceImpl gestionnaire=new UtilisateurServiceImpl();
            gestId=gestionnaire.getUuidByName("nico");
            gestServ.getByCitoyenId(gestId);
            trt.setGestId(gestId);
            trt.setFormId(formId);
            trtId=trtServ.insert(trt);
            //rep.setCitoyenId(userId);
            try {
                rep.setDateReponse( sdf.parse("21/05/2019") );
            }catch(ParseException e){}
            rep.setTrtId(trtId);
            repId=repServ.insert(rep);
        }catch(Exception e){
            //traitement spécial si ça plante?
        }

        prestation=new PrestationDTO();

        activite=new ActiviteDTO();

        docArtiste=new DocArtisteDTO();
        adresse=new AdresseDTO();
        secteur=new SecteurDTO();
        docArtiste.setCitoyen( citServ.getById(userId) );
        docArtiste.setReponseId(repId);

        prestation.setActivite(activite);

        secteur.setNomSecteur("Spectacle");
        try {
            sectId=UUID.fromString("b407f210-6241-45be-868c-681e1fc0e3c3");
        } catch (Exception e) {
            e.printStackTrace();
        }

        activite.setSecteurId(sectId);
        activite.setNomActivite("Jonglage");
        activite.setDescription("Representation");

        try {
            actId=actServ.insert(activite,formId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        prestation.setActiviteId(actId);

        prestation.setCommanditaireId(UUID.fromString("b1ac0cf9-3723-4af6-b632-86450bd93c16"));

        prestation.setDocArtiste(docArtiste);
        try {
            docArtiste.setDatePeremption(sdf.parse("30/06/2019"));
        }catch(ParseException e){}
        docArtiste.setTypeDocArtiste("Carte artiste");

        try {
            docArtId=docArtServ.insert(docArtiste);
        }catch(Exception e){
            //traitement spécial si ça plante?
        }

        adresse.setRue("Rue de la manivelle");
        adresse.setNumero("9");
        adresse.setVille("Jambes");
        adresse.setBoite("");
        try {
            adrId=adrServ.insert(adresse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        prestation.setSeDerouleId(adrId);

        try {
            prestation.setDatePrest(sdf.parse("25/05/2019"));
        }catch(ParseException e){}
        prestation.setDuree(2);
        prestation.setMontant(100.00);
        prestation.setEtat("Nouvelle");

    }

    @AfterEach
    void tearDown() {
        try {
            docArtServ.delete(docArtId);
            userServ.delete(userId);
            repServ.delete(repId);
            trtServ.delete(trtId);
            gestServ.delete(gestId);
            formServ.delete(formId);
        }catch(Exception e){
            //traitement spécial si ça plante?
        }
    }

    @Test
    void list() {
        assertDoesNotThrow(()->prestationService.list());
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

    @DisplayName("Test sur l'insertion d'une prestation dans la db")
    @Test
    void insert() {
        try {
            prestId = prestationService.insert(prestation);
        }catch(Exception e){
            //traitement spécial si ça plante?
        }
        System.out.println("prest = "+prestId);
        PrestationDTO newPrest = prestationService.getById(prestId);

        //test getUuidByName
        assertTrue( prestId.equals(newPrest.getId()) );

        //test parametre
        assertAll(
                ()->assertEquals(prestation.getActiviteId(),newPrest.getActiviteId()),
                ()->assertEquals(prestation.getCommanditaireId(),newPrest.getCommanditaireId()),
                ()->assertEquals(prestation.getDocArtisteId(),newPrest.getDocArtisteId()),
                ()->assertEquals(prestation.getSeDerouleId(),newPrest.getSeDerouleId()),
                ()->assertEquals(prestation.getSeDeroule(),newPrest.getSeDeroule()),
                ()->assertEquals(prestation.getDatePrest(),newPrest.getDatePrest()),
                ()->assertEquals(prestation.getDuree(),newPrest.getDuree()),
                ()->assertEquals(prestation.getEtat(),newPrest.getEtat()),
                ()->assertEquals(prestation.getMontant(),newPrest.getMontant())
        );

        //test sur le total de jour pour toutes les prestations de la carte artiste

        try {
            prestationService.delete(prestId);
        }catch(Exception e){
            //traitement spécial si ça plante?
        }
    }


    @Test
    void delete() {
        try {
            prestationService.insert(prestation);
            prestationService.delete(prestation.getId());
        }catch(Exception e){
            //traitement spécial si ça plante?
        }
        assertThrows(NullPointerException.class,()->prestationService.getById(prestation.getId()));
    }
}