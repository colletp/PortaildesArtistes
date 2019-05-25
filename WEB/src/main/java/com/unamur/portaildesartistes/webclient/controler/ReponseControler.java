package com.unamur.portaildesartistes.webclient.controler;

import com.unamur.portaildesartistes.DTO.*;
import com.unamur.portaildesartistes.webclient.dataForm.DocArtiste;
import com.unamur.portaildesartistes.webclient.dataForm.Reponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class ReponseControler extends Controler<ReponseDTO, Class< ReponseDTO >, Reponse> {
    private static final Logger logger = LoggerFactory.getLogger(ReponseControler.class);

    @Autowired
    UtilisateurControler usrCtrl;
    @Autowired
    CitoyenControler citCtrl;
    @Autowired
    SecteurControler sectCtrl;
    @Autowired
    TraitementControler trtCtrl;
    @Autowired
    FormulaireControler formCtrl;
    @Autowired
    DocArtisteControler docArtCtrl;

    @GetMapping(value = "/Reponse/creer/{id}")
    public String docArtCreateDef( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable("id") UUID itemId
            ,@ModelAttribute("rep") final Reponse rep
            ,Model model){

        try{
            usrCtrl.setRoles(cookieValue, model);

            model.addAttribute("rep",rep);
            return "Reponse/put.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @GetMapping(value = "/Reponse/modif/{id}")
    public String docArtModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                             @PathVariable("id") UUID itemId ,
                             Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("form",super.getObj(cookieValue,itemId,new ReponseDTO(),ReponseDTO.class,model));
            return "Reponse/post.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }


    @PostMapping(value = "/Reponse", params={"saveDraft"})
    public String docArtDraftPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("rep") final Reponse rep
            ,Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("form",super.postForm(cookieValue, rep.getDTO() ,method,model));
            return "Reponse/get.html";
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            return "Reponse/"+(method.isEmpty()?"post":method)+".html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @PostMapping(value = "/Reponse", params={"submit"})
    public String docArtPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("rep") final Reponse rep
            ,@ModelAttribute("docCarte") final DocArtiste docCarte
            ,@ModelAttribute("docVisa") final DocArtiste docVisa
            ,@ModelAttribute("repPositive") final String repPositive
            ,@ModelAttribute("docCarteCB") final String docCarteCB
            ,@ModelAttribute("docVisaCB") final String docVisaCB
            ,@ModelAttribute("body") final String reponse
            ,Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            rep.setReponse(reponse);
            rep.setReponsePositive(repPositive);

            //formCtrl.getObj(cookieValue,);

            UUID repId = super.postForm(cookieValue, rep.getDTO() ,method,model);
            model.addAttribute("repId",repId);
            if( repPositive.equals("1") ){
                if( docCarteCB.equals("1") )docArtCtrl.postForm(cookieValue, docCarte ,method,model);
                if( docVisaCB.equals("1")  )docArtCtrl.postForm(cookieValue, docVisa ,method,model);

            }

            return "Reponse/get.html";
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            return "Reponse/"+(method.isEmpty()?"post":method)+".html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @GetMapping(value = "/Reponse")
    public String docArtList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("form",super.list(cookieValue,new ReponseDTO(),ReponseDTO.class,model));
            return "Reponse/list.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @GetMapping(value = "/Reponse/{id}")
    public String getReponse( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                        @PathVariable("id") UUID itemId ,
                        Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("form",super.getObj(cookieValue,itemId,new ReponseDTO(),ReponseDTO.class,model));
            return "Reponse/get.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @GetMapping(value = "Reponse/suppr/{id}")
    public String docArtSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                             @PathVariable("id") UUID itemId,
                             Model model) {
        try{
            usrCtrl.setRoles(cookieValue, model);
            super.delete(cookieValue,new ReponseDTO(),itemId,model);
            return "Reponse/list.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

}