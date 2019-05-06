package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Activite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class ActiviteControler extends Controler< ActiviteDTO , java.lang.Class< ActiviteDTO > , Activite> {
    private static final Logger logger = LoggerFactory.getLogger(ActiviteControler.class);

    @GetMapping(value = "/activite/creer")
    public String activiteCreate( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){

        return "activite/put.html";
    }

    @GetMapping(value = "/activite/modif/{id}")
    public String activiteModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                                @PathVariable("id") UUID itemId ,
                                Model model){
        logger.error("activite/modif : Authentication received! Cookie : "+cookieValue );
        return super.getForm(cookieValue,new ActiviteDTO(),itemId,ActiviteDTO.class,"POST",model);
    }

    @PostMapping(value = "/activite")
    public String activitePost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("usrForm") final ActiviteDTO usrForm
            ,Model model){
        logger.error("activite(post) "+method+" : Authentication received! Cookie : "+cookieValue );
        return super.postForm(cookieValue,usrForm,method,model);
    }

    @GetMapping(value = "/activite")
    public String activiteList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        logger.error("activite List : Authentication received! Cookie : "+cookieValue );
        return super.list(cookieValue,new ActiviteDTO(),ActiviteDTO.class,model);
    }

    @GetMapping(value = "/activite/{id}")
    public String activite( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") UUID itemId ,
                           Model model){
        logger.error("activite : Authentication received! Cookie : "+cookieValue );
        return super.getForm(cookieValue,new ActiviteDTO(),itemId,ActiviteDTO.class,"GET",model);
    }

    @GetMapping(value = "activite/suppr/{id}")
    public String activiteSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        return super.delete(cookieValue, new ActiviteDTO() ,itemId,model);
    }

}
