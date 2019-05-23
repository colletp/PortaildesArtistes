package com.unamur.portaildesartistes.webclient.controler;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Activite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class ActiviteControler extends Controler< ActiviteDTO , java.lang.Class< ActiviteDTO > , Activite> {
    private static final Logger logger = LoggerFactory.getLogger(ActiviteControler.class);

    @Autowired
    UtilisateurControler usrCtrl;

    UUID insertActForm(String cookieValue,Activite actForm,UUID formId,Model model)throws Exception{
        HttpHeaders headers = initHeadersRest(cookieValue);
        return restTemplateHelper.putForEntity( UUID.class,configurationService.getUrl()+"/gestionActivite/"+formId,actForm,headers );
    }

    @GetMapping(value = "/Activite/creer")
    public String creerActiviteForm( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        try {
            usrCtrl.setRoles(cookieValue, model);
        }catch (Exception e){
            model.addAttribute("Err",e.getMessage() );
            return "login.html";
        }
        return "activite/put.html";
    }

    @GetMapping(value = "/Activite/modif/{id}")
    public String modifActiviteForm( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                                @PathVariable("id") UUID itemId ,
                                Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("form", super.getObj(cookieValue,itemId,new ActiviteDTO(),ActiviteDTO.class,model) );
            return "Activite/post.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @PostMapping(value = "/Activite")
    public String postActivite( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final Activite actForm
            ,Model model){
        try {
            usrCtrl.setRoles(cookieValue, model);
            actForm.setId( super.postForm(cookieValue, actForm, method, model).toString() );
            //model.addAttribute("form",actForm);
            return "Activite/get.html";
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            return "Activite/"+(method.isEmpty()?"post":method)+".html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @GetMapping(value = "/Activite")
    public String listActivite( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("form",super.list(cookieValue,new ActiviteDTO(),ActiviteDTO.class,model));
            return "Activite/list.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @GetMapping(value = "/Activite/{id}")
    public String getActivite( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") UUID itemId ,
                           Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("form",super.getObj(cookieValue,itemId,new ActiviteDTO(),ActiviteDTO.class,model));
            return "Activite/get.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @GetMapping(value = "Activite/suppr/{id}")
    public String supprActivite( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        try{
            usrCtrl.setRoles(cookieValue, model);
            super.delete(cookieValue, new ActiviteDTO() ,itemId,model);
            return "Activite/list.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

}
