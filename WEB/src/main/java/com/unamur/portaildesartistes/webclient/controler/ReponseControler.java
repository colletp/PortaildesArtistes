package com.unamur.portaildesartistes.webclient.controler;

import com.unamur.portaildesartistes.DTO.ReponseDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Reponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
                             Model model){

        model.addAttribute("form",super.getForm(cookieValue,new ReponseDTO(),new Reponse(),itemId,ReponseDTO.class,"POST",model));
        return "Reponse/post.html";
    }

    @PostMapping(value = "/Reponse")
    public String docArtPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final Reponse formRep
            ,Model model){

            model.addAttribute("form",super.postForm(cookieValue, formRep.getDTO() ,method,model));
            return "Response/"+(method.isEmpty()?"post":method)+".html";
    }

    @GetMapping(value = "/Reponse")//initialisation du login
    public String docArtList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        model.addAttribute("form",super.list(cookieValue,new ReponseDTO(),ReponseDTO.class,model));
        return "Reponse/list.html";
    }

    @GetMapping(value = "/Reponse/{id}")//initialisation du login
    public String getReponse( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                        @PathVariable("id") UUID itemId ,
                        Model model){
        model.addAttribute("form",super.getForm(cookieValue,new ReponseDTO(),new Reponse(),itemId,ReponseDTO.class,"GET",model));
        return "Reponse/get.html";
    }

    @GetMapping(value = "Reponse/suppr/{id}")
    public String docArtSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                             @PathVariable("id") UUID itemId,
                             Model model) {
        super.delete(cookieValue,new ReponseDTO(),itemId,model);
        return "Reponse/list.html";
    }

}