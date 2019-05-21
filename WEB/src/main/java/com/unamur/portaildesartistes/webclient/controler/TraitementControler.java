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

import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private GestionnaireControler gestCtrl;
    @Autowired
    private UtilisateurControler usrCtrl;
    @Autowired
    private AdresseControler adrsCtrl;

    @GetMapping(value = "/Traitement/form/{formId}")
    public String trtCreateByForm( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable("formId") String formId
            ,@ModelAttribute("trt") final Traitement formTrt
            ,Model model){
        try{
            usrCtrl.setRoles( cookieValue, model );
            FormulaireDTO formDTO = formCtrl.formGetById(cookieValue,UUID.fromString(formId) , model);
            formCtrl.loadForm( cookieValue, new Formulaire(formDTO) ,"GET",model);
            model.addAttribute("typeTrt","Form");
            model.addAttribute("formId",formId.toString());
            model.addAttribute("trt",formTrt);
            return "Traitement/put.html";
        }catch( Exception e ){
            return "/login.html";
        }
    }

    @GetMapping(value = "/Traitement/prest/{docArtId}")
    public String trtCreateByDocArtiste( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable("docArtId") UUID docArtId
            ,@ModelAttribute("trt") final Traitement formTrt
            ,Model model){
        try{
            usrCtrl.setRoles( cookieValue, model );
            DocArtisteDTO docArtDTO = docArtCtrl.getObj( cookieValue,docArtId, new DocArtisteDTO(), DocArtisteDTO.class ,model );
            docArtCtrl.loadDoc(cookieValue, docArtDTO ,"GET",model);
            model.addAttribute("typeTrt","Prest");
            model.addAttribute("trt",formTrt);
            return "Traitement/put.html";
        }catch( Exception e ){
            return "/login.html";
        }
    }

    @PostMapping(value = "/Traitement", params={"submit"})
    public String trtPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final Traitement formTrt
            ,@ModelAttribute("typeTrt") final String typeTrt
            ,@ModelAttribute("submit") final String submit
            ,@ModelAttribute("formId") final String formId
            ,Model model){
        try{
            usrCtrl.setRoles( cookieValue, model );
            UtilisateurDTO moi = usrCtrl.getMoi(cookieValue,model);
            GestionnaireDTO gestDTO = gestCtrl.getObj( cookieValue, moi.getCitoyen().getGest().getId() ,new GestionnaireDTO(),GestionnaireDTO.class,model );
            gestDTO.setCitoyen( citCtrl.getObj( cookieValue , moi.getId(), new CitoyenDTO(),CitoyenDTO.class, model ) );
            gestDTO.getCitoyen().setResideAdr( adrsCtrl.getObj(cookieValue,gestDTO.getCitoyen().getReside(),new AdresseDTO(),AdresseDTO.class,model ) );

            formTrt.setGestId( gestDTO.getId().toString() );
            UUID trtId = super.postForm(cookieValue,formTrt.getDTO(),method,model);
            formTrt.setFromDTO( super.getObj( cookieValue,trtId, formTrt.getDTO(),TraitementDTO.class,model ) );

            FormulaireDTO formDTO = formCtrl.formGetById(cookieValue, UUID.fromString( formId ), model);
            formCtrl.loadForm( cookieValue, new Formulaire(formDTO) ,"GET",model);

            model.addAttribute("_method",method);
            model.addAttribute("typeTrt",typeTrt);
            model.addAttribute("formId",formId);
            model.addAttribute("trt",formTrt);

            model.addAttribute("Err",submit);
            model.addAttribute("Msg",submit);

            return "Traitement/get.html";
        }catch(IllegalArgumentException e){
            model.addAttribute("_method",method);
            model.addAttribute("typeTrt",typeTrt);
            model.addAttribute("formId",formId);
            model.addAttribute("trt",formTrt);

            model.addAttribute("Err",e.getMessage());
            return "Traitement/"+(method.isEmpty()?"post":method)+".html";
        }catch( Exception e ){
            logger.error(e.getMessage());
            e.printStackTrace();
            return "/login.html";
        }
    }

    @GetMapping(value = "/Traitement/form/lang/{lang}")
    public String trtListForm( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable("lang") String lang
            ,Model model){

        try {
            usrCtrl.setRoles( cookieValue, model );
            UtilisateurDTO moi = usrCtrl.getMoi(cookieValue,model);

            List<String> lLang = new ArrayList<>();
            Boolean bLangOk=false;
            for(RoleDTO r : moi.getAuthorities() ){
                lLang.add(r.getLang());
                if(r.getLang().equals(lang)){
                    bLangOk=true;
                }
            }
            if( !bLangOk ) {
                model.addAttribute("Err", "Langue non disponible pour ce gestionnaire");
                return "choixTraitement.html";
            }

            model.addAttribute("formATrt", formCtrl.listATraiterByLang(cookieValue ,lang,model) );
            model.addAttribute("formEnCours", formCtrl.listEnCoursByLang(cookieValue ,lang,model) );
            model.addAttribute("formFini",formCtrl.listFiniByLang(cookieValue ,lang,model) );
            return "Traitement/listForm.html";
        }catch(Exception e){
            model.addAttribute("Err" , e.getMessage() );
            return "login.html";
        }
    }

    @GetMapping(value = "/Traitement")
    public String trtChoix( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        try {
            usrCtrl.setRoles( cookieValue, model );
        }catch(Exception e){
            model.addAttribute("Err",e.getMessage() );
            return "login.html";
        }
        return "choixTraitement.html";
    }

    @GetMapping(value = "/Traitement/prest")
    public String trtListPrest( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){

        try {
            usrCtrl.setRoles( cookieValue, model );
            UtilisateurDTO moi = usrCtrl.getMoi(cookieValue,model);
            List<String> lLang = new ArrayList<>();
            for(RoleDTO r : moi.getAuthorities() ){
                lLang.add(r.getLang());
            }

            //model.addAttribute("DocArtPrest",docArtCtrl.listByLang(cookieValue,lang,model) );
            return "Traitement/listForm.html";
        }catch(Exception e){
            model.addAttribute("Err" , e.getMessage() );
            return "login.html";
        }
    }

    @GetMapping(value = "/Traitement/{trtId}")
    public String Form( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                        @PathVariable("trtId") UUID trtId ,
                        Model model){
        try{
            usrCtrl.setRoles( cookieValue, model );
            TraitementDTO trtDTO = getObj(cookieValue,trtId,new TraitementDTO(),TraitementDTO.class,model);
            model.addAttribute("form", trtDTO );
            model.addAttribute("citoyen",citCtrl.getObj( cookieValue , trtDTO.getForm().getCitoyenId(), new CitoyenDTO(),CitoyenDTO.class, model ) );
            return "Traitement/get.html";
        }catch( Exception e ){
            model.addAttribute("Err" , e.getMessage() );
            return "login.html";
        }
    }

    @GetMapping(value = "/Traitement/suppr/{id}")
    public String trtSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                             @PathVariable("id") UUID itemId,
                             Model model) {
        try{
            usrCtrl.setRoles( cookieValue, model );
            super.delete(cookieValue,new TraitementDTO(),itemId,model);
            return "Traitement/list.html";
        }catch( Exception e ){
            model.addAttribute("Err" , e.getMessage() );
            return "login.html";
        }
    }
}