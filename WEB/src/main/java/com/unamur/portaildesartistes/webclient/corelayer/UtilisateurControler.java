package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.AdresseDTO;
import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Adresse;
import com.unamur.portaildesartistes.webclient.dataForm.Utilisateur;
import com.unamur.portaildesartistes.webclient.dataForm.UtilisateurInscript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.UUID;

@Controller
public class UtilisateurControler extends Controler< UtilisateurDTO , java.lang.Class< UtilisateurDTO >, Utilisateur > {
    private static final Logger logger = LoggerFactory.getLogger(UtilisateurControler.class);

    @GetMapping(value = "/Utilisateur/creer")
    public String citoyenCreate( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        return "Utilisateur/put.html";
    }

    @Autowired
    CitoyenControler citControler;
    @Autowired
    AdresseControler adrControler;

    @GetMapping(value = "/Utilisateur/modif/moi")//initialisation du login
    public String citoyenModifMoi( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        model.addAttribute("form", new UtilisateurInscript( super.getObj(cookieValue, super.getMyId(cookieValue) , new UtilisateurDTO(), UtilisateurDTO.class) ) );
        return "/Utilisateur/post.html";
    }
    @GetMapping(value = "/Utilisateur/modif/{id}")
    public String citoyenModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable("id") UUID itemId
            ,Model model){
        model.addAttribute("form", new UtilisateurInscript(super.getObj(cookieValue, itemId, new UtilisateurDTO(), UtilisateurDTO.class)) );
        return "/Utilisateur/post.html";
    }

    @GetMapping(value = "/Utilisateur")
    public String citoyenList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
                                ,Model model){
        logger.error("citoyen List : Authentication received! Cookie : "+cookieValue );
        return super.list(cookieValue,new UtilisateurDTO(),UtilisateurDTO.class,model);
    }

    @PostMapping(value = "/Utilisateur")
    public String citoyenPostConfirm( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final UtilisateurInscript usrForm
            ,Model model){
        //usrForm.setPassword(WebSecurityConfig.encoder().encode( usrForm.getPassword() ) );
        try{
            UtilisateurDTO usrDTO = usrForm.getDTO();
            super.postForm(cookieValue,usrDTO,method);
            model.addAttribute("form", new UtilisateurInscript(usrDTO) );
            model.addAttribute("Msg","Le profil a été mis à jour" );
            return "/Utilisateur/get.html";
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage() );
            model.addAttribute("form",usrForm);
            return "/Utilisateur/"+(method.isEmpty()?"POST":method)+".html";
        }catch(Exception e){
            model.addAttribute("Err",e.getMessage());
            return "/login";
        }
    }

    @GetMapping(value = "/Utilisateur/{id}")//initialisation du login
    public String citoyen( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") UUID itemId ,
                           Model model){
        return super.getForm(cookieValue,new UtilisateurDTO(),new Utilisateur(),itemId,UtilisateurDTO.class,"GET",model);
    }

    @GetMapping(value = "Utilisateur/suppr/{id}")
    public String citoyenSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        return super.delete(cookieValue,new UtilisateurDTO(),itemId,model);
    }
}