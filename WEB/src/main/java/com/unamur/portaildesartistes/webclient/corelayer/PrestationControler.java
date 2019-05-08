package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.PrestationDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Prestation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.UUID;

// Date de la prestation (si plusieurs jours compléter plusieurs lignes)
// Nature de la prestation
// Nom du donneur d’ordre ou numéro *BCE (*Banque-carrefour des entreprises)
// Signature du donneur d’ordre
// Indemnités prévues

@Controller
public class PrestationControler extends Controler<PrestationDTO, Class< PrestationDTO >, Prestation> {
    private static final Logger logger = LoggerFactory.getLogger(FormulaireControler.class);

    @Autowired
    private SecteurControler sectCtrl;

    @GetMapping(value = "/Prestation/creer")
    public String FormCreate(@CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            , @ModelAttribute("form") final Prestation formPrest
            , Model model){
        model.addAttribute("form",formPrest);
        String fragment = sectCtrl.listSecteurActivite( cookieValue , model );
        return "Prestation/put.html";
    }

    @GetMapping(value = "/Prestation/creer")
    public String FormCreateDef( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("form") final Prestation formPrest
            ,Model model){

        model.addAttribute("form",formPrest);
        model.addAttribute("activites",new ArrayList<String>());
        String fragment = sectCtrl.listSecteurActivite( cookieValue , model );
        return "Prestation/put.html";
    }

    @GetMapping(value = "/Prestation/modif/{id}")
    public String FormModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                             @PathVariable("id") UUID itemId ,
                             @ModelAttribute("form") final Prestation formPrest,
                             Model model){
        logger.error("Prestation/modif : Authentication received! Cookie : "+cookieValue );
        model.addAttribute("form",formPrest==null?new Prestation():formPrest);
        return super.getForm(cookieValue,new PrestationDTO(),new Prestation(),itemId,PrestationDTO.class,"POST",model);
    }

    @PostMapping(value = "/Formulaire")
    public String FormPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final Prestation formPrest
            ,Model model){
        logger.error("Form(post) "+method+" : Authentication received! Cookie : "+cookieValue );
        try {
            PrestationDTO formDTO = formPrest.getDTO();
            return super.postForm(cookieValue,formDTO,method);
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            model.addAttribute("form",formPrest);
            return "/Prestation/"+method+".html";
        }catch(ParseException e){
            model.addAttribute("Err",e.getMessage());
            model.addAttribute("form",formPrest);
            return "/Prestation/"+method+".html";
        }

    }

    @GetMapping(value = "/Prestation")//initialisation du login
    public String FormList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        logger.error("Form List : Authentication received! Cookie : "+cookieValue );

        return super.list(cookieValue,new PrestationDTO(),PrestationDTO.class,model);
    }

    @GetMapping(value = "/Prestation/{id}")//initialisation du login
    public String Form( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                        @PathVariable("id") UUID itemId ,
                        Model model){
        logger.error("Form : Authentication received! Cookie : "+cookieValue );
        return super.getForm(cookieValue,new PrestationDTO(),new Prestation(),itemId,PrestationDTO.class,"GET",model);
    }

    @GetMapping(value = "Prestation/suppr/{id}")
    public String FormSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                             @PathVariable("id") UUID itemId,
                             Model model) {
        return super.delete(cookieValue,new PrestationDTO(),itemId,model);
    }

}