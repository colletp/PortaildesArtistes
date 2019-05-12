package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.SecteurDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Secteur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.management.ServiceNotFoundException;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@Controller
public class SecteurControler extends Controler<SecteurDTO , Class< SecteurDTO > , Secteur> {
    private static final Logger logger = LoggerFactory.getLogger(SecteurControler.class);

    //@GetMapping(value = "/Secteur/Activite")
    public String listSecteurActivite( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        logger.error("Secteur : Authentication received! Cookie : "+cookieValue );
        HttpHeaders headers = initHeadersRest(cookieValue);
        List<SecteurDTO> lSectDTO;
        try{
            lSectDTO=restTemplateHelper.getForList(SecteurDTO.class,configurationService.getUrl()+"/gestionSecteur/Activite",headers );
            model.addAttribute("secteurs", lSectDTO );
        }catch( IllegalArgumentException e ){
            model.addAttribute("Err", e.getMessage() );
        //}catch( ParseException e ){
        }catch( ServiceNotFoundException e ){
            model.addAttribute("Err", e.getMessage() );
        }
        return "/fragments/activites.html";
    }


    @GetMapping(value = "/Secteur/creer")
    public String createSecteur( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){

        return "Secteur/put.html";
    }

    @GetMapping(value = "/Secteur/modif/{id}")
    public String modifSecteur( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                                @PathVariable("id") UUID itemId ,
                                Model model){
        logger.error("Secteur/modif : Authentication received! Cookie : "+cookieValue );
        return super.getForm(cookieValue,new SecteurDTO(),new Secteur(),itemId,SecteurDTO.class,"POST",model);
    }

    @PostMapping(value = "/Secteur")
    public String postSecteur( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("usrForm") final SecteurDTO sectForm
            ,Model model){
        logger.error("Secteur(post) "+method+" : Authentication received! Cookie : "+cookieValue );
        return super.postForm(cookieValue,sectForm,method);
    }

    @GetMapping(value = "/Secteur")
    public String listSecteur( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        logger.error("Secteur List : Authentication received! Cookie : "+cookieValue );
        return super.list(cookieValue,new SecteurDTO(),SecteurDTO.class,model);
    }

    @GetMapping(value = "/Secteur/{id}")
    public String getSecteur( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") UUID itemId ,
                           Model model){
        logger.error("Secteur : Authentication received! Cookie : "+cookieValue );
        return super.getForm(cookieValue,new SecteurDTO(),new Secteur(),itemId,SecteurDTO.class,"GET",model);
    }

    @GetMapping(value = "/Secteur/suppr/{id}")
    public String supprSecteur( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        return super.delete(cookieValue, new SecteurDTO() ,itemId,model);
    }

}
