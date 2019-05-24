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

    @PostMapping(value = "/Reponse", params={"addCarte"})
    public String docArtAddCard( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("rep") final Reponse rep
            ,Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
//sauve la réponse sans l'envoyer
            model.addAttribute("repId",super.postForm(cookieValue, rep.getDTO() ,method,model));
            TraitementDTO trtDTO = trtCtrl.getObj(cookieValue,UUID.fromString(rep.getTrtId()),new TraitementDTO(),TraitementDTO.class,model);
            FormulaireDTO formDTO= formCtrl.getObj(cookieValue,trtDTO.getFormId(),new FormulaireDTO(),FormulaireDTO.class,model);
            model.addAttribute("citoyen",citCtrl.getObj(cookieValue,formDTO.getCitoyenId(),new CitoyenDTO(),CitoyenDTO.class,model) );
            model.addAttribute("form", formDTO );
            model.addAttribute("docCarte",new DocArtisteDTO());
            model.addAttribute("docVisa",new DocArtisteDTO());
            //model.addAttribute("secteurs", formDTO );
            //super.postForm(cookieValue, rep.getDTO() ,method,model);
            return "Reponse/"+(method.isEmpty()?"post":method)+".html";
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            return "Reponse/"+(method.isEmpty()?"post":method)+".html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }
    @PostMapping(value = "/Reponse", params={"addVisa"})
    public String docArtAddVisa( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("rep") final Reponse rep
            ,Model model){
            try{
                usrCtrl.setRoles(cookieValue, model);
                model.addAttribute("docVisa",new DocArtiste() );
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
    @PostMapping(value = "/Reponse", params={"removeCarte"})
    public String docArtDelCard( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
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
    @PostMapping(value = "/Reponse", params={"removeVisa"})
    public String docArtDelVisa( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
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