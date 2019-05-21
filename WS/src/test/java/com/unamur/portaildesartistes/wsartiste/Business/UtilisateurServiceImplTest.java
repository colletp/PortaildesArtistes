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

    @Test
    void listCitoyen() {
    }

    @Test
    void list() {
    }

    @Test
    void getById() {
    }

    @Test
    void getUuidByName() {
    }

    @DisplayName("Test sur l'update en base de donnée")
    @Disabled
    @Test
    void update() {
        usrId = usrServImpl.insert(usr);

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

        usrServImpl.update(usr);
        UtilisateurDTO newUsr = usrServImpl.getById(usrId);

        //test getUuidByName
        UUID usr2Id = usrServImpl.getUuidByName(usr.getUsername());
        assertTrue( usr2Id.equals(newUsr.getId()) );

        //test user
        assertAll(
                ()->assertEquals( usr.getPassword() , newUsr.getPassword() ),
                ()->assertEquals( usr.getUsername() , newUsr.getUsername() )
        );

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

        usrServImpl.delete(usrId);

    }

    @DisplayName("Test sur l'insertion en base de donnée")
    @Test
    void insert() {
        usrId = usrServImpl.insert(usr);

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

        usrServImpl.delete(usrId);


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
        usrServImpl.insert(usr);
        usrServImpl.delete(usr.getId());
        assertThrows(NullPointerException.class,()->usrServImpl.getById(usr.getId()));
    }

}
