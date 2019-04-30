package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Controller
public class CitoyenControler extends Controler< UtilisateurDTO , java.lang.Class< UtilisateurDTO > > {
    private static final Logger logger = LoggerFactory.getLogger(CitoyenControler.class);

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public String handleHttpMediaTypeNotAcceptableException() {
        return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
    }

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
        return super.getForm(cookieValue,itemId,new UtilisateurDTO(),UtilisateurDTO.class,"POST",model);
    }

    @PostMapping(value = "/citoyen")//initialisation du login
    public String citoyenPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("usrForm") final UtilisateurDTO usrForm
            ,Model model){
        logger.error("citoyen(post) "+method+" : Authentication received! Cookie : "+cookieValue );
        //usrForm.setPassword(WebSecurityConfig.encoder().encode( usrForm.getPassword() ) );
        return super.postForm(cookieValue,method,usrForm,UtilisateurDTO.class,model);
    }

    @GetMapping(value = "/citoyen")//initialisation du login
    public String citoyenList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
                                ,Model model){
        logger.error("citoyen List : Authentication received! Cookie : "+cookieValue );

        return super.list(cookieValue,new UtilisateurDTO(),UtilisateurDTO.class,model);
    }

    @GetMapping(value = "/citoyen/{id}")//initialisation du login
    public String citoyen( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") UUID itemId ,
                           Model model){
        logger.error("citoyen : Authentication received! Cookie : "+cookieValue );
        return super.getForm(cookieValue,itemId,new UtilisateurDTO(),UtilisateurDTO.class,"GET",model);
    }

    @GetMapping(value = "citoyen/suppr/{id}")
    public String citoyenSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        return super.delete(cookieValue,itemId,UtilisateurDTO.class,model);
    }
}