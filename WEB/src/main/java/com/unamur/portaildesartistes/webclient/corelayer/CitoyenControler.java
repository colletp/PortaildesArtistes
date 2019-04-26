package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.webclient.RestTemplateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping(value = "/citoyen")//initialisation du login
    public String citoyenList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
    ,Model model){
        logger.error("citoyen : Authentication received! Cookie : "+cookieValue );

        HttpHeaders headers = new HttpHeaders( );
        headers.add("Cookie",cookieValue);
        List<MediaType> accept = new ArrayList<>();
        accept.add(MediaType.APPLICATION_JSON );
        headers.setAccept( accept );
        List<UtilisateurDTO> reponseServeur=null;
        UtilisateurDTO usr = new UtilisateurDTO();
        try{
            logger.error( "Appel REST" );
            reponseServeur = restTemplateHelper.getForList(UtilisateurDTO.class, configurationService.getUrl()+"/gestionUtilisateur2/list" , headers );
            logger.error( reponseServeur.toString() );
            usr = (reponseServeur==null?new UtilisateurDTO(): reponseServeur.get(0) );
        }
        catch(ClassCastException e){
            logger.error( e.getMessage() );
        }
        model.addAttribute("usrForm", usr );

        return "detailcitoyen.html";
    }
}