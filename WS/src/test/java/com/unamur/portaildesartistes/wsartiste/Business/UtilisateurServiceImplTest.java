package com.unamur.portaildesartistes.wsartiste.Business;

import com.unamur.portaildesartistes.DTO.AdresseDTO;
import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

//@Service
//@ComponentScan("com.unamur.portaildesartistes")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UtilisateurServiceImplTest {

    @Autowired
    UtilisateurServiceImpl usrServImpl;
    @Autowired
    AdresseServiceImpl adrServImpl;
//    @Autowired
//    WebSecurityConfig cfg;

    UtilisateurDTO usr;
    UUID usrId;
    @BeforeEach
    void setUp() {

        usr = new UtilisateurDTO();
        usr.setUsername("teSt2");
        usr.setPassword("i5Ts");
        //usr.getId() -> est NULL

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
        //citoyen.getId() -> est NULL
        //citoyen.getReside() -> est NULL

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

    @Test
    void update() {
    }

    @Test
    void insert() {
        usrId = usrServImpl.insert(usr);

        UtilisateurDTO newUsr = usrServImpl.getById(usrId);
//test getUuidByName
        UUID usr2Id = usrServImpl.getUuidByName(usr.getUsername());
        assertEquals( usr2Id, newUsr.getId(), "test id" );
//test user
       // assertEquals( usr, newUsr );
//        assertEquals( newUsr, usr );

//test citoyen
        assertEquals( usr.getCitoyen() , newUsr.getCitoyen() );
//test adresse
        assertEquals( usr.getCitoyen().getResideAdr() , newUsr.getCitoyen().getResideAdr() );

        newUsr.getId() .equals( newUsr.getCitoyen().getId() );
        newUsr.getCitoyen().getReside();

        //défaire spécifiquement le insert
        adrServImpl.delete( newUsr.getCitoyen().getReside() );
        usrServImpl.delete(usrId);


       // delete(usrId);
    }

    @Test
    void delete(){

    }

}
