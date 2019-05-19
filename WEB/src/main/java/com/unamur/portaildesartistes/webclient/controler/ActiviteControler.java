package com.unamur.portaildesartistes.webclient.controler;

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
        try{
            model.addAttribute("form", super.getObj(cookieValue,itemId,new ActiviteDTO(),ActiviteDTO.class,model) );
            return "Activite/post.html";
        }catch( Exception e ){
            return "/login.html";
        }
    }

    @PostMapping(value = "/Activite")
    public String activitePost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final Activite actForm
            ,Model model){
        try {
            actForm.setId( super.postForm(cookieValue, actForm, method, model).toString() );
            //model.addAttribute("form",actForm);
            return "Activite/get.html";
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            return "Activite/"+(method.isEmpty()?"post":method)+".html";
        }catch( Exception e ){
            return "/login.html";
        }
    }

    @GetMapping(value = "/Activite")
    public String activiteList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        try{
            model.addAttribute("form",super.list(cookieValue,new ActiviteDTO(),ActiviteDTO.class,model));
            return "Activite/list.html";
        }catch( Exception e ){
            return "/login.html";
        }
    }

    @GetMapping(value = "/Activite/{id}")
    public String activite( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") UUID itemId ,
                           Model model){
        try{
            model.addAttribute("form",super.getObj(cookieValue,itemId,new ActiviteDTO(),ActiviteDTO.class,model));
            return "Activite/get.html";
        }catch( Exception e ){
            return "/login.html";
        }
    }

    @GetMapping(value = "Activite/suppr/{id}")
    public String activiteSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        try{
            super.delete(cookieValue, new ActiviteDTO() ,itemId,model);
            return "Activite/list.html";
        }catch( Exception e ){
            return "/login.html";
        }
    }

}
