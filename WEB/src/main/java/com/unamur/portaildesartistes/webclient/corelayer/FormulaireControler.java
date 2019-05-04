package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.FormulaireDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Formulaire;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class FormulaireControler extends Controler< FormulaireDTO , Class< FormulaireDTO >, Formulaire > {
    private static final Logger logger = LoggerFactory.getLogger(FormulaireControler.class);

    @GetMapping(value = "/Formulaire/creer")
    public String FormCreate( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){

        return "Formulaire/put.html";
    }

    @GetMapping(value = "/Formulaire/modif/{id}")
    public String FormModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                                @PathVariable("id") UUID itemId ,
                                Model model){
        logger.error("Formulaire/modif : Authentication received! Cookie : "+cookieValue );
        return super.getForm(cookieValue,new FormulaireDTO(),itemId,FormulaireDTO.class,"POST",model);
    }

    @PostMapping(value = "/Formulaire")
    public String FormPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final Formulaire usrForm
            ,Model model){
        logger.error("Form(post) "+method+" : Authentication received! Cookie : "+cookieValue );
        //usrForm.setPassword(WebSecurityConfig.encoder().encode( usrForm.getPassword() ) );
        return super.postForm(cookieValue,usrForm,method,model);
    }

    @GetMapping(value = "/Formulaire")//initialisation du login
    public String FormList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
                                ,Model model){
        logger.error("Form List : Authentication received! Cookie : "+cookieValue );

        return super.list(cookieValue,new FormulaireDTO(),FormulaireDTO.class,model);
    }

    @GetMapping(value = "/Formulaire/{id}")//initialisation du login
    public String Form( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") UUID itemId ,
                           Model model){
        logger.error("Form : Authentication received! Cookie : "+cookieValue );
        return super.getForm(cookieValue,new FormulaireDTO(),itemId,FormulaireDTO.class,"GET",model);
    }

    @GetMapping(value = "Formulaire/suppr/{id}")
    public String FormSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        return super.delete(cookieValue,new FormulaireDTO(),itemId,model);
    }
}