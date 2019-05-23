package com.unamur.portaildesartistes.wsartiste.Business;

import com.unamur.portaildesartistes.DTO.AdresseDTO;
import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import org.junit.jupiter.api.*;
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
class UtilisateurServiceImplTest {

    @Autowired
    private UtilisateurServiceImpl usrServImpl;

    private AdresseServiceImpl adrServImpl;

    //@Autowired
    //WebSecurityConfig cfg;

    private UtilisateurDTO usr;
    private UUID usrId;

//    @Autowired
//    WebSecurityConfig cfg;

    @BeforeEach
    void setUp() {

        usr = new UtilisateurDTO();
        usr.setUsername("teSt2");
        usr.setPassword("i5Ts");

        CitoyenDTO citoyen = new CitoyenDTO();
        usr.setCitoyen(citoyen);
        citoyen.setNom("Dupont");
        citoyen.setPrenom("Henri");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            citoyen.setDateNaissance(sdf.parse("01/01/2001"));
        }catch(ParseException e){}
        citoyen.setNrn("01010100522");
        citoyen.setNation("Belge");
        citoyen.setTel("081010155");
        citoyen.setGsm("0495010155");
        citoyen.setMail("dupont.henry@bosso.be");

        AdresseDTO adresse = new AdresseDTO();
        citoyen.setResideAdr(adresse);
        adresse.setRue("Petite rue d'en face");
        adresse.setNumero("6");
        adresse.setVille("Namur");

    }

    @AfterEach
    void tearDown() {

    }

    @DisplayName("Test sur la récupération d'une liste d'utilisateur de la BD")
    @Test
    void list() {
        assertDoesNotThrow(()->usrServImpl.list());
    }

    @DisplayName("Test sur la récupération de l'utilisateur de la BD avec un UUID")
    @Test
    void getById() {
        UUID user=usrServImpl.getUuidByName("nico");
        assertDoesNotThrow(()->usrServImpl.getById(user));

    }

    @DisplayName("Test sur la récupération de l'UUID de la BD d'un utilisateur avec un nom")
    @Test
    void getUuidByName() {

        assertDoesNotThrow(()->usrServImpl.getUuidByName("nico"));
    }

    @DisplayName("Test sur l'update de la base de donnée")
    @Disabled
    @Test
    void update(){
        try {
            usrId = usrServImpl.insert(usr);
        }catch(Exception e){
            //traitement spécial si l'insert plante?
        }
        usr.setPassword("test");
        CitoyenDTO citoyen = new CitoyenDTO();
        usr.setCitoyen(citoyen);
        citoyen.setNom("Dubois");
        citoyen.setPrenom("Henriette");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            citoyen.setDateNaissance(sdf.parse("01/01/2000"));
        }catch(ParseException e){}
        citoyen.setNrn("01010100621");
        citoyen.setNation("Francais");
        citoyen.setTel("081010705");
        citoyen.setGsm("0495019245");
        citoyen.setMail("Dubois.henriette@bosso.be");

        AdresseDTO adresse = new AdresseDTO();
        citoyen.setResideAdr(adresse);
        adresse.setRue("Grande rue d'en face");
        adresse.setNumero("16");
        adresse.setVille("Bouge");

        try {
            usrServImpl.update(usr);
        }catch(Exception e){
            //traitement spécial si ça plante?
        }
        UtilisateurDTO newUsr = usrServImpl.getById(usrId);

        //test getUuidByName
        UUID usr2Id = usrServImpl.getUuidByName(usr.getUsername());
        assertTrue( usr2Id.equals(newUsr.getId()),"test comparaison de l'id utilisateur" );

        //test user
        assertAll(
                ()->assertEquals( usr.getPassword() , newUsr.getPassword(),"test comparaison de password" ),
                ()->assertEquals( usr.getUsername() , newUsr.getUsername(),"test comparaison du username" )
        );

