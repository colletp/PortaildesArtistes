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
            @Valid @ModelAttribute("form") final UtilisateurInscript usrInscrForm ,
            @ModelAttribute("_method") final String method,
            final BindingResult br ,
            final Model model)
    {
        if(br.hasErrors())
        {
            System.out.printf("Found %d fields!%n" , br.getErrorCount());
        }
        try{
            postForm("", usrInscrForm.getDTO(), "PUT","inscript",model);
            model.addAttribute("Msg", "Inscription effectuée. Veuillez vous connecter");
            return "login.html";
        }catch(IllegalArgumentException e){
            model.addAttribute("Err",e.getMessage());
            return "inscript.html";
        }catch( Exception e ){
            model.addAttribute("Err",e.getMessage());
            return "/login.html";
        }
    }
}
