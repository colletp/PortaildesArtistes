package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginControler {

    private static final Logger logger = LoggerFactory.getLogger(LoginControler.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PropertiesConfigurationService configService;

    @GetMapping(value = "/login")//initialisation du login
    public String loginView( Model model ){
        CitoyenDTO userDto = new CitoyenDTO();
        model.addAttribute("userForm", userDto);
        return "login.html";
    }

    @Autowired
    private PropertiesConfigurationService configurationService ;

    @PostMapping(value = "/login2" )//initialisation du login
    public ResponseEntity<String> authenticate(
            @Valid @ModelAttribute("userForm") final CitoyenDTO usrDTO ,
            final BindingResult br ,
            final Model m)
    {
        System.out.printf("Authentication received!%n");
        System.out.printf("LOGIN: %s%n" , usrDTO.getUsername() );
        System.out.printf("PASS: %s%n" , usrDTO.getPassword() );
        if(br.hasErrors())
        {
            System.out.printf("Found %d fields!%n" , br.getErrorCount());
        }
        Map<String,String> param = new HashMap<>();
        param.put("username",usrDTO.getUsername() );
        param.put("password",usrDTO.getPassword() );

        ResponseEntity<String> reponseServeur = (ResponseEntity<String>) restTemplate.postForObject( configurationService.getUrl()+"/Authentification", null , ResponseEntity.class , param );

        int codeReponseServeur= reponseServeur.getStatusCodeValue();
        if(codeReponseServeur!=200){
            logger.error("Réponse du serveur: "+codeReponseServeur+" ==> Serveur indisponible, votre application ne fonctionnera pas correctement");
        }else{
            logger.error("Réponse du serveur: "+ reponseServeur.getBody() );
        }
        logger.error( reponseServeur.getHeaders().toSingleValueMap().toString() );
        return new ResponseEntity<>( reponseServeur.getBody() , HttpStatus.OK);

        //return "login2.html";
    }

}