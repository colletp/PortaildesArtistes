package com.unamur.portaildesartistes.webclient.controler;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
import com.unamur.portaildesartistes.DTO.AdresseDTO;
import com.unamur.portaildesartistes.DTO.PrestationDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Prestation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

// Date de la prestation (si plusieurs jours compléter plusieurs lignes)
// Nature de la prestation
// Nom du donneur d’ordre ou numéro *BCE (*Banque-carrefour des entreprises)
// Signature du donneur d’ordre
// Indemnités prévues

@Controller
public class PrestationControler extends Controler<PrestationDTO, Class< PrestationDTO >, Prestation> {
    private static final Logger logger = LoggerFactory.getLogger(PrestationControler.class);

    @Autowired
    private DocArtisteControler docCtrl;

    @Autowired
    private SecteurControler sectCtrl;
    @Autowired
    private UtilisateurControler usrCtrl;

    @Autowired
    private AdresseControler adrCtrl;

    @GetMapping(value = "/Prestation/creer")
    public String prestCreateDef( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("form") final Prestation formPrest
            ,Model model) {

        return loadForm(cookieValue, formPrest, "put", model);
    }

    public String loadForm(String cookieValue, Prestation prestForm, String method, Model model){
        try{

            // Initialise une liste d'activités possible
            // Activités
            //tous les secteurs et activités existants
            prestForm.setSecteurActivites( sectCtrl.listSecteurActivite( cookieValue , model ) );
            model.addAttribute("activites",prestForm.getActiviteId() );

            /*
            private UUID commanditaireId;
            private UUID docArtisteId;
            private UUID seDerouleId;
            */

            // Met la date à la date du jour
            prestForm.setDatePrest(new Date());
            // valeur par défaut pour la durée
            prestForm.setDuree(0);
            // valeur par défaut pour le montant
            prestForm.setMontant(0.0);
            // valeur par défaut pour etat => Nouveau
            prestForm.setEtat("Nouveau");
            model.addAttribute("form",prestForm);

            return "Prestation/" +(method.isEmpty()?"post":method)+".html";
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            return "Prestation/"+(method.isEmpty()?"post":method)+".html";
            usrCtrl.setRoles(cookieValue, model);
            UUID usrId = docCtrl.getMyId(cookieValue);
            UUID docId = null; //TODO prendre ici l'identifiant du formulaire en fonction de l'utilisateur
            return "Prestation/put.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @PostMapping(value="/Prestation", params={"addRow"})
    public String addRowSectAct(@CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            , @ModelAttribute("_method")final String method
            , @ModelAttribute("form") final Prestation prestForm
            , @ModelAttribute("addRow") final String add
            , Model model){
        if(prestForm.getActToAddBySect()==null)prestForm.setActToAddBySect(new ArrayList<>());
        ActiviteDTO actDTO = new ActiviteDTO();
        actDTO.setSecteurId( UUID.fromString(add) );
        prestForm.getActToAddBySect().add( actDTO );
        return loadForm(cookieValue,prestForm,method,model);
    }

    @PostMapping(value="/Prestation", params={"removeRow"})
    public String removeRowSectAct(@CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method")final String method
            ,@ModelAttribute("form") final Prestation prestForm
            ,@ModelAttribute("removeRow") final String remove
            ,Model model
            ,final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        prestForm.getActToAddBySect().remove(rowId.intValue());
        return loadForm(cookieValue,prestForm,method,model);
    }


    @GetMapping(value = "/Prestation/modif/{id}")
    public String prestModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                             @PathVariable("id") String itemId ,
                             @ModelAttribute("form") final Prestation formPrest,
                             Model model){
        try{
            model.addAttribute("form",super.getObj(cookieValue,UUID.fromString(itemId),new PrestationDTO(),PrestationDTO.class,model));
            return "Prestation/post.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }


    @PostMapping(value = "/Prestation" , params={"submit"})
    public String formPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final Prestation prestForm
            ,Model model){
        try{
            UUID formId = super.postForm(cookieValue,prestForm,method,model);
            if(formId!=null) {
                model.addAttribute("Msg", "Données sauvées");
                prestForm.setId(formId.toString());
                loadForm(cookieValue, prestForm, method, model);
                return "Prestation/get.html";
            }else{
                return "Prestation/"+(method.isEmpty()?"post":method)+".html";
            }
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            return "Prestation/"+(method.isEmpty()?"post":method)+".html";
        }catch(Exception e){
            return "/login";
        }
    }

    @PostMapping(value = "/Prestation")
    public String prestPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final Prestation formPrest
            ,Model model){

        PrestationDTO formDTO = formPrest.getDTO();
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("form",super.postForm(cookieValue,formDTO,method,model) );
            return "Prestation/get.html";
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            return "Prestation/"+(method.isEmpty()?"post":method)+".html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @GetMapping(value = "/Prestation")
    public String prestList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("form",super.list(cookieValue,new PrestationDTO(),PrestationDTO.class,model));
            return "Prestation/list.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @GetMapping(value = "/Prestation/{id}")
    public String Form( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                        @PathVariable("id") String itemId ,
                        Model model){
        try{
            usrCtrl.setRoles(cookieValue, model);
            model.addAttribute("form",super.getObj(cookieValue,UUID.fromString(itemId),new PrestationDTO(),PrestationDTO.class,model));
            return "Prestation/get.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @GetMapping(value = "Prestation/suppr/{id}")
    public String prestSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                             @PathVariable("id") String itemId,
                             Model model) {
        try{
            usrCtrl.setRoles(cookieValue, model);
            super.delete(cookieValue,new PrestationDTO(),UUID.fromString(itemId),model);
            return "Prestation/list.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }
}