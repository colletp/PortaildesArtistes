package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Utilisateur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import javax.validation.Valid;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class InscriptionControler extends Controler< UtilisateurDTO , java.lang.Class< UtilisateurDTO > , Utilisateur>{

    private static final Logger logger = LoggerFactory.getLogger(com.unamur.portaildesartistes.webclient.corelayer.LoginControler.class);

    @GetMapping(value = "/inscript")
    public String loginView( Model model ){
        UtilisateurDTO usr = new UtilisateurDTO();
        model.addAttribute("form",usr);
        return "inscript.html";
    }

    public Boolean ValideInscript(UtilisateurDTO usrDTO){

        return true;
    }

    @PostMapping(value = "/inscript")
    public String inscript(
            @Valid @ModelAttribute("form") final UtilisateurDTO usrDTO ,
            @ModelAttribute("_method") final String method,
            final BindingResult br ,
            final Model model)
    {
        if(br.hasErrors())
        {
            System.out.printf("Found %d fields!%n" , br.getErrorCount());
        }
        ValideInscript(usrDTO);
        String resp="";
        try{
            resp=super.postForm("","POST",usrDTO,model,"inscript" );
            return "inscriptOK.html";
        }
        catch( HttpClientErrorException e){
            logger.error("Réponse du serveur: "+e.getStatusCode().toString() );
            switch( e.getStatusCode().value() ){
                case 401:
                    logger.error( "Connexion refusée par authentification back-end : "+ e.toString() );
                    break;
                case 403:
                    logger.error( "Connexion refusée par back-end car interdit : "+ e.toString() );
                    break;
                case 404:
                    logger.error( "Connexion refusée par back-end car rest introuvable : "+ e.toString() );
                    break;
                case 406:
                    logger.error( "Connexion refusée par back-end car réponse pas acceptable : "+ e.toString() );
                    break;
                case 415:
                    logger.error( "Connexion refusée par back-end car média pas supporté : "+ e.toString() );
                    break;
                default:
                    logger.error( e.getMessage() );
                    logger.error( e.toString() );
                    logger.error( e.getCause()==null?"":e.getCause().getMessage() );
            }
            resp = "Connexion refusée par back-end : "+e.getMessage() +"(voir logs)";
        }
        catch( ResourceAccessException e){
            logger.error( "Serveur back-end indisponible : "+e.getMessage() );
            resp = "Serveur back-end indisponible (voir logs)";
        }
        catch(RestClientException e) {
            logger.error( "RestClientException : "+e.getMessage()+e.getLocalizedMessage() );
            resp = "Serveur back-end en erreur REST (voir logs)";
        }
        catch( Exception e){
            logger.error( e.toString() );
            logger.error( e.getClass().toString() );
            logger.error( e.getMessage() );
            logger.error( e.getCause()==null?"":e.getCause().getMessage() );
            //reponseRest
            resp = "Autre erreur non gérée (voir logs)";
        }
        logger.error(resp);
        return "inscriptKO.html";
    }
}
