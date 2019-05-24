package com.unamur.portaildesartistes.webclient.controler;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Utilisateur;
import com.unamur.portaildesartistes.webclient.dataForm.UtilisateurInscript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.UUID;

@Controller
public class InscriptionControler extends Controler< UtilisateurDTO , java.lang.Class< UtilisateurDTO > , Utilisateur>{

    private static final Logger logger = LoggerFactory.getLogger(com.unamur.portaildesartistes.webclient.controler.LoginControler.class);

    @GetMapping(value = "/inscript")
    public String loginView( Model model ){
        UtilisateurInscript usr = new UtilisateurInscript();
        model.addAttribute("form",usr);
        return "inscript.html";
    }

    @PostMapping(value = "/inscript")
    public String inscript(
            @ModelAttribute("form") final UtilisateurInscript usrInscrForm ,
            @ModelAttribute("_method") final String method,
            final Model model)
    {
        try{
            postForm("", usrInscrForm.getDTO(), "PUT","inscript",model);
            //UUID uuid = postForm("", usrInscrForm.getDTO(), "PUT","inscript",model);
            model.addAttribute("Msg", "inscript_ok");
            return "login.html";
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            return "inscript.html";
        }catch( Exception e ){
            if(e.getMessage().equals("inscript_ok")){
                model.addAttribute("Msg", "inscript_ok");
                return "login.html";
            }
            logger.error(e.getMessage());
            model.addAttribute("Err", e.getMessage() );
            return "inscript.html";
        }
    }
}
