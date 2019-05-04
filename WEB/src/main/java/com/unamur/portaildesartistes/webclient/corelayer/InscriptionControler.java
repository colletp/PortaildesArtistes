package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Utilisateur;
import com.unamur.portaildesartistes.webclient.dataForm.UtilisateurInscript;
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
import java.text.ParseException;

@Controller
public class InscriptionControler extends Controler< UtilisateurDTO , java.lang.Class< UtilisateurDTO > , Utilisateur>{

    private static final Logger logger = LoggerFactory.getLogger(com.unamur.portaildesartistes.webclient.corelayer.LoginControler.class);

    @GetMapping(value = "/inscript")
    public String loginView( Model model ){
        UtilisateurInscript usr = new UtilisateurInscript();
        model.addAttribute("form",usr);
        return "inscript.html";
    }

    public Boolean ValideInscript(UtilisateurInscript usrInscrForm){
        try{
            UtilisateurDTO usr = usrInscrForm.getDTO();
            return true;
        }
        catch(Exception e){
            logger.error( e.getMessage() );
            return false;
        }
    }

    @PostMapping(value = "/inscript")
    public String inscript(
            @Valid @ModelAttribute("form") final UtilisateurInscript usrInscrForm ,
            @ModelAttribute("_method") final String method,
            final BindingResult br ,
            final Model model)
    {
        if(br.hasErrors())
        {
            System.out.printf("Found %d fields!%n" , br.getErrorCount());
        }
        String resp = "";
        if(ValideInscript(usrInscrForm)) {
            try {
                resp = postForm("", usrInscrForm.getDTO(), "PUT", model, "inscript");
                model.addAttribute("Msg","Inscription effectuée. Veuillez vous connecter");
                return "login.html";
            } catch (IllegalArgumentException e) {
                logger.error("Erreur lors de la validation du formualaire (Illegal argument): " + e.getMessage());
            } catch (ParseException e) {
                logger.error("Erreur lors de la validation du formualaire (Parse) : " + e.getMessage());
            } catch (HttpClientErrorException e) {
                logger.error("Réponse du serveur: " + e.getStatusCode().toString());
                switch (e.getStatusCode().value()) {
                    case 401:
                        logger.error("Connexion refusée par authentification back-end : " + e.toString());
                        break;
                    case 403:
                        logger.error("Connexion refusée par back-end car interdit : " + e.toString());
                        break;
                    case 404:
                        logger.error("Connexion refusée par back-end car rest introuvable : " + e.toString());
                        break;
                    case 406:
                        logger.error("Connexion refusée par back-end car réponse pas acceptable : " + e.toString());
                        break;
                    case 415:
                        logger.error("Connexion refusée par back-end car média pas supporté : " + e.toString());
                        break;
                    default:
                        logger.error(e.getMessage());
                        logger.error(e.toString());
                        logger.error(e.getCause() == null ? "" : e.getCause().getMessage());
                }
                resp = "Connexion refusée par back-end : " + e.getMessage() + "(voir logs)";
            } catch (ResourceAccessException e) {
                logger.error("Serveur back-end indisponible : " + e.getMessage());
                resp = "Serveur back-end indisponible (voir logs)";
            } catch (RestClientException e) {
                logger.error("RestClientException : " + e.getMessage() + e.getLocalizedMessage());
                resp = "Serveur back-end en erreur REST (voir logs)";
            } catch (Exception e) {
                logger.error(e.toString());
                logger.error(e.getClass().toString());
                logger.error(e.getMessage());
                logger.error(e.getCause() == null ? "" : e.getCause().getMessage());
                //reponseRest
                resp = "Autre erreur non gérée (voir logs)";
            }
            logger.error(resp);
        }
        model.addAttribute("Err",resp);
        return "inscript.html";
    }
}
