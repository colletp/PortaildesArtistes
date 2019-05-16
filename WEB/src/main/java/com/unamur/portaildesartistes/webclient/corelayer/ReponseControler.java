package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.ReponseDTO;
import com.unamur.portaildesartistes.DTO.ReponseDTO;
import com.unamur.portaildesartistes.DTO.SecteurDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Reponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Controller
public class ReponseControler extends Controler<ReponseDTO, Class< ReponseDTO >, Reponse> {
    private static final Logger logger = LoggerFactory.getLogger(ReponseControler.class);

    @GetMapping(value = "/Reponse/creer/{id}")
    public String docArtCreateDef( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable("id") UUID itemId
            ,@ModelAttribute("form") final Reponse formRep
            ,Model model){

        model.addAttribute("form",formRep);
        return "Reponse/put.html";
    }

    @GetMapping(value = "/Reponse/modif/{id}")
    public String docArtModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                             @PathVariable("id") UUID itemId ,
                             @ModelAttribute("form") final Reponse formRep,
                             Model model){

        model.addAttribute("form",formRep==null?new Reponse():formRep);
        return super.getForm(cookieValue,new ReponseDTO(),new Reponse(),itemId,ReponseDTO.class,"POST",model);
    }

    @PostMapping(value = "/Reponse")
    public UUID docArtPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final Reponse formRep
            ,Model model){

        try {
            ReponseDTO repDTO = formRep.getDTO();
            return super.postForm(cookieValue,repDTO,method);
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            model.addAttribute("form",formRep);
            throw e;
        }
    }

    @GetMapping(value = "/Reponse")//initialisation du login
    public String docArtList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){

        return super.list(cookieValue,new ReponseDTO(),ReponseDTO.class,model);
    }

    @GetMapping(value = "/Reponse/{id}")//initialisation du login
    public String Form( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                        @PathVariable("id") UUID itemId ,
                        Model model){

        return super.getForm(cookieValue,new ReponseDTO(),new Reponse(),itemId,ReponseDTO.class,"GET",model);
    }

    @GetMapping(value = "Reponse/suppr/{id}")
    public String docArtSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                             @PathVariable("id") UUID itemId,
                             Model model) {

        return super.delete(cookieValue,new ReponseDTO(),itemId,model);
    }

}