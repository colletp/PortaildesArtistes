package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.AdresseDTO;
import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.webclient.RestTemplateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

@Controller
public class InscriptionControler {

    private static final Logger logger = LoggerFactory.getLogger(com.unamur.portaildesartistes.webclient.corelayer.LoginControler.class);

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public String handleHttpMediaTypeNotAcceptableException() {
        return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
    }

    @Autowired
    @Qualifier("getMediaTypeYaml")
    MediaType yaml;

    @Autowired
    private RestTemplateHelper restTemplateHelper;

    @Autowired
    private PropertiesConfigurationService configurationService ;

    @GetMapping(value = "/inscript")
    public String loginView( Model model ){
        UtilisateurDTO usr = new UtilisateurDTO();
        usr.setUsername("test");
        usr.setCitoyen( new CitoyenDTO() );
        usr.getCitoyen().setNom("MonNom");
        usr.getCitoyen().setResideAdr( new AdresseDTO() );
        usr.getCitoyen().getResideAdr().setRue("Ch");

        model.addAttribute("usrForm",usr);

        return "inscript.html";
    }

    @PostMapping(value = "/inscript")
    public ResponseEntity<String> inscript(
            @Valid @ModelAttribute("userForm") final UtilisateurDTO usrDTO ,
            @ModelAttribute("_method") final String method,
            final BindingResult br ,
            final Model m)
    {
        if(br.hasErrors())
        {
            System.out.printf("Found %d fields!%n" , br.getErrorCount());
        }

        if( usrDTO.getCitoyen().getNom().length() < 2 ){
            throw new IllegalArgumentException("Nom vide ou trop court");
        }


        HttpHeaders headersRest = new HttpHeaders();
        //headersRest.setContentType( yaml );
logger.error( "username:"+usrDTO.getUsername() );
        ModelMap paramRest = new ModelMap();
        paramRest.addAttribute("usrForm", usrDTO );

        HttpEntity<ModelMap> request = new HttpEntity<>( paramRest, headersRest );

        MultiValueMap<String,String> paramClient = new LinkedMultiValueMap<>();
        String resp="";
        try{
            switch(method.toUpperCase()){
                case "PUT":
                    logger.error( "Appel REST PUT" );

                    UUID uuid = restTemplateHelper.putForEntity( UUID.class , configurationService.getUrl() + "/inscript", usrDTO , headersRest );
                    usrDTO.setId(uuid);
                    resp = "Inscription OK : "+ uuid;
                    break;
                default :
                    logger.error( "Appel REST : "+method );
                    resp="Appel REST : "+method;
            }



        }
        catch( HttpClientErrorException e){
            logger.error("Réponse du serveur: "+e.getStatusCode().toString() );
            switch( e.getStatusCode().value() ){
                case 401:
                    logger.error( "Connexion refusée par authentification back-end : "+ e.toString() );
                    break;
                case 403:
                    logger.error( "Connexion refusée par back-end car interdit : "+ e.toString() );
                    break;
                case 404:
                    logger.error( "Connexion refusée par back-end car rest introuvable : "+ e.toString() );
                    break;
                case 406:
                    logger.error( "Connexion refusée par back-end car réponse pas acceptable : "+ e.toString() );
                    break;
                case 415:
                    logger.error( "Connexion refusée par back-end car média pas supporté : "+ e.toString() );
                    break;
                default:
                    logger.error( e.getMessage() );
                    logger.error( e.toString() );
                    logger.error( e.getCause()==null?"":e.getCause().getMessage() );
            }
            resp = "Connexion refusée par back-end : "+e.getMessage() +"(voir logs)";
        }
        catch( ResourceAccessException e){
            logger.error( "Serveur back-end indisponible : "+e.getMessage() );
            resp = "Serveur back-end indisponible (voir logs)";
        }
        catch(RestClientException e) {
            logger.error( "RestClientException : "+e.getMessage()+e.getLocalizedMessage() );
            resp = "Serveur back-end en erreur REST (voir logs)";
        }
        catch( Exception e){
            logger.error( e.toString() );
            logger.error( e.getClass().toString() );
            logger.error( e.getMessage() );
            logger.error( e.getCause()==null?"":e.getCause().getMessage() );
            //reponseRest
            resp = "Autre erreur non gérée (voir logs)";
        }

        ResponseEntity<String> reponseAuClient = new ResponseEntity<>( resp , paramClient , HttpStatus.OK );
        return reponseAuClient;
    }
}
