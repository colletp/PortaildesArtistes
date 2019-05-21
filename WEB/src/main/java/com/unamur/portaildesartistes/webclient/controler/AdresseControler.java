package com.unamur.portaildesartistes.webclient.controler;

import com.unamur.portaildesartistes.DTO.AdresseDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Adresse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class AdresseControler extends Controler<AdresseDTO, Class< AdresseDTO > , Adresse> {
    private static final Logger logger = LoggerFactory.getLogger(AdresseControler.class);

    @Autowired
    UtilisateurControler usrCtrl;

    @GetMapping(value = "/Adresse/creer")
    public String adresseCreate( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        try {
            usrCtrl.setRoles(cookieValue, model);
        }catch (Exception e){
            model.addAttribute("Err",e.getMessage() );
            return "login.html";
        }
        return "adresse/put.html";
    }

    @GetMapping(value = "/Adresse/modif/{id}")
    public String adresseModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                                @PathVariable("id") UUID itemId ,
                                Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("form",super.getObj(cookieValue,itemId,new AdresseDTO(),AdresseDTO.class,model));
            return "Adresse/post.html";
        }catch( Exception e ){
            return "/login.html";
        }
    }

    @PostMapping(value = "/Adresse")
    public String adressePost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
        ,@ModelAttribute("adrForm") final Adresse adrForm
            ,Model model){
        model.addAttribute("form",adrForm);
        try {
            usrCtrl.setRoles(cookieValue, model);
            adrForm.setId(super.postForm(cookieValue, adrForm, method,model).toString());
            return "/Adresse/get.html";
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            return "/Adresse/"+(method.isEmpty()?"post":method)+".html";
        }catch( Exception e ){
            return "/login.html";
        }
    }

    @GetMapping(value = "/Adresse")
    public String adresseList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("form",super.list(cookieValue,new AdresseDTO(),AdresseDTO.class,model));
            return "Adresse/list.html";
        }catch( Exception e ){
            return "/login.html";
        }
    }

    @GetMapping(value = "/Adresse/{id}")
    public String adresse( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") UUID itemId ,
                           Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("form",super.getObj(cookieValue,itemId,new AdresseDTO(),AdresseDTO.class,model));
            return "Adresse/get.html";
        }catch( Exception e ){
            return "/login.html";
        }
    }

    @GetMapping(value = "Adresse/suppr/{id}")
    public String adresseSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        try{
            usrCtrl.setRoles(cookieValue, model);
            super.delete(cookieValue, new AdresseDTO() ,itemId,model);
            return "Adresse/list.html";
        }catch( Exception e ){
            return "/login.html";
        }
    }

}
