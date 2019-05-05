package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
import com.unamur.portaildesartistes.DTO.FormulaireDTO;
import com.unamur.portaildesartistes.DTO.SecteurDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Formulaire;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class FormulaireControler extends Controler< FormulaireDTO , Class< FormulaireDTO >, Formulaire > {
    private static final Logger logger = LoggerFactory.getLogger(FormulaireControler.class);

    @Autowired
    private SecteurControler sectCtrl;

    @GetMapping(value = "/Formulaire/creer")
    public String FormCreate( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model){
        model.addAttribute("form",formForm==null?new Formulaire():formForm);
        String fragment = sectCtrl.listSecteurActivite( cookieValue , model );
        return "Formulaire/put.html";
    }

    @GetMapping(value = "/Formulaire/modif/{id}")
    public String FormModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                                @PathVariable("id") UUID itemId ,
                                @ModelAttribute("form") final Formulaire formForm,
                                Model model){
        logger.error("Formulaire/modif : Authentication received! Cookie : "+cookieValue );
        model.addAttribute("form",formForm==null?new Formulaire():formForm);
        return super.getForm(cookieValue,new FormulaireDTO(),itemId,FormulaireDTO.class,"POST",model);
    }

    @PostMapping(value = "/Formulaire")
    public String FormPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model){
        logger.error("Form(post) "+method+" : Authentication received! Cookie : "+cookieValue );
        try {
            FormulaireDTO formDTO = formForm.getDTO();
            /*List<ActiviteDTO> listAct=new ArrayList<>();
            for(String actId : formForm.getActivitesId() )
                listAct.add(actCtrl.getObj(cookieValue,UUID.fromString(actId),new ActiviteDTO(),ActiviteDTO.class ));
            formDTO.setActivites(listAct);*/
            return super.postForm(cookieValue,formDTO,method,model);
        }catch(IllegalArgumentException e){
            String fragment = sectCtrl.listSecteurActivite( cookieValue , model );
            model.addAttribute("Err",e.getMessage());
            model.addAttribute("form",formForm);
            return "/Formulaire/"+method+".html";
        }catch(ParseException e){
            String fragment = sectCtrl.listSecteurActivite( cookieValue , model );
            model.addAttribute("Err",e.getMessage());
            model.addAttribute("form",formForm);
            return "/Formulaire/"+method+".html";
        }
    }

    @GetMapping(value = "/Formulaire")//initialisation du login
    public String FormList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
                                ,Model model){
        logger.error("Form List : Authentication received! Cookie : "+cookieValue );

        return super.list(cookieValue,new FormulaireDTO(),FormulaireDTO.class,model);
    }

    @GetMapping(value = "/Formulaire/{id}")//initialisation du login
    public String Form( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") UUID itemId ,
                           Model model){
        logger.error("Form : Authentication received! Cookie : "+cookieValue );
        return super.getForm(cookieValue,new FormulaireDTO(),itemId,FormulaireDTO.class,"GET",model);
    }

    @GetMapping(value = "Formulaire/suppr/{id}")
    public String FormSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        return super.delete(cookieValue,new FormulaireDTO(),itemId,model);
    }
}