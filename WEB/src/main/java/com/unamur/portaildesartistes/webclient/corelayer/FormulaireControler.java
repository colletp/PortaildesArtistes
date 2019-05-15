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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.UUID;

@Controller
public class FormulaireControler extends Controler< FormulaireDTO , Class< FormulaireDTO >, Formulaire > {
    private static final Logger logger = LoggerFactory.getLogger(FormulaireControler.class);

    @Autowired
    private SecteurControler sectCtrl;
    @Autowired
    private ActiviteControler actCtrl;

    @GetMapping(value = "/Formulaire/creer")
    public String formCreate( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model){
        //formForm.setRessources();
        formForm.setActivitesId( new ArrayList<>() );
        model.addAttribute("form",formForm);
        model.addAttribute("activites",formForm.getActivitesId() );
        sectCtrl.listSecteurActivite( cookieValue, model);
        //String fragment = sectCtrl.listSecteurActivite( cookieValue , model );
        return "Formulaire/put.html";
    }

    @PostMapping(value="/Formulaire", params={"addRowcursusAcCreate"})
    public String addRowCursusAc( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model
            //final SeedStarter seedStarter, final BindingResult bindingResult
            ) {
        formForm.getCursusAc().add( String.valueOf(formForm.getCursusAc().size()) );
        model.addAttribute("form",formForm);
        model.addAttribute("activites",formForm.getActivitesId() );
        sectCtrl.listSecteurActivite( cookieValue, model);
        return "Formulaire/put.html";
    }
    @PostMapping(value="/Formulaire", params={"removeRowcursusAcCreate"})
    public String removeRowCursusAc(@CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model
            //,final BindingResult bindingResult
            ,final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRowcursusAcCreate"));
        formForm.getCursusAc().remove(rowId.intValue());
        model.addAttribute("form",formForm);
        model.addAttribute("activites",formForm.getActivitesId() );
        sectCtrl.listSecteurActivite( cookieValue, model);
        return "Formulaire/put.html";
    }
    @PostMapping(value="/Formulaire", params={"addRowexpProCreate"})
    public String addRowExpPro( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model
                                  //final SeedStarter seedStarter, final BindingResult bindingResult
    ) {
        formForm.getExpPro().add( String.valueOf(formForm.getExpPro().size()) );
        model.addAttribute("form",formForm);
        model.addAttribute("activites",formForm.getActivitesId() );
        sectCtrl.listSecteurActivite( cookieValue, model);
        return "Formulaire/put.html";
    }
    @PostMapping(value="/Formulaire", params={"removeRowexpProCreate"})
    public String removeRowExpPro(@CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model
                                    //,final BindingResult bindingResult
            ,final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRowexpProCreate"));
        formForm.getExpPro().remove(rowId.intValue());
        model.addAttribute("form",formForm);
        model.addAttribute("activites",formForm.getActivitesId() );
        sectCtrl.listSecteurActivite( cookieValue, model);
        return "Formulaire/put.html";
    }
    @PostMapping(value="/Formulaire", params={"addRowressourcesCreate"})
    public String addRowRessources( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model
                                  //final SeedStarter seedStarter, final BindingResult bindingResult
    ) {
        formForm.getRessources().add( String.valueOf(formForm.getRessources().size()) );
        model.addAttribute("form",formForm);
        model.addAttribute("activites",formForm.getActivitesId() );
        sectCtrl.listSecteurActivite( cookieValue, model);
        return "Formulaire/put.html";
    }
    @PostMapping(value="/Formulaire", params={"removeRowressourcesCreate"})
    public String removeRowRessources(@CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model
                                    //,final BindingResult bindingResult
            ,final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRowressourcesCreate"));
        formForm.getRessources().remove(rowId.intValue());
        model.addAttribute("form",formForm);
        model.addAttribute("activites",formForm.getActivitesId() );
        sectCtrl.listSecteurActivite( cookieValue, model);
        return "Formulaire/put.html";
    }

