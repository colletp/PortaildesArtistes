package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.FormulaireDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Formulaire;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.UUID;

@Controller
public class FormulaireControler extends Controler< FormulaireDTO , Class< FormulaireDTO >, Formulaire > {
    private static final Logger logger = LoggerFactory.getLogger(FormulaireControler.class);

    @Autowired
    private SecteurControler sectCtrl;
    @Autowired
    private CitoyenControler citCtrl;

    @GetMapping(value = "/Formulaire/creer")
    public String formCreate( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model){
        return loadForm(cookieValue,formForm,"put",model);
    }

    private String loadForm(String cookieValue, Formulaire formForm, String method, Model model){
        formForm.setSecteurActivites( sectCtrl.listSecteurActivite( cookieValue ) );
        model.addAttribute("form",formForm);
        model.addAttribute("activites",formForm.getActivitesId() );
        String verb;
        switch(method.toUpperCase()){
            case "PUT":
                model.addAttribute("citoyen", citCtrl.getCitoyen( cookieValue , citCtrl.getMyId(cookieValue) ) );
                verb="put";
            break;
            case "POST": case "":
                model.addAttribute("citoyen", citCtrl.getCitoyen( cookieValue , UUID.fromString(formForm.getCitoyenId()) ) );
                verb="post"; break;
            default : verb=""; logger.error( "verbe REST non reconnu : "+method );
        }
        return "Formulaire/"+verb+".html";
    }
    private String loadForm(String cookieValue, FormulaireDTO formDTO, String method, Model model){
        Formulaire formForm = new Formulaire();
        formForm.setFromDTO(formDTO);
        return loadForm(cookieValue, formForm, method, model);
    }

    @PostMapping(value="/Formulaire", params={"addRowcursusAc"})
    public String addRowCursusAc( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method")final String method
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model
            ) {
        formForm.getCursusAc().add( String.valueOf(formForm.getCursusAc().size()) );

        return loadForm(cookieValue,formForm,method,model);
    }
    @PostMapping(value="/Formulaire", params={"removeRowcursusAc"})
    public String removeRowCursusAc(@CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method")final String method
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model
            //,final BindingResult bindingResult
            ,final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRowcursusAc"));
        formForm.getCursusAc().remove(rowId.intValue());

        return loadForm(cookieValue,formForm,method,model);
    }
    @PostMapping(value="/Formulaire", params={"addRowexpPro"})
    public String addRowExpPro( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method")final String method
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model
                                  //final SeedStarter seedStarter, final BindingResult bindingResult
    ) {
        formForm.getExpPro().add( String.valueOf(formForm.getExpPro().size()) );

        return loadForm(cookieValue,formForm,method,model);
    }
    @PostMapping(value="/Formulaire", params={"removeRowexpPro"})
    public String removeRowExpPro(@CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method")final String method
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model
                                    //,final BindingResult bindingResult
            ,final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRowexpPro"));
        formForm.getExpPro().remove(rowId.intValue());

        return loadForm(cookieValue,formForm,method,model);
    }
    @PostMapping(value="/Formulaire", params={"addRowressources"})
    public String addRowRessources( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method")final String method
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model
                                  //final SeedStarter seedStarter, final BindingResult bindingResult
    ) {
        formForm.getRessources().add( String.valueOf(formForm.getRessources().size()) );

        return loadForm(cookieValue,formForm,method,model);
    }
    @PostMapping(value="/Formulaire", params={"removeRowressources"})
    public String removeRowRessources(@CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method")final String method
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model
                                    //,final BindingResult bindingResult
            ,final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRowressources"));
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

        return loadForm(cookieValue,formForm,"put",model);
    }

    @GetMapping(value = "/Formulaire/modif/{id}")
    public String formModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                                @PathVariable("id") UUID itemId ,
                                Model model){
        FormulaireDTO formDTO = super.getObj( cookieValue,itemId,new FormulaireDTO(),FormulaireDTO.class );
        Formulaire formForm = new Formulaire();
        formForm.setFromDTO(formDTO);

        return loadForm(cookieValue,formForm,"post",model);
    }

    @PostMapping(value = "/Formulaire" , params={"submit"})
    public String formPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model){
        try {
            UUID formId = super.postForm(cookieValue,formForm,method);

            model.addAttribute("Msg","Données sauvées");
            formForm.setId(formId.toString());
            loadForm(cookieValue,formForm,method,model);
            return "Formulaire/get.html";
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            return loadForm(cookieValue,formForm,method,model);
        }
    }

    @GetMapping(value = "/Formulaire")
    public String formList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
                                ,Model model){
        return super.list(cookieValue,new FormulaireDTO(),FormulaireDTO.class,model);
    }

    @GetMapping(value = "/Formulaire/{id}")
    public String formDetail( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") UUID itemId ,
                           Model model){
        loadForm(cookieValue, super.getObj(cookieValue,itemId,new FormulaireDTO(),FormulaireDTO.class) ,"",model);
        return "Formulaire/get.html";
        //return super.getForm(cookieValue,new FormulaireDTO(),new Formulaire(),itemId,FormulaireDTO.class,"GET",model);
    }

    public FormulaireDTO formGetById( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
                        ,@PathVariable("id") UUID itemId
                        ){
        return super.getObj( cookieValue,itemId,new FormulaireDTO(), FormulaireDTO.class );
    }


    @GetMapping(value = "Formulaire/suppr/{id}")
    public String formSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        return super.delete(cookieValue,new FormulaireDTO(),itemId,model);
    }
}