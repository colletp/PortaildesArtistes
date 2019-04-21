package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginControler {
    private static final Logger logger = LoggerFactory.getLogger(LoginControler.class);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PropertiesConfigurationService configurationService ;

    @GetMapping(value = "/login")//initialisation du login
    public String loginView( Model model ){

        return "login.html";
    }


    @PostMapping(value = "/login2")//initialisation du login
    public ResponseEntity<String> authenticate(
            @Valid @ModelAttribute("userForm") final UtilisateurDTO usrDTO ,
            final BindingResult br ,
            final Model m)
    {
        if(br.hasErrors())
        {
            System.out.printf("Found %d fields!%n" , br.getErrorCount());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_FORM_URLENCODED );

        MultiValueMap<String, String> param= new LinkedMultiValueMap<>();
        param.add("username",usrDTO.getUsername() );
        param.add("password",usrDTO.getPassword() );

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(param, headers);

        ResponseEntity<String> reponseServeur = restTemplate.postForEntity( configurationService.getUrl()+"/Authentification", request , String.class );

        int codeReponseServeur= reponseServeur.getStatusCodeValue();
        if(codeReponseServeur!=200){
            logger.error("Réponse du serveur: "+codeReponseServeur+" ==> Serveur indisponible, votre application ne fonctionnera pas correctement");
        }else{
            logger.debug("Réponse du serveur: "+ reponseServeur.getBody() );
        }
        logger.debug( "Session : "+ reponseServeur.getHeaders().get( "Set-Cookie").toString() );

        MultiValueMap<String,String> paramClient = new LinkedMultiValueMap<>();
        paramClient.add("Set-Cookie", reponseServeur.getHeaders()
                .get( "Set-Cookie").get(0)
                .replace( " Path="+configurationService.getBackEndPath()+";",
                      " Path="+configurationService.getFrontEndPath()+";"
                    )
        );

        ResponseEntity<String> reponseAuClient = new ResponseEntity<String>( paramClient,HttpStatus.OK );
        return reponseAuClient;
    }

    @GetMapping(value = "/gestionUtilisateur/list")//initialisation du login
    public ResponseEntity<String> test( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ){
        logger.debug("Authentication received!");
        logger.debug("Cookie : "+cookieValue );

        HttpHeaders headers = new HttpHeaders( );

        headers.add("Cookie",cookieValue);

        List<MediaType> accept = new ArrayList<>();
        accept.add( MediaType.valueOf("text/yaml") );
        //accept.add(MediaType.TEXT_XML);
        accept.add(MediaType.APPLICATION_JSON );
        logger.error( "count acceptable:"+accept.size() );
        headers.setAccept( accept );
        HttpEntity<String> request = new HttpEntity<>( "" , headers);

        //ResponseEntity<String> reponseServeur = restTemplate.getForEntity( configurationService.getUrl()+"/gestionUtilisateur/list", String.class ,headers );
        //HttpEntity<CitoyenDTO> request = new HttpEntity<>(new CitoyenDTO());//?

        List<CitoyenDTO> reponseServeur = restTemplate.getForObject( configurationService.getUrl()+"/gestionUtilisateur/list" , List.class , headers );

        ResponseEntity ret = new ResponseEntity<>( reponseServeur , HttpStatus.OK );
        return ret;
    }

}