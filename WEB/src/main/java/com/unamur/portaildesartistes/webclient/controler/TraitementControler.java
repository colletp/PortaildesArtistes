package com.unamur.portaildesartistes.webclient.controler;

import com.unamur.portaildesartistes.DTO.*;
import com.unamur.portaildesartistes.webclient.dataForm.DocArtiste;
import com.unamur.portaildesartistes.webclient.dataForm.Formulaire;
import com.unamur.portaildesartistes.webclient.dataForm.Traitement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    private FormulaireControler formCtrl;
    @Autowired
    private DocArtisteControler docArtCtrl;
    @Autowired
    private CitoyenControler citCtrl;
    @Autowired
    private UtilisateurControler usrCtrl;
    @Autowired
    private GestionnaireControler gestCtrl;

    @GetMapping(value = "/Traitement/form/{formId}")
    public String trtCreateByForm( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable("formId") final String formId
            ,Model model){
        Formulaire formForm = new Formulaire();
        Traitement trt = new Traitement();
        try{
            usrCtrl.setRoles( cookieValue, model );

            FormulaireDTO f = formCtrl.getObj( cookieValue, UUID.fromString(formId) ,new FormulaireDTO(),FormulaireDTO.class , model);
            formForm.setFromDTO( f );
            formCtrl.loadForm( cookieValue, formForm ,"GET",model);

            model.addAttribute("form",formForm);

            //FormulaireDTO formDTO = formCtrl.formGetById(cookieValue,UUID.fromString(formId) , model);
            //formCtrl.loadForm( cookieValue, new Formulaire(formDTO) ,"GET",model);
            model.addAttribute("formId",formId);
            model.addAttribute("typeTrt","Form");
            model.addAttribute("trt",trt);


            List<TraitementDTO> lTrt;
            HttpHeaders headers = initHeadersRest(cookieValue);
            try{
                lTrt = restTemplateHelper.getForList(TraitementDTO.class,configurationService.getUrl()+"/gestionTraitement/form/"+formId,headers );
            }catch( Exception e ){
                model.addAttribute("Err",e.getMessage());
                throw new Exception(e.getMessage());
            }

            model.addAttribute("lTrt",lTrt);
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

    @PostMapping(value = "/Traitement")
    public String trtPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("typeTrt") final String typeTrt
            ,@ModelAttribute("trt") final Traitement formTrt
            ,@ModelAttribute("submit") final String submit
            ,Model model){
        try{
            usrCtrl.setRoles( cookieValue, model );

            UtilisateurDTO moi = usrCtrl.getMoi(cookieValue,model);
            //GestionnaireDTO gestDTO = gestCtrl.getObj( cookieValue, moi.getCitoyen().getGest().getId() ,new GestionnaireDTO(),GestionnaireDTO.class,model );
            //si je suis un gestionnaire
            if(moi.getCitoyen().getGest()==null)throw new IllegalArgumentException("illegalGest");
            formTrt.setGestId(moi.getCitoyen().getGest().getId().toString());


            FormulaireDTO formDTO = formCtrl.getObj( cookieValue, UUID.fromString(formTrt.getFormId()) ,new FormulaireDTO(),FormulaireDTO.class ,model );
            model.addAttribute("form", formDTO );
            model.addAttribute("citoyen",citCtrl.getObj(cookieValue,formDTO.getCitoyenId(),new CitoyenDTO(),CitoyenDTO.class,model) );

            //enregistrement du traitement avec l'ID du gestionnaire
            UUID trtId = super.postForm(cookieValue, formTrt.getDTO(), method, model);
            //retrouver les éléments insérés en DB (date du traitement, ...)
            formTrt.setFromDTO( super.getObj( cookieValue,trtId, formTrt.getDTO(),TraitementDTO.class,model ) );

            //FormulaireDTO formDTO = formCtrl.formGetById( cookieValue, UUID.fromString( formTrt.getFormId() ) , model);
            //formCtrl.loadForm( cookieValue, new Formulaire(formDTO) ,"GET",model);

			model.addAttribute("Msg",submit);
            switch(submit){
				case "renvoyerForm":
					//traitement remettant le formulaire consultable par le citoyen
					formCtrl.invalidateForm( cookieValue, formTrt.getFormId() ,model );
					return "Traitement/listForm.html";
				case "envoiReponse":
					//redirection vers création réponse
                    model.addAttribute("trtId",trtId);

                    DocArtiste c = new DocArtiste();
                    c.setTypeDocArtiste("Carte artiste");
                    model.addAttribute("docCarte", c);

                    DocArtiste v = new DocArtiste();
                    v.setTypeDocArtiste("Visa artiste");
                    model.addAttribute("docVisa",v);

                    //model.addAttribute("listSect",listSect);
                    List<UUID> activitesId = new ArrayList<>();
                    model.addAttribute("activitesId", activitesId );

                    model.addAttribute("rep",new ReponseDTO() );
                    return "Reponse/put.html";
				case "sauvCommentaire":
                //rien de plus, tout a déjà été fait plus haut (sauvegarde du traitement)
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
                if(r.getLang()!=null && r.getLang().equals(lang))
                    bLangOk=true;
            }
            if( !bLangOk ) {
                model.addAttribute("Err", "errLangDispo");
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
                        @PathVariable("trtId") String trtId,
                        @ModelAttribute("form") final Formulaire formForm,
                        @ModelAttribute("trt") final Traitement trt,
                        Model model){
        try{
            usrCtrl.setRoles( cookieValue, model );

            TraitementDTO t = super.getObj( cookieValue, UUID.fromString(trtId) ,new TraitementDTO(),TraitementDTO.class , model);
            FormulaireDTO f = formCtrl.getObj( cookieValue, t.getFormId() ,new FormulaireDTO(),FormulaireDTO.class , model);
            formForm.setFromDTO( f );
            formCtrl.loadForm( cookieValue, formForm ,"GET",model);
            trt.setFromDTO(t);
            //FormulaireDTO formDTO = formCtrl.formGetById(cookieValue,UUID.fromString(formId) , model);
            //formCtrl.loadForm( cookieValue, new Formulaire(formDTO) ,"GET",model);
            model.addAttribute("typeTrt","Form");
            model.addAttribute("trt",trt);

            GestionnaireDTO gestDTO = gestCtrl.getObj( cookieValue, UUID.fromString(trt.getGestId()), new GestionnaireDTO(),GestionnaireDTO.class , model);
            model.addAttribute("gestNom",gestDTO.getCitoyen().getNom());
            model.addAttribute("gestId",gestDTO.getId());
            //model.addAttribute("citoyen",citCtrl.getObj( cookieValue , trtDTO.getForm().getCitoyenId(), new CitoyenDTO(),CitoyenDTO.class, model ) );
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
            return "Traitement/listForm.html";
        }catch( Exception e ){
            model.addAttribute("Err" , e.getMessage() );
            return "login.html";
        }
    }
}