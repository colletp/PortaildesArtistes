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
        try {
            postForm("", usrInscrForm.getDTO(), "PUT","inscript");
            model.addAttribute("Msg","Inscription effectuée. Veuillez vous connecter");
            return "login.html";
        } catch (IllegalArgumentException e) {
            model.addAttribute("Err","Erreur lors de la validation du formualaire (Illegal argument): " + e.getMessage());
        } catch (HttpClientErrorException e) {
            switch (e.getStatusCode().value()) {
                case 401:
                    model.addAttribute("Err","Connexion refusée par authentification back-end : " + e.toString());
                    break;
                case 403:
                    model.addAttribute("Err","Connexion refusée par back-end car interdit : " + e.toString());
                    break;
                case 404:
                    model.addAttribute("Err","Connexion refusée par back-end car rest introuvable : " + e.toString());
                    break;
                case 406:
                    model.addAttribute("Err","Connexion refusée par back-end car réponse pas acceptable : " + e.toString());
                    break;
                case 415:
                    model.addAttribute("Err","Connexion refusée par back-end car média pas supporté : " + e.toString());
                    break;
                default:
                    model.addAttribute("Err",e.getMessage());
            }
            model.addAttribute("Err","Connexion refusée par back-end : " + e.getMessage());
        } catch (ResourceAccessException e) {
            model.addAttribute("Err","Serveur back-end indisponible : " + e.getMessage());
        } catch (RestClientException e) {
            model.addAttribute("Err","RestClientException : " + e.getMessage() + e.getLocalizedMessage());
        } catch (Exception e) {
            model.addAttribute("Err",e.toString());
        }
        return "inscript.html";
    }
}
