package com.unamur.portaildesartistes.webclient.controler;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Utilisateur;
import com.unamur.portaildesartistes.webclient.dataForm.UtilisateurInscript;
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
public class UtilisateurControler extends Controler< UtilisateurDTO , java.lang.Class< UtilisateurDTO >, Utilisateur > {
    private static final Logger logger = LoggerFactory.getLogger(UtilisateurControler.class);

    @GetMapping(value = "/Utilisateur/creer")
    public String citoyenCreate( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        return "Utilisateur/put.html";
    }

    @Autowired
    CitoyenControler citControler;
    @Autowired
    AdresseControler adrControler;

    @GetMapping(value = "/Utilisateur/modif/moi")//initialisation du login
    public String citoyenModifMoi( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        try{
            model.addAttribute("form", new UtilisateurInscript( super.getObj(cookieValue, super.getMyId(cookieValue) , new UtilisateurDTO(), UtilisateurDTO.class, model) ) );
            return "/Utilisateur/post.html";
        }catch(Exception e){
            return "/login";
        }
    }
    @GetMapping(value = "/Utilisateur/modif/{id}")
    public String citoyenModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable("id") UUID itemId
            ,Model model){

        try{
            CitoyenDTO citDTO = citControler.getById( cookieValue , itemId, model );
            UtilisateurInscript usrForm = new UtilisateurInscript();
            usrForm.setUtilisateur( super.getObj( cookieValue,itemId,new UtilisateurDTO(),UtilisateurDTO.class, model ) );
            usrForm.setCitoyen(citDTO);
            usrForm.setAdresse( citDTO.getResideAdr() );
            model.addAttribute("form",usrForm);
            return "/Utilisateur/post.html";
        }catch(Exception e){
            return "/login";
        }
    }

    @GetMapping(value = "/Utilisateur")
    public String citoyenList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
                                ,Model model){
        try{
            List<UtilisateurDTO> lUsrDTO = super.list(cookieValue, new UtilisateurDTO(), UtilisateurDTO.class, model );
            List<UtilisateurInscript> lUsrForm = new ArrayList<>();
            for( UtilisateurDTO usrDTO : lUsrDTO ){
                CitoyenDTO citDTO = citControler.getById( cookieValue , usrDTO.getId(), model );

                UtilisateurInscript usrForm= new UtilisateurInscript();
                usrForm.setUtilisateur( usrDTO );
                usrForm.setCitoyen(citDTO);
                usrForm.setAdresse( citDTO.getResideAdr() );
                //usrForm.setAdresse( adrControler.getObj( cookieValue,citDTO.getReside() ,new AdresseDTO(),AdresseDTO.class ) );

                lUsrForm.add( usrForm );

            }

            model.addAttribute("form", lUsrForm );
            return "Utilisateur/list.html";
        }catch(Exception e){
            return "/login";
        }
    }

    @PostMapping(value = "/Utilisateur")
    public String citoyenPostConfirm( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final UtilisateurInscript usrForm
            ,Model model){
        try{
            //usrForm.setPassword(WebSecurityConfig.encoder().encode( usrForm.getPassword() ) );
            UtilisateurDTO usrDTO = usrForm.getDTO();
            UUID uuid = super.postForm(cookieValue,usrDTO,method,model);
            if(uuid!=null){
                model.addAttribute("Msg","Le profil a été mis à jour" );
                model.addAttribute("form", new UtilisateurInscript(usrDTO) );
                return "Utilisateur/get.html";
            }else{
                return "Utilisateur/"+(method.isEmpty()?"post":method)+".html";
            }
        }catch(Exception e){
            return "/login";
        }
    }

    @GetMapping(value = "/Utilisateur/{id}")//initialisation du login
    public String citoyen( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") UUID itemId ,
                           Model model){
        try{
            CitoyenDTO citDTO = citControler.getById( cookieValue , itemId, model );
            UtilisateurInscript usrForm = new UtilisateurInscript();
            usrForm.setUtilisateur( super.getObj( cookieValue,itemId,new UtilisateurDTO(),UtilisateurDTO.class,model ) );
            usrForm.setCitoyen(citDTO);
            usrForm.setAdresse( citDTO.getResideAdr() );
            model.addAttribute("form",usrForm);
            return "Utilisateur/get.html";
        }catch(Exception e){
            return "/login";
        }
    }

    @GetMapping(value = "Utilisateur/suppr/{id}")
    public String citoyenSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        try{
            super.delete(cookieValue,new UtilisateurDTO(),itemId,model);
            return "Utilisateur/list.html";
        }catch(Exception e){
            return "/login";
        }
    }
}