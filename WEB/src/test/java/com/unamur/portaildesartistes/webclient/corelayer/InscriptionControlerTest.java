package com.unamur.portaildesartistes.webclient.corelayer;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.unamur.portaildesartistes.DTO.AdresseDTO;
import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import org.apache.el.util.Validation;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class InscriptionControlerTest {

    private InscriptionControler citoyen;
    private UtilisateurDTO user;

    @BeforeEach
    void setUp() {
        citoyen=new InscriptionControler();
        user=new UtilisateurDTO();
        user.setUsername("teSt2");
        user.setPassword("i5Ts");
        user.setCitoyen(new CitoyenDTO());
        user.getCitoyen().setNom("Dupont");
        user.getCitoyen().setPrenom("Henri");
        //user.getCitoyen().setDateNaissance();
        user.getCitoyen().setResideAdr(new AdresseDTO());
        user.getCitoyen().getResideAdr().setRue("Petite rue d'en face");
        user.getCitoyen().getResideAdr().setNumero("6");
        user.getCitoyen().getResideAdr().setVille("Namur");
        user.getCitoyen().setNrn("01010100522");
        user.getCitoyen().setNation("Belge");
        user.getCitoyen().setTel("081010155");
        user.getCitoyen().setGsm("0495010155");
        user.getCitoyen().setMail("dupont.henry@bosso.be");

    }

    @DisplayName("Test d’inscription avec des données valides, TC 2.1")
    @Test
    void testInscriptValide21() {

        assertEquals(true,citoyen.ValideInscript(user));
    }

    @DisplayName("Test d’inscription avec un mots de passe existant, TC 2.2")
    @Test
    void testInscriptValide22() {
        user.setPassword("i234");
        assertEquals(true,citoyen.ValideInscript(user));
    }

    @DisplayName("Test d’inscription avec un identifiant existant, TC 2.3")
    @Test
    void testInscriptNonValide23() {
        user.setUsername("test1");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test d’inscription sans identifiant, TC 2.4")
    @Test
    void testInscriptNonValide24() {
        user.setUsername("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test d’inscription avec un identifiant non conforme, TC 2.5")
    @Test
    void testInscriptNonValide25() {
        user.setUsername("T");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test d’inscription sans mots de passe, TC 2.6")
    @Test
    void testInscriptNonValide26() {
        user.setPassword("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test d’inscription avec un mots de passe trop long, TC 2.7")
    @Test
    void testInscriptNonValide27() {
        user.setPassword("1235476800ss0");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test d’inscription avec un mots de passe trop faible, TC 2.8")
    @Test
    void testInscriptNonValide28() {
        user.setPassword("0000");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test d’inscription avec un mots de passe trop faible, TC 2.9")
    @Test
    void testInscriptNonValide29() {
        user.setPassword("1234");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test sur le nom, valeur absente, TC 2.10")
    @Test
    void testInscriptNonValide210() {
        user.getCitoyen().setNom("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test sur le prénom, valeur absente, TC 2.11")
    @Test
    void testInscriptNonValide211() {
        user.getCitoyen().setPrenom("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test sur le rue, valeur absente, TC 2.12")
    @Test
    void testInscriptNonValide212() {
        user.getCitoyen().getResideAdr().setRue("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test sur le numéro de rue, valeur absente, TC 2.13")
    @Test
    void testInscriptNonValide213() {
        user.getCitoyen().getResideAdr().setNumero("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test sur la localité, valeur absente, TC 2.14")
    @Test
    void testInscriptNonValide214() {
        user.getCitoyen().getResideAdr().setVille("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test sur la nationalité, valeur absente, TC 2.15")
    @Test
    void testInscriptNonValide215() {
        user.getCitoyen().setNation("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test sur la NRN, valeur absente, TC 2.16")
    @Test
    void testInscriptNonValide216() {
        user.getCitoyen().setNrn("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test sur la NRN, format des valeurs non valide, TC 2.17")
    @Test
    void testInscriptNonValide217() {
        user.getCitoyen().setNrn("");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }
    @DisplayName("Test sur la NRN, valeur erronée, TC 2.18")
    @Test
    void testInscriptNonValide218() {
        user.getCitoyen().setNrn("11111111111");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test sur la NRN, valeur existancte, TC 2.19")
    @Test
    void testInscriptNonValide219() {
        user.getCitoyen().setNrn("1212721501");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test sur le numéro de rue, valeur non valide, TC 2.20")
    @Test
    void testInscriptNonValide220() {
        user.getCitoyen().getResideAdr().setNumero("Ab-c");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test sur la localité, valeur non valide, TC 2.21")
    @Test
    void testInscriptNonValide221() {
        user.getCitoyen().getResideAdr().setVille("123");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test sur le numéro de téléphone, format des valeurs non valide, TC 2.22")
    @Test
    void testInscriptNonValide222() {
        user.getCitoyen().setTel("Ab55486-6");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test sur le numéro de GSM, format des  valeurs non valide, TC 2.23")
    @Test
    void testInscriptNonValide223() {
        user.getCitoyen().setGsm("Ab55482-6");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test sur le mail, manque le @, TC 2.24")
    @Test
    void testInscriptNonValide224() {
        user.getCitoyen().setMail("abc-b.com");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @DisplayName("Test sur le mail, manque le .com ou autre, TC 2.25")
    @Test
    void testInscriptNonValide225() {
        user.getCitoyen().setMail("abc@b");
        assertThrows(IllegalArgumentException.class,()->citoyen.ValideInscript(user));
    }

    @AfterEach
    void tearDown() {
        user=null;
        citoyen=null;
    }


}