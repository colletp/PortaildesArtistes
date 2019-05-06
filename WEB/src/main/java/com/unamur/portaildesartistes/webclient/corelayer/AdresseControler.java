package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.AdresseDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Adresse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class AdresseControler extends Controler<AdresseDTO, Class< AdresseDTO > , Adresse> {
    private static final Logger logger = LoggerFactory.getLogger(AdresseControler.class);

    @GetMapping(value = "/Adresse/creer")
    public String adresseCreate( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){

        return "adresse/put.html";
    }

    @GetMapping(value = "/Adresse/modif/{id}")
    public String adresseModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                                @PathVariable("id") UUID itemId ,
                                Model model){
        logger.error("adresse/modif : Authentication received! Cookie : "+cookieValue );
        return super.getForm(cookieValue,new AdresseDTO(),itemId,AdresseDTO.class,"POST",model);
    }

    @PostMapping(value = "/Adresse")
    public String adressePost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("adrForm") final AdresseDTO usrForm
            ,Model model){
        logger.error("adresse(post) "+method+" : Authentication received! Cookie : "+cookieValue );
        return super.postForm(cookieValue,usrForm,method);
    }

    @GetMapping(value = "/Adresse")
    public String adresseList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        logger.error("adresse List : Authentication received! Cookie : "+cookieValue );
        return super.list(cookieValue,new AdresseDTO(),AdresseDTO.class,model);
    }

    @GetMapping(value = "/Adresse/{id}")
    public String adresse( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") UUID itemId ,
                           Model model){
        logger.error("adresse : Authentication received! Cookie : "+cookieValue );
        return super.getForm(cookieValue,new AdresseDTO(),itemId,AdresseDTO.class,"GET",model);
    }

    @GetMapping(value = "Adresse/suppr/{id}")
    public String adresseSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        return super.delete(cookieValue, new AdresseDTO() ,itemId,model);
    }

}
