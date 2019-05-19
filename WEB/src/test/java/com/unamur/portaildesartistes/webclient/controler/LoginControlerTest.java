package com.unamur.portaildesartistes.webclient.controler;

import com.unamur.portaildesartistes.webclient.RestTemplateHelper;
import com.unamur.portaildesartistes.webclient.dataForm.Utilisateur;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class LoginControlerTest {

    @InjectMocks
    private LoginControler connect;
    @Mock
    private BindingResult bindingResult=mock(BindingResult.class);
    @Mock
    private Model model=mock(Model.class);
    @Mock
    private RestTemplateHelper restTemplateHelper=mock(RestTemplateHelper.class);
    @Mock
    private PropertiesConfigurationService configurationService=mock(PropertiesConfigurationService.class);

    private Utilisateur user;

    @BeforeEach
    void setUp() {
        connect=new LoginControler();
        user=new Utilisateur();
        user.setUsername("test1");
        user.setPassword("i234");
    }

    @DisplayName("TC 1.1, Test de connexion avec des données valides")
    @Test
    void testConnectValide11() {

        assertDoesNotThrow( ()->user.getDTO() );
    }

    @DisplayName("TC 1.2, Test de connexion avec un identifiant non valide")
    @Test
    void testConnectNonValide12() {
        user.setUsername("test");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(configurationService.getUrl()).thenReturn("http://localhost:8081/wsArtiste");
        //when(restTemplateHelper.postForAuth(anyString(),anyString(), (HttpHeaders) anyList(),(HttpHeaders)anyObject()).thenReturn();
        assertEquals("<401 SEE_OTHER See Other,Autre erreur non gérée (voir logs),[]>",connect.authenticate(user,bindingResult,model).toString());
    }

    @DisplayName("TC 1.3, Test de connexion avec un identifiant non valide")
    @Test
    void testConnectNonValide13() {
        user.setUsername("test12");
        assertThrows(IllegalArgumentException.class,()->user.getDTO());
    }

    @DisplayName("TC 1.4, Test de connexion avec un identifiant non valide")
    @Test
    void testConnectNonValide14() {
        user.setUsername("Test1");
        assertThrows(IllegalArgumentException.class,()->user.getDTO());
    }

    @DisplayName("TC 1.5, Test de connexion avec un mot de passe non valide")
    @Test
    void testConnectNonValide15() {
        user.setPassword("1234");
        assertThrows(IllegalArgumentException.class,()->user.getDTO());
    }

    @DisplayName("TC 1.6, Test de connexion avec un mot de passe non valide")
    @Test
    void testConnectNonValide16() {
        user.setPassword("I234");
        assertThrows(IllegalArgumentException.class,()->user.getDTO());
    }

    @DisplayName("TC 1.7, Test de connexion avec un mot de passe non valide")
    @Test
    void testConnectNonValide17() {
        user.setPassword("234");
        assertThrows(IllegalArgumentException.class,()->user.getDTO());
    }

    @DisplayName("TC 1.8, Test de connexion avec un mot de passe non valide")
    @Test
    void testConnectNonValide18() {
        user.setPassword("i2345678");
        assertThrows(IllegalArgumentException.class,()->user.getDTO());
    }

    @DisplayName("TC 1.9, Test de connexion sans un mot de passe")
    @Test
    void testConnectNonValide19() {
        user.setPassword("");
        assertThrows(IllegalArgumentException.class,()->user.getDTO());
    }

    @DisplayName("TC 1.10, Test de connexion sans identifiant")
    @Test
    void testConnectNonValide110() {
        user.setUsername("");
        user.setPassword("");
        assertThrows(IllegalArgumentException.class,()->user.getDTO());
    }


    @AfterEach
    void tearDown() {
        user=null;
        connect=null;
    }
}