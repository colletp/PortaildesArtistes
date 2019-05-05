package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Utilisateur;
import com.unamur.portaildesartistes.webclient.dataForm.UtilisateurInscript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.UUID;

@Controller
public class CitoyenControler extends Controler< UtilisateurDTO , java.lang.Class< UtilisateurDTO >, Utilisateur > {
    private static final Logger logger = LoggerFactory.getLogger(CitoyenControler.class);

    @GetMapping(value = "/Utilisateur/creer")
    public String citoyenCreate( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        return "Utilisateur/put.html";
    }

    @GetMapping(value = "/Utilisateur/modif/moi")//initialisation du login
    public String citoyenModifMoi( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        logger.error("Utilisateur/modif/moi : Authentication received! Cookie : "+cookieValue );
        //try {
        UtilisateurInscript usr = new UtilisateurInscript();
        UtilisateurDTO usrDTO = super.getObj(cookieValue, super.getMyId(cookieValue), new UtilisateurDTO(), UtilisateurDTO.class);
        usr.setUtilisateur( usrDTO );
        usr.setCitoyen(usrDTO.getCitoyen());
        usr.setAdresse(usrDTO.getCitoyen().getResideAdr());
        //usr.setCitoyen( super.getObj(cookieValue, super.getMyId(cookieValue), new CitoyenDTO(), CitoyenDTO.class) );
        //usr.setAdresse( super.getObj(cookieValue, super.getMyId(cookieValue), new UtilisateurDTO(), UtilisateurDTO.class) );
        model.addAttribute("form",usr);
        return "/Utilisateur/post.html";
        //}catch(ParseException e) {
            /*model.addAttribute("Err",e.getMessage());
            model.addAttribute("form",new UtilisateurInscript().getDTO());
            */
        //    return "/Utilisateur/post.html";
        //}
    }
    @GetMapping(value = "/Utilisateur/modif/{id}")//initialisation du login
    public String citoyenModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable("id") UUID itemId
            ,Model model){
        logger.error("Utilisateur/modif : Authentication received! Cookie : "+cookieValue );
        return super.getForm(cookieValue,new UtilisateurDTO(),itemId,UtilisateurDTO.class,"POST",model);
    }

    @PostMapping(value = "/Utilisateur")
    public String citoyenPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final UtilisateurInscript usrForm
            ,Model model){
        logger.error("citoyen(post) "+method+" : Authentication received! Cookie : "+cookieValue );
        //usrForm.setPassword(WebSecurityConfig.encoder().encode( usrForm.getPassword() ) );
        UtilisateurDTO usrDTO=null;
        try{
            usrDTO = usrForm.getDTO();
            return super.postForm(cookieValue,usrDTO,method,model);
        }catch(IllegalArgumentException e){
            logger.error(e.getMessage());
            model.addAttribute("Err",e.getMessage() );
            return "/Utilisateur/"+method+".html";
        }catch(ParseException e){
            logger.error(e.getMessage());
            model.addAttribute("Err",e.getMessage() );
            return "/Utilisateur/"+method+".html";
        }
    }

    @GetMapping(value = "/Utilisateur")//initialisation du login
    public String citoyenList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
                                ,Model model){
        logger.error("citoyen List : Authentication received! Cookie : "+cookieValue );

        return super.list(cookieValue,new UtilisateurDTO(),UtilisateurDTO.class,model);
    }

    @GetMapping(value = "/Utilisateur/{id}")//initialisation du login
    public String citoyen( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") UUID itemId ,
                           Model model){
        logger.error("citoyen : Authentication received! Cookie : "+cookieValue );
        return super.getForm(cookieValue,new UtilisateurDTO(),itemId,UtilisateurDTO.class,"GET",model);
    }

    @GetMapping(value = "Utilisateur/suppr/{id}")
    public String citoyenSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        return super.delete(cookieValue,new UtilisateurDTO(),itemId,model);
    }
}