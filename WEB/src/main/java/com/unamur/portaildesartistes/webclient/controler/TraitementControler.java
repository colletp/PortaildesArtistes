package com.unamur.portaildesartistes.webclient.controler;

import com.unamur.portaildesartistes.DTO.*;
import com.unamur.portaildesartistes.webclient.RestTemplateHelper;
import com.unamur.portaildesartistes.webclient.dataForm.Formulaire;
import com.unamur.portaildesartistes.webclient.dataForm.Traitement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class TraitementControler extends Controler<TraitementDTO, Class< TraitementDTO >, Traitement> {
    private static final Logger logger = LoggerFactory.getLogger(TraitementControler.class);

    @Autowired
    RestTemplateHelper restTemplateHelper;

    @Autowired
    private FormulaireControler formCtrl;
    @Autowired
    private DocArtisteControler docArtCtrl;

    @Autowired
    private CitoyenControler citCtrl;

    @GetMapping(value = "/Traitement/form/{id}")
    public String trtCreateByForm( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable("id") UUID formId
            ,@ModelAttribute("trt") final Traitement formTrt
            ,Model model){
        try{
            FormulaireDTO formDTO = formCtrl.formGetById(cookieValue,formId , model);
            formCtrl.loadForm( cookieValue, new Formulaire(formDTO) ,"GET",model);
            model.addAttribute("trt",formTrt);
            return "Traitement/putForm.html";
        }catch( Exception e ){
            return "/login.html";
        }
    }

    @GetMapping(value = "/Traitement/prest/{id}")
    public String trtCreateByDocArtiste( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable("id") UUID docArtId
            ,@ModelAttribute("trt") final Traitement formTrt
            ,Model model){
        try{
            DocArtisteDTO docArtDTO = docArtCtrl.getObj( cookieValue,docArtId, new DocArtisteDTO(), DocArtisteDTO.class ,model );
            docArtCtrl.loadDoc(cookieValue, docArtDTO ,"GET",model);
            model.addAttribute("trt",formTrt);
            return "Traitement/putPrest.html";
        }catch( Exception e ){
            return "/login.html";
        }
    }

    /*
    @GetMapping(value = "/Traitement/modif/{id}")
    public String trtModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                             @PathVariable("id") UUID itemId ,
                             @ModelAttribute("form") final Traitement formTrt,
                             Model model){

        TraitementDTO trtDTO = getObj(cookieValue,itemId,new TraitementDTO(),TraitementDTO.class);
        CitoyenDTO citDTO = citCtrl.getCitoyen( cookieValue,trtDTO.getForm().getCitoyenId() );
        model.addAttribute("form", trtDTO );
        model.addAttribute("citoyen", citDTO );

        return "Traitement/post.html";
    }
    */

    @PostMapping(value = "/Traitement")
    public String trtPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final Traitement formTrt
            ,Model model){
        try{
            model.addAttribute("form",super.postForm(cookieValue,formTrt.getDTO(),method,model));
            return "Traitement/"+(method.isEmpty()?"post":method)+".html";
        }catch( Exception e ){
            return "/login.html";
        }
    }

    @GetMapping(value = "/Traitement")
    public String trtList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){

        try {
            model.addAttribute("formATrt", formCtrl.listATraiterByLang(cookieValue ,"FR",model) );
            model.addAttribute("formEnCours", formCtrl.listEnCoursByLang(cookieValue ,"FR",model) );
            model.addAttribute("formFini",formCtrl.listFiniByLang(cookieValue ,"FR",model) );
            model.addAttribute("DocArtPrest",docArtCtrl.listByLang(cookieValue,"FR",model) );
            return "Traitement/list.html";
        }catch(Exception e){
            model.addAttribute("Err" , e.getMessage() );
            return "/login.html";
        }
    }

    @GetMapping(value = "/Traitement/{id}")
    public String Form( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                        @PathVariable("id") UUID itemId ,
                        Model model){
        try{
            TraitementDTO trtDTO = getObj(cookieValue,itemId,new TraitementDTO(),TraitementDTO.class,model);
            model.addAttribute("form", trtDTO );
            model.addAttribute("citoyen", citCtrl.getById( cookieValue,trtDTO.getForm().getCitoyenId() ,model ) );
            return "Traitement/get.html";
        }catch( Exception e ){
            return "/login.html";
        }
    }

    @GetMapping(value = "/Traitement/suppr/{id}")
    public String trtSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                             @PathVariable("id") UUID itemId,
                             Model model) {
        try{
            super.delete(cookieValue,new TraitementDTO(),itemId,model);
            return "Traitement.list.html";
        }catch( Exception e ){
            return "/login.html";
        }
    }
}