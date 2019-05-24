package com.unamur.portaildesartistes.webclient.controler;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.RoleDTO;
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

    public void setRoles(String cookieValue, Model model)throws Exception{
        model.addAttribute("GestFormFR", testRole(cookieValue, "Gestionnaire de formulaire FR", model));
        model.addAttribute("GestFormEN", testRole(cookieValue, "Gestionnaire de formulaire EN", model));
        model.addAttribute("GestPers",   testRole(cookieValue, "Gestionnaire du personnel", model));
        model.addAttribute("GestPrest",  testRole(cookieValue, "Gestionnaire de prestation", model));
    }

    private Boolean testRole(String cookieValue, String role, Model model)throws Exception {
        for (RoleDTO r : getMoi(cookieValue, model).getAuthorities()){
            logger.error(r.getAuthority());
            if( r.getAuthority().equals(role) )
                return true;
        }
        return false;
    }

    @GetMapping(value = "/Utilisateur/creer")
    public String citoyenCreate( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        try{
            setRoles(cookieValue, model);
            return "Utilisateur/put.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }

    @Autowired
    CitoyenControler citCtrl;
    @Autowired
    AdresseControler adrsCtrl;

    public UtilisateurDTO getMoi(String cookieValue,Model model)throws Exception{
        return super.getObj(cookieValue, getMyId(cookieValue) ,new UtilisateurDTO(),UtilisateurDTO.class,model);
    }

    @GetMapping(value = "/Utilisateur/modif/moi")//initialisation du login
    public String citoyenModifMoi( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,Model model){
        try{
            setRoles(cookieValue, model);

            model.addAttribute("form", new UtilisateurInscript( super.getObj(cookieValue, super.getMyId(cookieValue) , new UtilisateurDTO(), UtilisateurDTO.class, model) ) );
            return "/Utilisateur/post.html";
        }catch(Exception e){
            model.addAttribute("Err",e.getMessage());
            return "/login";
        }
    }
    @GetMapping(value = "/Utilisateur/modif/{id}")
    public String citoyenModif( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@PathVariable("id") UUID itemId
            ,Model model){

        try{
            setRoles(cookieValue, model);

            CitoyenDTO citDTO = citCtrl.getObj( cookieValue , itemId, new CitoyenDTO(),CitoyenDTO.class, model );
            UtilisateurInscript usrForm = new UtilisateurInscript();
            usrForm.setUtilisateur( super.getObj( cookieValue,itemId,new UtilisateurDTO(),UtilisateurDTO.class, model ) );
            usrForm.setCitoyen(citDTO);
            usrForm.setAdresse( citDTO.getResideAdr() );
            model.addAttribute("form",usrForm);
            return "/Utilisateur/post.html";
        }catch(Exception e){
            model.addAttribute("Err",e.getMessage());
            return "/login";
        }
    }

    @GetMapping(value = "/Utilisateur")
    public String citoyenList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
                                ,Model model){
        try{
            setRoles(cookieValue, model);
            List<UtilisateurDTO> lUsrDTO = super.list(cookieValue, new UtilisateurDTO(), UtilisateurDTO.class, model );
            List<UtilisateurInscript> lUsrForm = new ArrayList<>();
            for( UtilisateurDTO usrDTO : lUsrDTO ){
                CitoyenDTO citDTO = citCtrl.getObj( cookieValue , usrDTO.getId(), new CitoyenDTO(),CitoyenDTO.class, model );

                UtilisateurInscript usrForm= new UtilisateurInscript();
                usrForm.setUtilisateur( usrDTO );
                usrForm.setCitoyen(citDTO);
                usrForm.setAdresse( citDTO.getResideAdr() );

                lUsrForm.add( usrForm );
            }

            model.addAttribute("form", lUsrForm );
            return "Utilisateur/list.html";
        }catch(Exception e){
            model.addAttribute("Err",e.getMessage());
            return "/login";
        }
    }

    @PostMapping(value = "/Utilisateur")
    public String citoyenPostConfirm( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("form") final UtilisateurInscript usrForm
            ,Model model){
        try{
            setRoles(cookieValue, model);
            //usrForm.setPassword(WebSecurityConfig.encoder().encode( usrForm.getPassword() ) );
            UtilisateurDTO usrDTO = usrForm.getDTO();
            super.postForm(cookieValue,usrDTO,method,model);
            model.addAttribute("Msg","Le profil a été mis à jour" );
            model.addAttribute("form", new UtilisateurInscript(usrDTO) );
            return "Utilisateur/get.html";
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            return "Utilisateur/"+(method.isEmpty()?"post":method)+".html";
        }catch(Exception e){
            model.addAttribute("Err",e.getMessage());
            return "/login";
        }
    }

    @GetMapping(value = "/Utilisateur/{id}")//initialisation du login
    public String citoyen( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                           @PathVariable("id") UUID itemId ,
                           Model model){
        try{
            setRoles(cookieValue, model);
            CitoyenDTO citDTO = citCtrl.getObj( cookieValue , itemId, new CitoyenDTO(),CitoyenDTO.class, model );
            UtilisateurInscript usrForm = new UtilisateurInscript();
            usrForm.setUtilisateur( super.getObj( cookieValue,itemId,new UtilisateurDTO(),UtilisateurDTO.class,model ) );
            usrForm.setCitoyen(citDTO);
            usrForm.setAdresse( citDTO.getResideAdr() );
            model.addAttribute("form",usrForm);
            return "Utilisateur/get.html";
        }catch(Exception e){
            model.addAttribute("Err",e.getMessage());
            return "/login";
        }
    }

    @GetMapping(value = "Utilisateur/suppr/{id}")
    public String citoyenSuppr( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ,
                                @PathVariable("id") UUID itemId,
                                Model model) {
        try{
            setRoles(cookieValue, model);
            super.delete(cookieValue,new UtilisateurDTO(),itemId,model);
            return "Utilisateur/list.html";
        }catch(Exception e){
            model.addAttribute("Err",e.getMessage());
            return "/login";
        }
    }
}