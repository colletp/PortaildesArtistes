package com.unamur.portaildesartistes.webclient.controler;

import com.unamur.portaildesartistes.DTO.SecteurDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Secteur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class SecteurControler extends Controler<SecteurDTO , Class< SecteurDTO > , Secteur> {
    private static final Logger logger = LoggerFactory.getLogger(SecteurControler.class);

    @Autowired
    UtilisateurControler usrCtrl;

    public List<SecteurDTO> listSecteurActivite( String cookieValue , Model model)throws Exception{
        HttpHeaders headers = initHeadersRest(cookieValue);
        return restTemplateHelper.getForList(SecteurDTO.class,configurationService.getUrl()+"/gestionSecteur/Activite",headers );
    }
    public List<SecteurDTO> listSecteurActiviteByForm( String cookieValue, UUID formId,Model model)throws Exception{
        HttpHeaders headers = initHeadersRest(cookieValue);
        return restTemplateHelper.getForList(SecteurDTO.class,configurationService.getUrl()+"/gestionSecteur/SecteurActivite/Form/"+formId.toString(),headers );
    }
    public List<SecteurDTO> listSecteurActiviteByDoc( String cookieValue, UUID docId , Model model)throws Exception{
        HttpHeaders headers = initHeadersRest(cookieValue);
        return restTemplateHelper.getForList(SecteurDTO.class,configurationService.getUrl()+"/gestionSecteur/SecteurActivite/Doc/"+docId.toString(),headers );
    }

    @GetMapping(value = "/Secteur/creer")
    public String createSecteur( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        try {
            usrCtrl.setRoles(cookieValue, model);
        }catch (Exception e){
            model.addAttribute("Err",e.getMessage() );
            return "login.html";
        }
        return "Secteur/put.html";
    }

    @GetMapping(value = "/Secteur/modif/{id}")
    public String modifSecteur( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                                @PathVariable("id") UUID itemId ,
                                Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("form",super.getObj(cookieValue,itemId,new SecteurDTO(),SecteurDTO.class,model) );
            return "Secteur/post.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @PostMapping(value = "/Secteur")
    public String postSecteur( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("usrForm") final Secteur sectForm
            ,Model model){
        try {
            usrCtrl.setRoles(cookieValue, model);
            super.postForm(cookieValue, sectForm, method, model);
            model.addAttribute("form",sectForm);
            return "Secteur/get.html";
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            model.addAttribute("form",sectForm);
            return "Secteur/"+(method.isEmpty()?"post":method)+".html";
        }catch(Exception e){
            model.addAttribute("Err",e.getMessage());
            return "login.html";
        }
    }

    @GetMapping(value = "/Secteur")
    public String listSecteur( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("form",super.list(cookieValue,new SecteurDTO(),SecteurDTO.class,model));
            return "Secteur/list.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @GetMapping(value = "/Secteur/{id}")
    public String getSecteur( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") UUID itemId ,
                           Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("form",super.getObj(cookieValue,itemId,new SecteurDTO(),SecteurDTO.class,model));
            return "Secteur/get.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @GetMapping(value = "/Secteur/suppr/{id}")
    public String supprSecteur( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        try{
            usrCtrl.setRoles(cookieValue, model);
            super.delete(cookieValue, new SecteurDTO() ,itemId,model);
            return "Secteur/list.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }
}
