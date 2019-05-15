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

    @GetMapping(value = "/Activite/creer")
    public String activiteCreate( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        return "activite/put.html";
    }

    @GetMapping(value = "/Activite/modif/{id}")
    public String activiteModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                                @PathVariable("id") UUID itemId ,
                                Model model){
        Activite actForm = new Activite();
        return super.getForm(cookieValue,new ActiviteDTO(),actForm,itemId,ActiviteDTO.class,"POST",model);

    }

    @PostMapping(value = "/Activite")
    public String activitePost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final Activite form
            ,Model model){
        super.postForm(cookieValue,form,method);
        return "/Activite/get.html";
    }

    @GetMapping(value = "/Activite")
    public String activiteList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        return super.list(cookieValue,new ActiviteDTO(),ActiviteDTO.class,model);
    }

    @GetMapping(value = "/Activite/{id}")
    public String activite( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") UUID itemId ,
                           Model model){
        return super.getForm(cookieValue,new ActiviteDTO(),new Activite(),itemId,ActiviteDTO.class,"GET",model);
    }

    @GetMapping(value = "Activite/suppr/{id}")
    public String activiteSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        return super.delete(cookieValue, new ActiviteDTO() ,itemId,model);
    }

}
