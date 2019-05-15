package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.*;
import com.unamur.portaildesartistes.webclient.dataForm.DocArtiste;
import com.unamur.portaildesartistes.webclient.dataForm.Secteur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

// Date de la DocArtiste (si plusieurs jours compléter plusieurs lignes)
// Nature de la DocArtiste
// Nom du donneur d’ordre ou numéro *BCE (*Banque-carrefour des entreprises)
// Signature du donneur d’ordre
// Indemnités prévues

@Controller
public class DocArtisteControler extends Controler<DocArtisteDTO, Class< DocArtisteDTO >, DocArtiste> {
    private static final Logger logger = LoggerFactory.getLogger(DocArtisteControler.class);

    @Autowired
    private FormulaireControler formCtrl;

    @GetMapping(value = "/DocArtiste/creer/{id}")
    public String docArtCreateDef( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable("id") UUID itemId
            ,@ModelAttribute("form") final DocArtiste formDocArt
            ,Model model){
        FormulaireDTO formDTO = formCtrl.formGetById(cookieValue,itemId);
        List<UUID> lActIdDTO = formDTO.getActivitesId();
        Collection<SecteurDTO> lSectDTO = formDTO.getSecteurActivites();

        model.addAttribute("form",formDocArt);
        model.addAttribute("activites", lActIdDTO );
        model.addAttribute("secteurs", lSectDTO );
        //String fragment = sectCtrl.listSecteurActiviteByFormulaire( cookieValue,formId , model );
        return "DocArtiste/put.html";
    }

    @GetMapping(value = "/DocArtiste/modif/{id}")
    public String docArtModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                             @PathVariable("id") UUID itemId ,
                             @ModelAttribute("form") final DocArtiste formDocArt,
                             Model model){
        logger.error("DocArtiste/modif : Authentication received! Cookie : "+cookieValue );
        model.addAttribute("form",formDocArt==null?new DocArtiste():formDocArt);

        return super.getForm(cookieValue,new DocArtisteDTO(),new DocArtiste(),itemId,DocArtisteDTO.class,"POST",model);
    }

    @PostMapping(value = "/DocArtiste")
    public UUID docArtPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final DocArtiste formDocArt
            ,Model model){
        logger.error("Form(post) "+method+" : Authentication received! Cookie : "+cookieValue );
        try {
            DocArtisteDTO docArtDTO = formDocArt.getDTO();
            docArtDTO.setCitoyenId( formCtrl.getMyId(cookieValue) );
            return super.postForm(cookieValue,docArtDTO,method);
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            model.addAttribute("form",formDocArt);
            throw e;
        }
    }

    @GetMapping(value = "/DocArtiste")//initialisation du login
    public String docArtList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        logger.error("Form List : Authentication received! Cookie : "+cookieValue );

        return super.list(cookieValue,new DocArtisteDTO(),DocArtisteDTO.class,model);
    }

    @GetMapping(value = "/DocArtiste/{id}")//initialisation du login
    public String Form( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                        @PathVariable("id") UUID itemId ,
                        Model model){
        logger.error("Form : Authentication received! Cookie : "+cookieValue );
        return super.getForm(cookieValue,new DocArtisteDTO(),new DocArtiste(),itemId,DocArtisteDTO.class,"GET",model);
    }

    @GetMapping(value = "DocArtiste/suppr/{id}")
    public String docArtSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                             @PathVariable("id") UUID itemId,
                             Model model) {
        return super.delete(cookieValue,new DocArtisteDTO(),itemId,model);
    }

}