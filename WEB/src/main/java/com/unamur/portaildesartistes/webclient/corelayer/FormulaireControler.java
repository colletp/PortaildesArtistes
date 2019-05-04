package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
import com.unamur.portaildesartistes.DTO.FormulaireDTO;
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
    private ActiviteControler actCtrl;

    @GetMapping(value = "/Formulaire/creer")
    public String FormCreate( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        model.addAttribute("form",new Formulaire());
        List<ActiviteDTO> activites = actCtrl.listObj( cookieValue, new ActiviteDTO(),ActiviteDTO.class );
        model.addAttribute("act", activites );

        return "Formulaire/put.html";
    }

    @GetMapping(value = "/Formulaire/modif/{id}")
    public String FormModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                                @PathVariable("id") UUID itemId ,
                                Model model){
        logger.error("Formulaire/modif : Authentication received! Cookie : "+cookieValue );
        return super.getForm(cookieValue,new FormulaireDTO(),itemId,FormulaireDTO.class,"POST",model);
    }

    @PostMapping(value = "/Formulaire")
    public String FormPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model){
        logger.error("Form(post) "+method+" : Authentication received! Cookie : "+cookieValue );
        //usrForm.setPassword(WebSecurityConfig.encoder().encode( usrForm.getPassword() ) );
        try {
            FormulaireDTO formDTO = formForm.getDTO();
            List<ActiviteDTO> listAct=new ArrayList<>();
            for(String actId : formForm.getActivitesId() ){
                listAct.add(actCtrl.getObj(cookieValue,UUID.fromString(actId),new ActiviteDTO(),ActiviteDTO.class ));
            }
            formDTO.setActivites(listAct);
        }catch(IllegalArgumentException e){
            return "err";
        }catch(ParseException e){
            return "err";
        }
        return super.postForm(cookieValue,formForm,method,model);
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