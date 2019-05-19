package com.unamur.portaildesartistes.webclient.controler;

import com.unamur.portaildesartistes.DTO.*;
import com.unamur.portaildesartistes.webclient.dataForm.DocArtiste;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.management.ServiceNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

// Date de la DocArtiste (si plusieurs jours compléter plusieurs lignes)
// Nature de la DocArtiste
// Nom du donneur d’ordre ou numéro *BCE (*Banque-carrefour des entreprises)
// Signature du donneur d’ordre
// Indemnités prévues

@Controller
public class DocArtisteControler extends Controler<DocArtisteDTO, Class< DocArtisteDTO >, DocArtiste> {
    private static final Logger logger = LoggerFactory.getLogger(DocArtisteControler.class);

    @Autowired
    private FormulaireControler formCtrl;
    @Autowired
    private CitoyenControler citCtrl;
    @Autowired
    private SecteurControler sectCtrl;

    public String loadDoc(String cookieValue, DocArtisteDTO docArtDTO, String method, Model model){
        DocArtiste docArtForm = new DocArtiste();
        docArtForm.setFromDTO(docArtDTO);
        return loadDoc(cookieValue, docArtForm, method, model);
    }
    private String loadDoc(String cookieValue, DocArtiste docArtForm, String method, Model model){
        try{
            model.addAttribute("citoyen", citCtrl.getById( cookieValue , method.toUpperCase().equals("PUT")?citCtrl.getMyId(cookieValue):UUID.fromString(docArtForm.getCitoyenId()) , model ) );
            docArtForm.setSecteurActivites( sectCtrl.listSecteurActivite( cookieValue , model ) );
            model.addAttribute("docArt",docArtForm);
            model.addAttribute("activites",docArtForm.getActivitesId() );
            return "DocArtiste/"+(method.isEmpty()?"post":method)+".html";
        }catch(Exception e){
            return "/login";
        }
    }


    @GetMapping(value = "/DocArtiste/creer/{id}")
    public String docArtCreate( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable("id") UUID itemId
            ,@ModelAttribute("form") final DocArtiste formDocArt
            ,Model model){
        try{
            FormulaireDTO formDTO = formCtrl.formGetById(cookieValue,itemId, model);
            List<UUID> lActIdDTO = formDTO.getActivitesId();
            Collection<SecteurDTO> lSectDTO = formDTO.getSecteurActivites();
            model.addAttribute("form",formDocArt);
            model.addAttribute("activites", lActIdDTO );
            model.addAttribute("secteurs", lSectDTO );
            return "DocArtiste/put.html";
        }catch( Exception e ){
            return "/login.html";
        }
    }

    @GetMapping(value = "/DocArtiste/modif/{id}")
    public String docArtModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,
                             @PathVariable("id") UUID itemId ,
                             @ModelAttribute("form") final DocArtiste formDocArt,
                             Model model){
        try {
            model.addAttribute("form", super.getObj(cookieValue, itemId, new DocArtisteDTO(), DocArtisteDTO.class, model));
            return "DocArtiste/post.html";
        }catch(Exception e){
            return "/login";
        }
    }

    @PostMapping(value = "/DocArtiste")
    public String docArtPost( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final DocArtiste formDocArt
            ,Model model){
        try {
            DocArtisteDTO docArtDTO = formDocArt.getDTO();
            docArtDTO.setCitoyenId( formCtrl.getMyId(cookieValue) );
            model.addAttribute("form", super.postForm(cookieValue,docArtDTO,method,model));
            return "DocArtiste/"+(method.isEmpty()?"post":method)+".html";
        }catch(Exception e){
            return "/login";
        }
    }

    @GetMapping(value = "/DocArtiste")
    public String docArtList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        try{
            model.addAttribute("form",super.list(cookieValue,new DocArtisteDTO(),DocArtisteDTO.class,model));
            return "DocArtiste/list.html";
        }catch(Exception e){
            return "/login";
        }
    }
    public List<DocArtisteDTO> listByLang( String cookieValue , String lang, Model model )throws Exception{
        HttpHeaders headers = initHeadersRest(cookieValue);
        try{
            return restTemplateHelper.getForList(DocArtisteDTO.class,configurationService.getUrl()+"/gestionDocArtiste/lang/"+lang,headers );
        }catch( ServiceNotFoundException e ){
            model.addAttribute("Err",e.getMessage() );
            return new ArrayList<>();
        }
    }

    @GetMapping(value = "/DocArtiste/{id}")
    public String docArtGetById( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                        @PathVariable("id") UUID itemId ,
                        Model model){
        try{
            model.addAttribute("form",super.getObj(cookieValue,itemId,new DocArtisteDTO(),DocArtisteDTO.class,model));
            return "DocArtiste/get.html";
        }catch(Exception e){
            return "/login";
        }
    }

    public DocArtisteDTO docObjArtGetById(String cookieValue, UUID itemId, Model model)throws Exception{
        return super.getObj(cookieValue,itemId,new DocArtisteDTO(),DocArtisteDTO.class, model);
    }

    @GetMapping(value = "DocArtiste/suppr/{id}")
    public String docArtSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                             @PathVariable("id") UUID itemId,
                             Model model) {
        try{
            super.delete(cookieValue,new DocArtisteDTO(),itemId,model);
            return "docArtiste/list.html";
        }catch(Exception e){
            return "/login";
        }
    }

}