package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.TraitementDTO;
import com.unamur.portaildesartistes.DTO.FormulaireDTO;
import com.unamur.portaildesartistes.DTO.SecteurDTO;
import com.unamur.portaildesartistes.webclient.RestTemplateHelper;
import com.unamur.portaildesartistes.webclient.dataForm.Formulaire;
import com.unamur.portaildesartistes.webclient.dataForm.Traitement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.management.ServiceNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Controller
public class TraitementControler extends Controler<TraitementDTO, Class< TraitementDTO >, Traitement> {
    private static final Logger logger = LoggerFactory.getLogger(TraitementControler.class);

    @Autowired
    RestTemplateHelper restTemplateHelper;

    @Autowired
    private FormulaireControler formCtrl;

    @GetMapping(value = "/Traitement/creer/{id}")
    public String trtCreateDef( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable("id") UUID formId
            ,@ModelAttribute("form") final Traitement formTrt
            ,Model model){

        FormulaireDTO formDTO = formCtrl.formGetById(cookieValue,formId);
        List<UUID> lActIdDTO = formDTO.getActivitesId();
        Collection<SecteurDTO> lSectDTO = formDTO.getSecteurActivites();

        model.addAttribute("form",formTrt);
        model.addAttribute("activites", lActIdDTO );
        model.addAttribute("secteurs", lSectDTO );

        return "Traitement/put.html";
    }

    @GetMapping(value = "/Traitement/modif/{id}")
    public String trtModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                             @PathVariable("id") UUID trtId ,
                             @ModelAttribute("form") final Traitement formTrt,
                             Model model){

        model.addAttribute("form",formTrt==null?new Traitement():formTrt);

        return super.getForm(cookieValue,new TraitementDTO(),new Traitement(),trtId,TraitementDTO.class,"POST",model);
    }

    @PostMapping(value = "/Traitement")
    public UUID trtPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final Traitement formTrt
            ,Model model){

        try {
            TraitementDTO formTrtDTO = formTrt.getDTO();
            formTrtDTO.setCitoyenPrestId( formCtrl.getMyId(cookieValue) );
            return super.postForm(cookieValue,formTrtDTO,method);
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            model.addAttribute("form",formTrt);
            throw e;
        }
    }

    @GetMapping(value = "/Traitement")
    public String trtList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){

        try {
            List<Formulaire> lForm = new ArrayList<>();
            List<FormulaireDTO> lFormDTO = formCtrl.listByLang(cookieValue ,"FR");
            for( FormulaireDTO formDTO : lFormDTO ){
                Formulaire formForm = new Formulaire();
                formForm.setFromDTO(formDTO);
                lForm.add(formForm);
            }
            model.addAttribute("form"   , lForm );

            List<TraitementDTO> lTrtDTO = getTrtListByLang(cookieValue,"FR");
            model.addAttribute("trt"    , lTrtDTO );
            model.addAttribute("trtDone", lTrtDTO );

        }catch(Exception e){
            model.addAttribute("Err" , e.getMessage() );
             //return "/login.html";
        }
        return "Traitement/list.html";
    }

    private List<TraitementDTO> getTrtListByLang(String cookieValue,String lang) throws ServiceNotFoundException{
        HttpHeaders headers = initHeadersRest(cookieValue);
        return restTemplateHelper.getForList(TraitementDTO.class,configurationService.getUrl()+"/gestionTraitement/lang/"+lang,headers );
    }


    @GetMapping(value = "/Traitement/{id}")
    public String Form( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                        @PathVariable("id") UUID itemId ,
                        Model model){
        return super.getForm(cookieValue,new TraitementDTO(),new Traitement(),itemId,TraitementDTO.class,"GET",model);
    }

    @GetMapping(value = "/Traitement/suppr/{id}")
    public String trtSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                             @PathVariable("id") UUID itemId,
                             Model model) {
        return super.delete(cookieValue,new TraitementDTO(),itemId,model);
    }

}