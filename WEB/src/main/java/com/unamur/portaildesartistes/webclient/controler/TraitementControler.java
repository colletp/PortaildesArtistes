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
    private FormulaireControler formCtrl;
    @Autowired
    private DocArtisteControler docArtCtrl;
    @Autowired
    private CitoyenControler citCtrl;
    @Autowired
    private UtilisateurControler usrCtrl;

    @GetMapping(value = "/Traitement/form/{formId}")
    public String trtCreateByForm( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable("formId") String formId
            ,@ModelAttribute("trt") final Traitement formTrt
            ,Model model){
        try{
            usrCtrl.setRoles( cookieValue, model );
            FormulaireDTO formDTO = formCtrl.formGetById(cookieValue,UUID.fromString(formId) , model);
            formCtrl.loadForm( cookieValue, new Formulaire(formDTO) ,"GET",model);
            //model.addAttribute("formId",formId.toString());
            formTrt.setFormId( formId );
            model.addAttribute("trt",formTrt);
            model.addAttribute("typeTrt","Form");
            return "Traitement/put.html";
        }catch( Exception e ){
            return "login.html";
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
            ,@ModelAttribute("typeTrt") final String typeTrt
            ,@ModelAttribute("trt") final Traitement formTrt
            ,@ModelAttribute("submit") final String submit
            //,@ModelAttribute("formId") final String formId
            ,Model model){
        try{
            usrCtrl.setRoles( cookieValue, model );

            UtilisateurDTO moi = usrCtrl.getMoi(cookieValue,model);
            //GestionnaireDTO gestDTO = gestCtrl.getObj( cookieValue, moi.getCitoyen().getGest().getId() ,new GestionnaireDTO(),GestionnaireDTO.class,model );
            //si je suis un gestionnaire
            if(moi.getCitoyen().getGest()==null)throw new IllegalArgumentException("Opération illégale, il faut être gestionnaire");
            formTrt.setGestId(moi.getCitoyen().getGest().getId().toString());
            //enregistrement du traitement avec l'ID du gestionnaire
            UUID trtId = super.postForm(cookieValue, formTrt.getDTO(), method, model);
            //retrouver les éléments insérés en DB (date du traitement, ...)
            formTrt.setFromDTO( super.getObj( cookieValue,trtId, formTrt.getDTO(),TraitementDTO.class,model ) );

            FormulaireDTO formDTO = formCtrl.formGetById( cookieValue, UUID.fromString( formTrt.getFormId() ) , model);
            formCtrl.loadForm( cookieValue, new Formulaire(formDTO) ,"GET",model);

			model.addAttribute("Msg",submit);
            switch(submit){
				case "renvoyerForm":
					//traitement remettant le formulaire consultable par le citoyen
					switch( formCtrl.invalidate( cookieValue, UUID.fromString( formTrt.getFormId() ) , model) ){
                        case 1: model.addAttribute("Msg","Formulaire invalidé");
                            break;
                        case -100: model.addAttribute("Err","Le formulaire ne peut plus être invalidé, au moins un document a déjà été émis.");
                            break;
                        case -101: model.addAttribute("Err","Ceci n'est pas votre formulaire");
                            break;
                        case -102: model.addAttribute("Err","Vous n'êtes pas un gestionnaire pouvant gérer un formulaire");
                            break;
                        default:
                            model.addAttribute("Err","Erreur inconue");
                    }
                    return "Traitement/list.html";
				case "envoiReponse":
					//redirection vers création réponse
					model.addAttribute("trtId",trtId);
					return "Reponse/put.html";
				case "sauvCommentaire"://rien de plus, tout a déjà été fait plus haut (sauvegarde du traitement)
				default:
					model.addAttribute("_method",method);
					model.addAttribute("typeTrt",typeTrt);
					model.addAttribute("trt",formTrt);
			}
            return "Traitement/get.html";
        }catch(IllegalArgumentException e){
            model.addAttribute("_method",method);
            model.addAttribute("typeTrt",typeTrt);
            model.addAttribute("trt",formTrt);

            model.addAttribute("Err",e.getMessage());
            return "Traitement/"+(method.isEmpty()?"post":method)+".html";
        }catch( Exception e ){
            logger.error(e.getMessage());
            e.printStackTrace();
            return "login.html";
        }
    }

    @GetMapping(value = "/Traitement/form/lang/{lang}")
    public String trtListForm( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable("lang") String lang
            ,Model model){

        try {
            usrCtrl.setRoles( cookieValue, model );
            UtilisateurDTO moi = usrCtrl.getMoi(cookieValue,model);

            Boolean bLangOk=false;
            for(RoleDTO r : moi.getAuthorities() ){
                if(r.getLang().equals(lang))
                    bLangOk=true;
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
            model.addAttribute("trt", trtDTO );
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