//test citoyen
        assertAll(
                ()->assertEquals( usr.getCitoyen().getGsm() , newUsr.getCitoyen().getGsm(),"test comparaison du gsm" ),
                ()->assertEquals( usr.getCitoyen().getTel() , newUsr.getCitoyen().getTel(),"test comparaison du téléphone" ),
                ()->assertEquals( usr.getCitoyen().getNrn() , newUsr.getCitoyen().getNrn(),"test comparaison du NRN" ),
                ()->assertEquals( usr.getCitoyen().getMail() , newUsr.getCitoyen().getMail(),"test comparaison du mail") ,
                ()->assertEquals( usr.getCitoyen().getNation() , newUsr.getCitoyen().getNation(),"test comparaison de la nationnalité"),
                ()->assertEquals( usr.getCitoyen().getNom() , newUsr.getCitoyen().getNom(),"test comparaison du nom"),
                ()->assertEquals( usr.getCitoyen().getPrenom() , newUsr.getCitoyen().getPrenom(),"test comparaison du prénom"),
                ()->assertEquals( usr.getCitoyen().getDateNaissance() , newUsr.getCitoyen().getDateNaissance(),"test comparaison de la date de naissance")
        );
//test adresse

        assertEquals( usr.getCitoyen().getResideAdr().toString() , newUsr.getCitoyen().getResideAdr().toString() );

        assertEquals(newUsr.getId(), newUsr.getCitoyen().getId());

        try {
            usrServImpl.delete(usrId);
        }catch(Exception e){
            //traitement spécial si ça plante?
        }

    }

    @DisplayName("Test sur l'insertion en base de donnée")
    @Test
    void insert() {
        try {
            usrId = usrServImpl.insert(usr);
        }catch(Exception e){
            //traitement spécial si ça plante?
        }

        UtilisateurDTO newUsr = usrServImpl.getById(usrId);

//test getUuidByName
        UUID usr2Id = usrServImpl.getUuidByName(usr.getUsername());
        assertTrue( usr2Id.equals(newUsr.getId()) );

//test user
        assertAll(
                ()->assertEquals( usr.getPassword() , newUsr.getPassword() ),
                ()->assertEquals( usr.getUsername() , newUsr.getUsername() )
        );
        assertEquals( usr2Id, newUsr.getId(), "test id" );
//test user
        // assertEquals( usr, newUsr );
//        assertEquals( newUsr, usr );

//test citoyen
        assertAll(
                ()->assertEquals( usr.getCitoyen().getGsm() , newUsr.getCitoyen().getGsm() ),
                ()->assertEquals( usr.getCitoyen().getTel() , newUsr.getCitoyen().getTel() ),
                ()->assertEquals( usr.getCitoyen().getNrn() , newUsr.getCitoyen().getNrn() ),
                ()->assertEquals( usr.getCitoyen().getMail() , newUsr.getCitoyen().getMail()) ,
                ()->assertEquals( usr.getCitoyen().getNation() , newUsr.getCitoyen().getNation()),
                ()->assertEquals( usr.getCitoyen().getNom() , newUsr.getCitoyen().getNom() ),
                ()->assertEquals( usr.getCitoyen().getPrenom() , newUsr.getCitoyen().getPrenom() ),
                ()->assertEquals( usr.getCitoyen().getDateNaissance() , newUsr.getCitoyen().getDateNaissance() )
        );
//test adresse

        assertEquals( usr.getCitoyen().getResideAdr().toString() , newUsr.getCitoyen().getResideAdr().toString() );

        assertEquals(newUsr.getId(), newUsr.getCitoyen().getId());

        try {
            usrServImpl.delete(usrId);
        }catch(Exception e){
            //traitement spécial si ça plante?
        }
        // delete(usrId);
    }

    @DisplayName("Test insert sans citoyen")
    @Test
    void insertInvalid() {

        UtilisateurDTO usr2 = new UtilisateurDTO();

        usr2.setUsername("teSt3");
        usr2.setPassword("i5Ts");

        assertThrows(IllegalArgumentException.class, () -> usrServImpl.insert(usr2));

    }

    @Test
    void testDeleteUser() {
        try {
            usrServImpl.insert(usr);
            usrServImpl.delete(usr.getId());
        }catch(Exception e){
            //traitement spécial si ça plante?
        }
        assertThrows(NullPointerException.class,()->usrServImpl.getById(usr.getId()));
    }

}
