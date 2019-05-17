package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.TraitementDTO;
import com.unamur.portaildesartistes.DTO.FormulaireDTO;
import com.unamur.portaildesartistes.DTO.SecteurDTO;
import com.unamur.portaildesartistes.DTO.TraitementDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Traitement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Controller
public class TraitementControler extends Controler<TraitementDTO, Class< TraitementDTO >, Traitement> {
    private static final Logger logger = LoggerFactory.getLogger(TraitementControler.class);

    @Autowired
    private FormulaireControler trtCtrl;

    @GetMapping(value = "/Traitement/creer/{id}")
    public String trtCreateDef( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable("id") UUID itemId
            ,@ModelAttribute("form") final Traitement formTrt
            ,Model model){
        FormulaireDTO formDTO = trtCtrl.formGetById(cookieValue,itemId);
        List<UUID> lActIdDTO = formDTO.getActivitesId();
        Collection<SecteurDTO> lSectDTO = formDTO.getSecteurActivites();

        model.addAttribute("form",formTrt);
        model.addAttribute("activites", lActIdDTO );
        model.addAttribute("secteurs", lSectDTO );
        //String fragment = sectCtrl.listSecteurActiviteByFormulaire( cookieValue,formId , model );
        return "Traitement/put.html";
    }

    @GetMapping(value = "/Traitement/modif/{id}")
    public String trtModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                             @PathVariable("id") UUID itemId ,
                             @ModelAttribute("form") final Traitement formTrt,
                             Model model){
        logger.error("Traitement/modif : Authentication received! Cookie : "+cookieValue );
        model.addAttribute("form",formTrt==null?new Traitement():formTrt);

        return super.getForm(cookieValue,new TraitementDTO(),new Traitement(),itemId,TraitementDTO.class,"POST",model);
    }

    @PostMapping(value = "/Traitement")
    public UUID trtPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final Traitement formTrt
            ,Model model){
        logger.error("Form(post) "+method+" : Authentication received! Cookie : "+cookieValue );
        try {
            TraitementDTO formTrtDTO = formTrt.getDTO();
            formTrtDTO.setCitoyenPrestId( trtCtrl.getMyId(cookieValue) );
            return super.postForm(cookieValue,formTrtDTO,method);
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            model.addAttribute("form",formTrt);
            throw e;
        }
    }

    @GetMapping(value = "/Traitement")//initialisation du login
    public String trtList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        logger.error("Form List : Authentication received! Cookie : "+cookieValue );

        return super.list(cookieValue,new TraitementDTO(),TraitementDTO.class,model);
    }

    @GetMapping(value = "/Traitement/{id}")//initialisation du login
    public String Form( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                        @PathVariable("id") UUID itemId ,
                        Model model){
        logger.error("Form : Authentication received! Cookie : "+cookieValue );
        return super.getForm(cookieValue,new TraitementDTO(),new Traitement(),itemId,TraitementDTO.class,"GET",model);
    }

    @GetMapping(value = "Traitement/suppr/{id}")
    public String trtSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                             @PathVariable("id") UUID itemId,
                             Model model) {
        return super.delete(cookieValue,new TraitementDTO(),itemId,model);
    }

}