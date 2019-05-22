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

    //@Autowired
    //WebSecurityConfig cfg;

    private PrestationDTO prestation;
    private UtilisateurDTO user;
    private CitoyenDTO cit;
    private ReponseDTO rep;
    private TraitementDTO trt;
    private GestionnaireDTO gest;
    private FormulaireDTO form;


    private UUID prestId;
    private UUID docArtId;
    private UUID citId;
    private UUID userId;
    private UUID repId;
    private UUID trtId;
    private UUID gestId;
    private UUID formId;


    @BeforeEach
    void setUp() {

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

        userId=userServ.insert(user);

        rep=new ReponseDTO();
        trt=new TraitementDTO();
        form=new FormulaireDTO();
        formId=formServ.insert(form);
        gest=new GestionnaireDTO();
        gestId=gestServ.insert(gest);
        trt.setGestId(gestId);
        trt.setFormId(formId);
        trtId=trtServ.insert(trt);
        //rep.setCitoyenId(userId);
        try {
            rep.setDateReponse( sdf.parse("21/05/2019") );
        }catch(ParseException e){}
        rep.setTrtId(trtId);
        repId=repServ.insert(rep);

        prestation=new PrestationDTO();

        ActiviteDTO activite=new ActiviteDTO();
        CommanditaireDTO commanditaire=new CommanditaireDTO();
        commanditaire=null;

        DocArtisteDTO docArtiste=new DocArtisteDTO();
        AdresseDTO adresse=new AdresseDTO();
        SecteurDTO secteur=new SecteurDTO();
        docArtiste.setCitoyenId(userId);
        docArtiste.setReponseId(repId);

        prestation.setActivite(activite);
        activite.setSecteur(secteur);
        secteur.setNomSecteur("Peinture");
        activite.setNomActivite("Jonglage");
        activite.setDescription("Representation");

        prestation.setCommanditaire(commanditaire);


        prestation.setDocArtiste(docArtiste);
        try {
            docArtiste.setDatePeremption(sdf.parse("30/06/2019"));
        }catch(ParseException e){}
        docArtiste.setTypeDocArtiste("Carte artiste");
        docArtId=docArtServ.insert(docArtiste);

        prestation.setSeDeroule(adresse);
        adresse.setRue("Rue de la manivelle");
        adresse.setNumero("9");
        adresse.setVille("Jambes");
        adresse.setBoite("");

        try {
            prestation.setDatePrest(sdf.parse("30/05/2019"));
        }catch(ParseException e){}
        prestation.setDuree(2);
        prestation.setMontant(100.00);
        prestation.setEtat("Nouvelle");

    }

    @AfterEach
    void tearDown() {
        docArtServ.delete(docArtId);
        userServ.delete(userId);
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