    @GetMapping(value = "/Formulaire/creer/{typeDoc}")
    public String formCreateDef( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable(name="typeDoc")final String typeDoc
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model){
        //formForm.setRessources();
        formForm.setActivitesId( new ArrayList<>() );
        switch (typeDoc){
            case "carte":
                formForm.setCarte("1");
                break;
            case "visa":
                formForm.setVisa("1");
                break;
            default:
        }
        model.addAttribute("form",formForm);
        model.addAttribute("activites",new ArrayList<String>());
        sectCtrl.listSecteurActivite( cookieValue, model);
        return "Formulaire/put.html";
    }

    @GetMapping(value = "/Formulaire/modif/{id}")
    public String formModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                                @PathVariable("id") UUID itemId ,
                                @ModelAttribute("form") final Formulaire formForm,
                                Model model){
        logger.error("Formulaire/modif : Authentication received! Cookie : "+cookieValue );
        model.addAttribute("form",formForm);
        model.addAttribute("activites",formForm.getActivitesId());
        sectCtrl.listSecteurActivite( cookieValue, model);
        return super.getForm(cookieValue,new FormulaireDTO(),new Formulaire(),itemId,FormulaireDTO.class,"POST",model);
    }

    @PostMapping(value = "/Formulaire" , params={"submit"})
    public String formPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final Formulaire formForm
            ,Model model){
        logger.error("Form(post) "+method+" : Authentication received! Cookie : "+cookieValue );
        try {
            FormulaireDTO formDTO = formForm.getDTO();
            /*List<ActiviteDTO> listAct=new ArrayList<>();
            for(String actId : formForm.getActivitesId() )
                listAct.add(actCtrl.getObj(cookieValue,UUID.fromString(actId),new ActiviteDTO(),ActiviteDTO.class ));
            formDTO.setActivites(listAct);*/
            String str = super.postForm(cookieValue,formDTO,method);
            model.addAttribute("form",formForm);
            model.addAttribute("activites",formForm.getActivitesId() );
            sectCtrl.listSecteurActivite( cookieValue, model);

            //actCtrl.activite();
            return str;
        }catch(IllegalArgumentException e){
            String fragment = sectCtrl.listSecteurActivite( cookieValue , model );
            model.addAttribute("Err",e.getMessage());
            model.addAttribute("form",formForm);
logger.error( formForm.getActivitesId().toString() );
            model.addAttribute("activites",formForm.getActivitesId());
            return "/Formulaire/"+method+".html";
        }catch(ParseException e){
            String fragment = sectCtrl.listSecteurActivite( cookieValue , model );
            model.addAttribute("Err",e.getMessage());
            model.addAttribute("form",formForm);
logger.error( formForm.getActivitesId().toString() );
            model.addAttribute("activites",formForm.getActivitesId());
            return "/Formulaire/"+method+".html";
        }
    }

    @GetMapping(value = "/Formulaire")//initialisation du login
    public String formList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
                                ,Model model){
        logger.error("Form List : Authentication received! Cookie : "+cookieValue );
        return super.list(cookieValue,new FormulaireDTO(),FormulaireDTO.class,model);
    }

    @GetMapping(value = "/Formulaire/{id}")//initialisation du login
    public String formDetail( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") UUID itemId ,
                           Model model){
        logger.error("Form : Authentication received! Cookie : "+cookieValue );
        return super.getForm(cookieValue,new FormulaireDTO(),new Formulaire(),itemId,FormulaireDTO.class,"GET",model);
    }

    public FormulaireDTO formGetById( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
                        ,@PathVariable("id") UUID itemId
                        //,Model model
                        ){
        logger.error("Form : Authentication received! Cookie : "+cookieValue );
        return super.getObj( cookieValue,itemId,new FormulaireDTO(), FormulaireDTO.class );
    }


    @GetMapping(value = "Formulaire/suppr/{id}")
    public String formSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        return super.delete(cookieValue,new FormulaireDTO(),itemId,model);
    }
}