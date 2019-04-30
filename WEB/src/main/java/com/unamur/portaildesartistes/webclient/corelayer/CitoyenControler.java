package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.webclient.RestTemplateHelper;
import com.unamur.portaildesartistes.webclient.security.WebSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class CitoyenControler {
    private static final Logger logger = LoggerFactory.getLogger(CitoyenControler.class);

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public String handleHttpMediaTypeNotAcceptableException() {
        return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
    }

    @Autowired
    private RestTemplateHelper restTemplateHelper;

    @Autowired
    private PropertiesConfigurationService configurationService ;

    @Autowired
    @Qualifier("getMediaTypeYaml")
    MediaType yaml;

    @GetMapping(value = "/citoyen/creer")//initialisation du login
    public String citoyenCreate( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){

        return "citoyen/put.html";
    }

    @GetMapping(value = "/citoyen/modif/{id}")//initialisation du login
    public String citoyenModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                                @PathVariable("id") UUID itemId ,
                                Model model){
        logger.error("citoyen/modif : Authentication received! Cookie : "+cookieValue );

        HttpHeaders headers = new HttpHeaders( );
        headers.add("Cookie",cookieValue);
        List<MediaType> accept = new ArrayList<>();
        accept.add(MediaType.APPLICATION_JSON );
        headers.setAccept( accept );
        UtilisateurDTO reponseServeur=null;
        UtilisateurDTO usr = new UtilisateurDTO();
        try{
            logger.error( "Appel REST" );
            reponseServeur = restTemplateHelper.getForEntity( UtilisateurDTO.class, configurationService.getUrl()+"/gestionUtilisateur/"+itemId , headers );
            logger.error( reponseServeur.toString() );
            usr = (reponseServeur==null?new UtilisateurDTO(): reponseServeur );
        }
        catch(ClassCastException e){
            logger.error( e.getMessage() );
        }
        model.addAttribute("usrForm", usr );

        return "citoyen/post.html";
    }

    @PostMapping(value = "/citoyen")//initialisation du login
    public String citoyenPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("usrForm") final UtilisateurDTO usrForm
            ,Model model){
        logger.error("citoyen(post) "+method+" : Authentication received! Cookie : "+cookieValue );

        //usrForm.setPassword(WebSecurityConfig.encoder().encode( usrForm.getPassword() ) );

        HttpHeaders headers = new HttpHeaders( );
        headers.add("Cookie",cookieValue);
        List<MediaType> accept = new ArrayList<>();
        accept.add(MediaType.APPLICATION_JSON );
        headers.setAccept( accept );
        try{
            switch(method.toUpperCase()){
                case "PUT":
                    logger.error( "Appel REST PUT" );
                    logger.error( usrForm.getUsername() );
                    logger.error( usrForm.getId().toString() );

                    UUID uuid = restTemplateHelper.putForEntity( UUID.class, configurationService.getUrl()+"/gestionUtilisateur" , usrForm , headers );
                    usrForm.setId(uuid);
                    break;
                case "POST":
                case "":
                    logger.error( "Appel REST POST" );
                    logger.error( usrForm.getId().toString() );
                    logger.error( usrForm.getUsername() );
                    restTemplateHelper.postForEntity( configurationService.getUrl()+"/gestionUtilisateur" , usrForm , headers );
                    break;
                /*case "GET":
                    logger.error( "Appel REST GET" );
                    reponseServeur = restTemplateHelper.getForList(UtilisateurDTO.class, configurationService.getUrl()+"/gestionUtilisateur2/list" , headers );
                    logger.error( reponseServeur.toString() );
                    usr = (reponseServeur==null?new UtilisateurDTO(): reponseServeur.get(0) );
                    break;
                */
                default :
                    logger.error( "Appel REST : "+method );
            }
            //usr = (reponseServeur==null?new UtilisateurDTO(): reponseServeur );
        }
        catch(ClassCastException e){
            logger.error( e.getMessage() );
        }
        model.addAttribute("usrForm", usrForm );
        //model.addAttribute( "id",usrForm.getId() );
        return "citoyen/get.html";
    }

    @GetMapping(value = "/citoyen")//initialisation du login
    public String citoyenList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
                                ,Model model){
        logger.error("citoyen List : Authentication received! Cookie : "+cookieValue );

        HttpHeaders headers = new HttpHeaders( );
        headers.add("Cookie",cookieValue);
        List<MediaType> accept = new ArrayList<>();
        accept.add(MediaType.APPLICATION_JSON );
        headers.setAccept( accept );
        List<UtilisateurDTO> reponseServeur=null;
        try{
            logger.error( "Appel REST" );
            reponseServeur = restTemplateHelper.getForList(UtilisateurDTO.class, configurationService.getUrl()+"/gestionUtilisateur" , headers );
            logger.error( reponseServeur.toString() );
        }
        catch(ClassCastException e){
            logger.error( e.getMessage() );
        }
        model.addAttribute("usrForm", reponseServeur );

        return "citoyen/list.html";
    }

    @GetMapping(value = "/citoyen/{id}")//initialisation du login
    public String citoyen( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") UUID itemId ,
                           Model model){
        logger.error("citoyen : Authentication received! Cookie : "+cookieValue );

        HttpHeaders headers = new HttpHeaders( );
        headers.add("Cookie",cookieValue);
        List<MediaType> accept = new ArrayList<>();
        accept.add(MediaType.APPLICATION_JSON );
        headers.setAccept( accept );
        UtilisateurDTO reponseServeur=null;
        UtilisateurDTO usr = new UtilisateurDTO();
        try{
            logger.error( "Appel REST" );
            reponseServeur = restTemplateHelper.getForEntity(UtilisateurDTO.class, configurationService.getUrl()+"/gestionUtilisateur/"+itemId , headers );
            logger.error( reponseServeur.toString() );
            usr = (reponseServeur==null?new UtilisateurDTO(): reponseServeur );
        }
        catch(ClassCastException e){
            logger.error( e.getMessage() );
        }
        model.addAttribute("usrForm", usr );

        return "citoyen/get.html";
    }

    @GetMapping(value = "citoyen/suppr/{id}")
    public String citoyenSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        HttpHeaders headers = new HttpHeaders( );
        headers.add("Cookie",cookieValue);
        List<MediaType> accept = new ArrayList<>();
        accept.add(MediaType.APPLICATION_JSON );
        headers.setAccept( accept );
        restTemplateHelper.delete(configurationService.getUrl()+"/gestionUtilisateur/"+itemId , headers );
        return "citoyen/list.html";
    }

}