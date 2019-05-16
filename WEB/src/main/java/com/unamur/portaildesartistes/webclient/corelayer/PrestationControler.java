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
    private static final Logger logger = LoggerFactory.getLogger(PrestationControler.class);

    @Autowired
    private DocArtisteControler docCtrl;

    @Autowired
    private SecteurControler sectCtrl;

    @GetMapping(value = "/Prestation/creer")
    public String prestCreateDef( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("form") final Prestation formPrest
            ,Model model){

        model.addAttribute("form",formPrest);
        model.addAttribute("activites",new ArrayList<String>());

        UUID usrId = docCtrl.getMyId(cookieValue);
        //UUID formId =


        UUID docId = null; //TODO prendre ici l'identifiant du formulaire en fonction de l'utilisateur

        //String fragment = sectCtrl.listSecteurActiviteByDocument( cookieValue,docId , model );
        return "Prestation/put.html";
    }

    @GetMapping(value = "/Prestation/modif/{id}")
    public String prestModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                             @PathVariable("id") String itemId ,
                             @ModelAttribute("form") final Prestation formPrest,
                             Model model){
        logger.error("Prestation/modif : Authentication received! Cookie : "+cookieValue );
        model.addAttribute("form",formPrest==null?new Prestation():formPrest);
        return super.getForm(cookieValue,new PrestationDTO(),new Prestation(),UUID.fromString(itemId),PrestationDTO.class,"POST",model);
    }

    @PostMapping(value = "/Prestation")
    public UUID prestPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
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
            throw e;
        }

    }

    @GetMapping(value = "/Prestation")
    public String prestList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        logger.error("Form List : Authentication received! Cookie : "+cookieValue );

        return super.list(cookieValue,new PrestationDTO(),PrestationDTO.class,model);
    }

    @GetMapping(value = "/Prestation/{id}")
    public String Form( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                        @PathVariable("id") String itemId ,
                        Model model){
        logger.error("Form : Authentication received! Cookie : "+cookieValue );
        return super.getForm(cookieValue,new PrestationDTO(),new Prestation(),UUID.fromString(itemId),PrestationDTO.class,"GET",model);
    }

    @GetMapping(value = "Prestation/suppr/{id}")
    public String prestSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                             @PathVariable("id") String itemId,
                             Model model) {
        return super.delete(cookieValue,new PrestationDTO(),UUID.fromString(itemId),model);
    }

}