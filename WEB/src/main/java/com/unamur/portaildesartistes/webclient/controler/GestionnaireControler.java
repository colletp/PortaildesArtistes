package com.unamur.portaildesartistes.webclient.controler;

import com.unamur.portaildesartistes.DTO.GestionnaireDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Gestionnaire;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Controller
public class GestionnaireControler extends Controler<GestionnaireDTO , Class<GestionnaireDTO> , Gestionnaire> {
    private static final Logger logger = LoggerFactory.getLogger(GestionnaireControler.class);

    @Autowired
    UtilisateurControler usrCtrl;

    @GetMapping(value = "/Gestionnaire/creer/{citId}")
    public String createGestionnaire( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
                                ,@PathVariable("citId") UUID citId
                                ,Model model){
        try {
            usrCtrl.setRoles(cookieValue, model);
        }catch (Exception e){
            model.addAttribute("Err",e.getMessage() );
            return "login.html";
        }
        return "Gestionnaire/put.html";
    }

    @GetMapping(value = "/Gestionnaire/modif/{id}")
    public String modifGestionnaire( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                                @PathVariable("id") UUID itemId ,
                                Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("form",super.getObj(cookieValue,itemId,new GestionnaireDTO(),GestionnaireDTO.class,model) );
            return "Gestionnaire/post.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @PostMapping(value = "/Gestionnaire")
    public String postGestionnaire( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("usrForm") final Gestionnaire sectForm
            ,Model model){
        try {
            usrCtrl.setRoles(cookieValue, model);
            super.postForm(cookieValue, sectForm, method, model);
            return "Gestionnaire/get.html";
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            return "Gestionnaire/"+(method.isEmpty()?"post":method)+".html";
        }catch(Exception e){
            model.addAttribute("form",sectForm);
            return "Gestionnaire/get.html";
        }
    }

    @GetMapping(value = "/Gestionnaire")
    public String listGestionnaire( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("gest",super.list(cookieValue,new GestionnaireDTO(),GestionnaireDTO.class,model));
            return "Gestionnaire/list.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @GetMapping(value = "/Gestionnaire/{id}")
    public String getGestionnaire( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") UUID itemId ,
                           Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("gest",super.getObj(cookieValue,itemId,new GestionnaireDTO(),GestionnaireDTO.class,model));
            return "Gestionnaire/get.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @GetMapping(value = "/Gestionnaire/suppr/{id}")
    public String supprGestionnaire( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        try{
            usrCtrl.setRoles(cookieValue, model);
            super.delete(cookieValue, new GestionnaireDTO() ,itemId,model);
            return "Gestionnaire/list.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }
}
