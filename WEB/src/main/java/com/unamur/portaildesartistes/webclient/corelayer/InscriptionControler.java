package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.AdresseDTO;
import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.DTO.CustomWrapper;
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
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
    private RestTemplate restTemplate;

    @Autowired
    private PropertiesConfigurationService configurationService ;

    @GetMapping(value = "/inscript")
    public String loginView( Model model ){
        UtilisateurDTO usrForm = new UtilisateurDTO();
        usrForm.setUsername("test");
        usrForm.setCitoyen( new CitoyenDTO() );
        usrForm.getCitoyen().setNom("MonNom");
        usrForm.getCitoyen().setResideAdr( new AdresseDTO() );
        usrForm.getCitoyen().getResideAdr().setRue("Ch");

        model.addAttribute("usrForm",usrForm);

        return "inscript.html";
    }

    @PostMapping(value = "/inscript")
    public ResponseEntity<String> inscript(
            @Valid @ModelAttribute("userForm") final UtilisateurDTO usrDTO ,
            final BindingResult br ,
            final Model m)
    {
        if(br.hasErrors())
        {
            System.out.printf("Found %d fields!%n" , br.getErrorCount());
        }

        HttpHeaders headersRest = new HttpHeaders();
        //headersRest.setContentType( yaml );
logger.error( "username:"+usrDTO.getUsername() );
        ModelMap paramRest = new ModelMap();

        CustomWrapper<UtilisateurDTO> wrp = new CustomWrapper<>();
        wrp.setObject(usrDTO);

        paramRest.addAttribute("wrp", wrp );

        HttpEntity<ModelMap> request = new HttpEntity<>( paramRest, headersRest );
        ResponseEntity<String> resp=null;
        //CustomWrapper<UtilisateurDTO> reponseRest;

        MultiValueMap<String,String> paramClient = new LinkedMultiValueMap<>();
        try{
            resp = restTemplate.postForEntity(configurationService.getUrl() + "/inscript", wrp, String.class);
            //reponseRest = restTemplate.postForObject(configurationService.getUrl() + "/inscript", request, CustomWrapper.class , paramRest );
            //resp = new ResponseEntity<>( reponseRest.getObject().getId().toString() , HttpStatus.OK );
            if( resp.getStatusCodeValue() !=200){
                logger.error("Réponse du serveur: "+resp.getStatusCodeValue() );
            }else{
                logger.debug("Inscription OK : "+ resp.getBody() );
            }
        }
        catch( HttpClientErrorException e){
            switch( e.getMessage() ){
                case "401":
                    logger.error( "Connexion refusée par authentification back-end : "+ e.toString() );
                    break;
                case "403":
                    logger.error( "Connexion refusée par back-end car interdit : "+ e.toString() );
                    break;
                case "404":
                    logger.error( "Connexion refusée par back-end car rest introuvable : "+ e.toString() );
                    break;
                case "406":
                    logger.error( "Connexion refusée par back-end car réponse pas acceptable : "+ e.toString() );
                    break;
                case "415":
                    logger.error( "Connexion refusée par back-end car média pas supporté : "+ e.toString() );
                    break;
                default:
                    logger.error( e.getMessage() );
                    logger.error( e.toString() );
                    logger.error( e.getCause()==null?"":e.getCause().getMessage() );
            }
            resp = new ResponseEntity<>( "Connexion refusée par back-end : "+e.getMessage() +"(voir logs)" , HttpStatus.OK );
        }
        catch( ResourceAccessException e){
            logger.error( "Serveur back-end indisponible : "+e.getMessage() );
            resp = new ResponseEntity<>( "Serveur back-end indisponible (voir logs)" , HttpStatus.OK );
        }
        catch(RestClientException e) {
            logger.error( "RestClientException : "+e.getMessage()+e.getLocalizedMessage() );
            resp = new ResponseEntity<>( "Serveur back-end en erreur REST (voir logs)" , HttpStatus.OK );
        }
        catch( Exception e){
            logger.error( e.toString() );
            logger.error( e.getClass().toString() );
            logger.error( e.getMessage() );
            logger.error( e.getCause()==null?"":e.getCause().getMessage() );
            //reponseRest
            resp = new ResponseEntity<>( "Autre erreur non gérée (voir logs)" , HttpStatus.OK );
        }

        ResponseEntity<String> reponseAuClient = new ResponseEntity<>( resp.getBody() , paramClient , HttpStatus.OK );
        return reponseAuClient;
    }
}
