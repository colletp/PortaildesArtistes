package com.unamur.portaildesartistes.webclient.controler;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.FormulaireDTO;
import com.unamur.portaildesartistes.DTO.SecteurDTO;
import com.unamur.portaildesartistes.webclient.RestTemplateHelper;
import com.unamur.portaildesartistes.webclient.dataForm.Activite;
import com.unamur.portaildesartistes.webclient.dataForm.Formulaire;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class FormulaireControler extends Controler< FormulaireDTO , Class< FormulaireDTO >, Formulaire > {
    private static final Logger logger = LoggerFactory.getLogger(FormulaireControler.class);

    @Autowired
    private RestTemplateHelper restTemplateHelper;

    @Autowired
    private SecteurControler sectCtrl;
    @Autowired
    private ActiviteControler actCtrl;
    @Autowired
    private CitoyenControler citCtrl;
    @Autowired
    private UtilisateurControler usrCtrl;


    @GetMapping(value = "/Formulaire/invalidate/{formId}")
    public String invalidateForm( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable("formId") final String formId
            ,Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);

            invalidate(cookieValue,UUID.fromString(formId));
            return "Formulaire/list.html";
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            return "Formulaire/list.html";
        }catch(Exception e){
            model.addAttribute("Err",e.getMessage());
            return "login.html";
        }
    }

	private void invalidate( String cookieValue, UUID formId)throws Exception{
	    try {
            HttpHeaders headers = initHeadersRest(cookieValue);
            restTemplateHelper.getForEntity(String.class, configurationService.getUrl() + "/gestionFormulaire/invalidate/" + formId, headers);
        }
	    catch(Exception e){
            throw new IllegalArgumentException( e.getMessage() );
        }
	}
	
    @GetMapping(value = "/Formulaire/creer")
    public String formCreate( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model){
        String ret = loadForm(cookieValue,formForm,"put",model);
	    return ret;
    }

    public String loadForm(String cookieValue, Formulaire formForm, String method, Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            //citoyen ayant complété le formulaire
            CitoyenDTO citDTO = citCtrl.getObj(cookieValue,method.toUpperCase().equals("PUT")?citCtrl.getMyId(cookieValue):UUID.fromString( formForm.getCitoyenId() ),new CitoyenDTO(),CitoyenDTO.class,model );
            model.addAttribute("citoyen",citDTO);
            //formForm.setCitoyenId( citDTO.getId().toString() );
            //tous les secteurs et activités existants
            //formForm.setSecteurActivites( sectCtrl.listSecteurActivite( cookieValue , model ) );
            if( formForm.getId()==null ){
                //si formulaire vide juste la liste des secteurs
                formForm.setSecteurActivites( sectCtrl.list(cookieValue,new SecteurDTO(),SecteurDTO.class,model) );
            }
            else{
                //formulaire complété avec la liste des secteurs et activités le concernant
                formForm.setSecteurActivites( sectCtrl.listSecteurActiviteByForm(cookieValue, UUID.fromString(formForm.getId()) , model) );
            }
            //model.addAttribute("form",formForm);
            //activité ayant été cochées par le citoyen
            //model.addAttribute("activites",formForm.getActivitesId() );
            return "Formulaire/"+(method.isEmpty()?"post":method)+".html";
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            return "Formulaire/"+(method.isEmpty()?"post":method)+".html";
        }catch(Exception e){
            model.addAttribute("Err",e.getMessage());
            return "login.html";
        }
    }

    @PostMapping(value="/Formulaire", params={"addRow"})
    public String addRowCursusAc( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method")final String method
            ,@ModelAttribute("form") final Formulaire formForm
            ,@ModelAttribute("addRow") String field
            ,Model model
            ) {
        switch(field){
            case "cursusAc":
                if(formForm.getCursusAc()==null)formForm.setCursusAc(new ArrayList<>());
                formForm.getCursusAc().add( String.valueOf(formForm.getCursusAc().size()) );
                break;
            case "expPro":
                if(formForm.getExpPro()==null)formForm.setExpPro(new ArrayList<>());
                formForm.getExpPro().add( String.valueOf(formForm.getExpPro().size()) );
                break;
            case "ressources":
                if(formForm.getRessources()==null)formForm.setRessources(new ArrayList<>());
                formForm.getRessources().add( String.valueOf(formForm.getRessources().size()) );
                break;
            default:
        }
        return
        loadForm(cookieValue,formForm,method,model);
        //"Formulaire/"+(method.isEmpty()?"post":method)+".html";
    }
    @PostMapping(value="/Formulaire", params={"removeRowcursusAc"})
    public String removeRowcursusAc(@CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,@ModelAttribute("_method")final String method,@ModelAttribute("form") final Formulaire formForm
            ,@ModelAttribute("removeRowcursusAc") final String field,Model model,final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(field);
        formForm.getCursusAc().remove(rowId.intValue());
        return loadForm(cookieValue,formForm,method,model);
    }
    @PostMapping(value="/Formulaire", params={"removeRowexpPro"})
    public String removeRowexpPro(@CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,@ModelAttribute("_method")final String method,@ModelAttribute("form") final Formulaire formForm
            ,@ModelAttribute("removeRowexpPro") final String field,Model model,final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(field);
        formForm.getExpPro().remove(rowId.intValue());
        return loadForm(cookieValue,formForm,method,model);
    }
    @PostMapping(value="/Formulaire", params={"removeRowressources"})
    public String removeRowressources(@CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,@ModelAttribute("_method")final String method,@ModelAttribute("form") final Formulaire formForm
            ,@ModelAttribute("removeRowressources") final String field,Model model,final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(field);
        formForm.getRessources().remove(rowId.intValue());
        return loadForm(cookieValue,formForm,method,model);
    }

    @GetMapping(value = "/Formulaire/creer/{typeDoc}")
    public String formCreateDef( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable(name="typeDoc")final String typeDoc
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model){
        formForm.setVisa( typeDoc.equals("visa")?"1":"0" );
        formForm.setCarte( typeDoc.equals("carte")?"1":"0" );
        //if(formForm.getActivitesId()==null)
        //    formForm.setActivitesId( new ArrayList<>() );
        return loadForm(cookieValue,formForm,"put",model);
    }

    @GetMapping(value = "/Formulaire/modif/{id}")
    public String formModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                                @PathVariable("id") UUID itemId ,
                                Model model){
        Formulaire f;
        try{
            usrCtrl.setRoles(cookieValue, model);
            f = new Formulaire( super.getObj( cookieValue,itemId,new FormulaireDTO(),FormulaireDTO.class,model ) );
        }catch(Exception e){
            model.addAttribute("Err",e.getMessage());
            return "login.html";
        }
        return loadForm(cookieValue, f ,"post",model);
    }
    @PostMapping(value="/Formulaire", params={"addAct"})
    public String addRowSectAct(@CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            , @ModelAttribute("_method")final String method
            , @ModelAttribute("form") final Formulaire formForm
            , @ModelAttribute("secteurId") final String secteurId
            , @ModelAttribute("nomActivite") final String nomActivite
            , @ModelAttribute("description") final String description
            , Model model){
        //if(formForm.getActToAddBySect()==null)formForm.setActToAddBySect(new ArrayList<>());
        try{
            usrCtrl.setRoles(cookieValue, model);

            //insérer le formulaire
            Model tmp= new ConcurrentModel();
            formPost(cookieValue,method,formForm,tmp);
            UUID formId = UUID.fromString( formForm.getId() );
            //puis l'invalide
            invalidate(cookieValue,formId);

            Activite actForm = new Activite();
            actForm.setSecteurId( secteurId );
            actForm.setNomActivite( nomActivite );
            actForm.setDescription( description );
            actCtrl.insertActForm(cookieValue, actForm, formId, model);

            loadForm(cookieValue,formForm,method,model);
            return "Formulaire/post.html";
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            return "Formulaire/"+(method.isEmpty()?"post":method)+".html";
        }catch(Exception e){
            model.addAttribute("Err",e.getMessage());
            return "login.html";
        }
    }
    @PostMapping(value="/Formulaire", params={"removeAct"})
    public String removeRowSectAct(@CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method")final String method
            ,@ModelAttribute("form") final Formulaire formForm
            ,@ModelAttribute("removeAct") final String actId
            ,Model model
            ,final HttpServletRequest req) {
        try{
            usrCtrl.setRoles(cookieValue, model);

            logger.error("actId:"+actId);
            actCtrl.supprActivite( cookieValue, UUID.fromString(actId), model );
            loadForm(cookieValue,formForm,method,model);
            return "Formulaire/post.html";
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            return "Formulaire/post.html";
        }catch(Exception e){
            model.addAttribute("Err",e.getMessage());
            return "login.html";
        }
    }

    @PostMapping(value = "/Formulaire" , params={"submit"})
    public String formPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            FormulaireDTO formDTO = formForm.getDTO();

            CitoyenDTO citDTO = citCtrl.getObj(cookieValue,method.toUpperCase().equals("PUT")?citCtrl.getMyId(cookieValue):UUID.fromString( formForm.getCitoyenId() ),new CitoyenDTO(),CitoyenDTO.class,model );
            formDTO.setCitoyen(citDTO);

            UUID formId = super.postForm(cookieValue,formDTO,method,model);
            if(formId!=null) {
                model.addAttribute("Msg", "formSend");
                formDTO=super.getObj(cookieValue,formId,formDTO,FormulaireDTO.class,model );
                formForm.setFromDTO(formDTO);
                loadForm(cookieValue, formForm, method, model);
                return "Formulaire/get.html";
            }else{
                return "Formulaire/"+(method.isEmpty()?"post":method)+".html";
            }
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            model.addAttribute("form",formForm);
            return "Formulaire/"+(method.isEmpty()?"post":method)+".html";
        }catch(Exception e){
            model.addAttribute("Err",e.getMessage());
            return "login.html";
        }
    }

    @GetMapping(value = "/Formulaire")
    public String formList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
                                ,Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("form",super.list(cookieValue,new FormulaireDTO(),FormulaireDTO.class,model));
            return "Formulaire/list.html";
        }catch(Exception e){
            model.addAttribute("Err",e.getMessage());
            return "login.html";
        }
    }

    @GetMapping(value = "/Formulaire/my")
    public String listMyForms( String cookieValue , Model model ){
        try{
            usrCtrl.setRoles(cookieValue, model);
			
			HttpHeaders headers = initHeadersRest(cookieValue);
            model.addAttribute("form",restTemplateHelper.getForList(FormulaireDTO.class,configurationService.getUrl()+"/gestionFormulaire/myForms",headers ) );
			return "Formulaire/list.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "login.html";
        }
    }
	
    public List<FormulaireDTO> listATraiterByLang( String cookieValue , String lang , Model model )throws Exception{
        HttpHeaders headers = initHeadersRest(cookieValue);
        try{
            return restTemplateHelper.getForList(FormulaireDTO.class,configurationService.getUrl()+"/gestionFormulaire/aTraiter/lang/"+lang,headers );
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
    public List<FormulaireDTO> listEnCoursByLang( String cookieValue , String lang, Model model )throws  Exception{
        HttpHeaders headers = initHeadersRest(cookieValue);
        try{
            return restTemplateHelper.getForList(FormulaireDTO.class,configurationService.getUrl()+"/gestionFormulaire/enCours/lang/"+lang,headers );
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
    public List<FormulaireDTO> listFiniByLang( String cookieValue , String lang, Model model )throws Exception{
        HttpHeaders headers = initHeadersRest(cookieValue);
        try{
            return restTemplateHelper.getForList(FormulaireDTO.class,configurationService.getUrl()+"/gestionFormulaire/fini/lang/"+lang,headers );
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping(value = "/Formulaire/{id}")
    public String formDetail( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") final UUID itemId ,
                           final Model model){
        try{
            //usrCtrl.setRoles(cookieValue, model);
            Formulaire f = new Formulaire(super.getObj( cookieValue,itemId,new FormulaireDTO(),FormulaireDTO.class , model));
            model.addAttribute("form",f);
            loadForm( cookieValue, f ,"GET",model);
            return "Formulaire/get.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "login.html";
        }

    }

    public FormulaireDTO formGetById( String cookieValue, UUID itemId , Model model)throws Exception{
        return super.getObj( cookieValue,itemId,new FormulaireDTO(), FormulaireDTO.class, model );
    }

    @GetMapping(value = "Formulaire/suppr/{id}")
    public String formSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        try{
            usrCtrl.setRoles(cookieValue, model);
            super.delete(cookieValue,new FormulaireDTO(),itemId,model);
            return "Formulaire/list.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "login.html";
        }
    }
}