package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.webclient.RestTemplateHelper;
import com.unamur.portaildesartistes.webclient.security.UserDetailsServiceWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class LoginControler {
    private static final Logger logger = LoggerFactory.getLogger(LoginControler.class);

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public String handleHttpMediaTypeNotAcceptableException() {
        return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
    }

    @Autowired
    private RestTemplateHelper restTemplateHelper;

    @Autowired
    private PropertiesConfigurationService configurationService ;

    @GetMapping(value="/mainCSS.css")
    public String css(Model model) {
        return "mainCSS.css";
    }

    @GetMapping(value = "/login")//initialisation du login
    public String loginView( @ModelAttribute("lang") String lang,Model model ){
        logger.error("lang:"+lang);
        return "login.html";
    }

    @Autowired
    @Qualifier("getMediaTypeYaml")
    MediaType yaml;

    //@Autowired
    //PasswordEncoder encoder;

    @Autowired
    UserDetailsServiceWeb uDS;

    @PostMapping(value = "/login" //,consumes = "text/yaml",produces = "text/yaml"
            )
    //initialisation du login
    public ResponseEntity<String> authenticate(
            @Valid @ModelAttribute("userForm") final UtilisateurDTO usrDTO ,
            final BindingResult br ,
            final Model m)
    {
        /*logger.error( "password front:"+usrDTO.getPassword()+" -> "+encoder.encode( usrDTO.getPassword() ));
        usrDTO.setPassword( encoder.encode( usrDTO.getPassword() ) );*/
        logger.error( "new password front:"+usrDTO.getPassword() );
        if(br.hasErrors())
        {
            System.out.printf("Found %d fields!%n" , br.getErrorCount());
        }

        HttpHeaders headersRest = new HttpHeaders();
        //headersRest.setContentType( yaml );
        MultiValueMap<String, String> paramRest= new LinkedMultiValueMap<>();
        paramRest.add("username",usrDTO.getUsername() );
        paramRest.add("password",usrDTO.getPassword() );
        ResponseEntity<String> reponseRest;
        MultiValueMap<String,String> paramClient = new LinkedMultiValueMap<>();
        try{
            /* ici test match? encodage supplémentaire */
            //UserDetails uD = uDS.loadUserByUsername( usrDTO.getUsername() );
            //uD.
            /**/
            reponseRest = restTemplateHelper.postForAuth( String.class , configurationService.getUrl() + "/Authentification", paramRest , headersRest );
            logger.debug( "Session : "+ reponseRest.getHeaders().get( "Set-Cookie" ).toString() );
            //transfert au front-end
            paramClient.add("Set-Cookie",
                    //prends le header coté back-end générant un cookie(avec le n° de session)
                    reponseRest.getHeaders().get( "Set-Cookie").get(0)
                            //transforme le contexte (répertoire du front-end à la place du back-end)
                            .replace( " Path="+configurationService.getBackEndPath()+";",
                                    " Path="+configurationService.getFrontEndPath()+";" )
            );
            if( reponseRest.getStatusCodeValue() !=200){
                logger.error("Réponse du serveur: "+reponseRest.getStatusCodeValue() );
            }else{
                logger.debug("Authentification OK : "+ reponseRest.getBody() );
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
            reponseRest = new ResponseEntity<>( "Connexion refusée par back-end : "+e.getMessage() +"(voir logs)" , HttpStatus.OK );
        }
        catch( ResourceAccessException e){
            logger.error( "Serveur back-end indisponible : "+e.getMessage() );
            reponseRest = new ResponseEntity<>( "Serveur back-end indisponible (voir logs)" , HttpStatus.OK );
        }
        catch(RestClientException e) {
            logger.error( e.getMessage() );
            reponseRest = new ResponseEntity<>( "Serveur front-end converter non dispo (voir logs)" , HttpStatus.OK );
        }
        catch( Exception e){
            logger.error( e.toString() );
            logger.error( e.getClass().toString() );
            logger.error( e.getMessage() );
            logger.error( e.getCause()==null?"":e.getCause().getMessage() );
            reponseRest = new ResponseEntity<>( "Autre erreur non gérée (voir logs)" , HttpStatus.OK );
        }

        ResponseEntity<String> reponseAuClient = new ResponseEntity<>( reponseRest.getBody() , paramClient , HttpStatus.OK );
        return reponseAuClient;
    }

    @GetMapping(value = "/gestionUtilisateur/list")//initialisation du login
    public ResponseEntity<String> userList( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ){
        logger.debug("userList : Authentication received! Cookie : "+cookieValue );

        HttpHeaders headers = new HttpHeaders( );
        headers.add("Cookie",cookieValue);
        List<MediaType> accept = new ArrayList<>();
        //accept.add( yaml );
        //accept.add(MediaType.TEXT_XML);
        accept.add(MediaType.APPLICATION_JSON );
        headers.setAccept( accept );

        //headers.setContentType( yaml );
        MultiValueMap<String, String> param= new LinkedMultiValueMap<>();
        //param.add("username",usrDTO.getUsername() );
        //param.add("password",usrDTO.getPassword() );
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(param, headers);
        List<CitoyenDTO> reponseServeur = restTemplateHelper.getForList( CitoyenDTO.class , configurationService.getUrl()+"/gestionUtilisateur/list" , headers );
        ResponseEntity ret = new ResponseEntity<>( reponseServeur , HttpStatus.OK );
        return ret;
    }
    @GetMapping( value = "/logout2" )
    public String logout( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ) {
        logger.debug("Logout : Authentication received! Cookie : " + cookieValue);

        HttpHeaders headers = new HttpHeaders( );
        headers.add("Cookie",cookieValue);
        List<MediaType> accept = new ArrayList<>();
        //accept.add( yaml );
        //accept.add(MediaType.TEXT_XML);
        accept.add(MediaType.APPLICATION_JSON );
        headers.setAccept( accept );

        //headers.setContentType( yaml );
        MultiValueMap<String, String> param= new LinkedMultiValueMap<>();
        //param.add("username",usrDTO.getUsername() );
        //param.add("password",usrDTO.getPassword() );
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(param, headers);
        String reponseServeur = restTemplateHelper.getForEntity( String.class ,configurationService.getUrl()+"/logout", headers );
        return "login.html";
    }

}