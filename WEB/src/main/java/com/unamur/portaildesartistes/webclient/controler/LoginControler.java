package com.unamur.portaildesartistes.webclient.controler;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.webclient.RestTemplateHelper;
import com.unamur.portaildesartistes.webclient.dataForm.Utilisateur;
import com.unamur.portaildesartistes.webclient.security.UserDetailsServiceWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginControler {
    private static final Logger logger = LoggerFactory.getLogger(LoginControler.class);

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public String handleHttpMediaTypeNotAcceptableException() {
        return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
    }

    @Autowired
    UtilisateurControler usrCtrl;

    @Autowired
    private RestTemplateHelper restTemplateHelper;
    @Autowired
    private PropertiesConfigurationService configurationService ;

    @GetMapping(value="/accueil")
    public String acceuilhtml(@CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,Model model) {
        try {
            usrCtrl.setRoles(cookieValue, model);
        }catch (Exception e){
            model.addAttribute("Err",e.getMessage() );
            return "login.html";
        }
        return "accueil.html";
    }

    @GetMapping(value="/home")
    public String homehtml(@CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,Model model) {
        try {
            usrCtrl.setRoles(cookieValue, model);
        }catch (Exception e){
            model.addAttribute("Err",e.getMessage() );
            return "login.html";
        }
        return "home.html";
    }

    @GetMapping(value="/ChangePassword")
    public String changePasswordhtml(@CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,Model model) {
        try {
            usrCtrl.setRoles(cookieValue, model);
        }catch (Exception e){
            model.addAttribute("Err",e.getMessage() );
            return "login.html";
        }
        return "Changepassword.html";
    }
    @GetMapping(value="/choixRole")
    public String choixRolehtml(@CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,Model model) {
        try {
            usrCtrl.setRoles( cookieValue, model);
        }catch (Exception e){
            model.addAttribute("Err",e.getMessage() );
            return "login.html";
        }
        return "choixRole.html";
    }
    @GetMapping(value="/artiste")
    public String artistehtml(@CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue,Model model) {
        try {
            usrCtrl.setRoles(cookieValue, model);
        }catch (Exception e){
            model.addAttribute("Err",e.getMessage() );
            return "login.html";
        }
        return "artiste.html";
    }

    @GetMapping(value="/mainCSS.css")
    public String maincss(Model model) {
        return "mainCSS.css";
    }

    @GetMapping(value="/logo.png")
    public String logoPng(Model model) {
        return "logo.png";
    }
    @GetMapping(value="/LogoPortail.png")
    public String LogoPortailPng(Model model) {
        return "LogoPortail.png";
    }
    @GetMapping(value="/magritte.png")
    public String magrittePng(Model model) {
        return "magritte.png";
    }
    @GetMapping(value="/spfblabla.png")
    public String spfblablaPng(Model model) {
        return "spfblabla.png";
    }
    @GetMapping(value="/favicon.ico")
    public String favicon(Model model) {
        return "favicon.ico";
    }
    @GetMapping(value="/bt_detail.png")
    public String bt_detail(Model model) { return "bt_detail.png"; }
    @GetMapping(value="/bt_modif.png")
    public String bt_modif(Model model) { return "bt_modif.png"; }
    @GetMapping(value="/bt_del.png")
    public String bt_del(Model model) { return "bt_del.png"; }

    @GetMapping(value = "/login")
    public String loginView( @ModelAttribute("lang") String lang
            ,@ModelAttribute("error") String Err
            ,@ModelAttribute("message") String Msg
            ,Model model ){
        String Message="";
        String Erreur="";
        switch (Err){
            case "401":
            case "403":
                Erreur="badLogin";
                break;
            case "404":
                Erreur="restNotFound";
                break;
            default:
                Erreur="";
        }
        model.addAttribute("Msg",Message);
        model.addAttribute("Err",Erreur);
        return "login.html";
    }

    @Autowired
    UserDetailsServiceWeb uDS;

    //Envoi des identifiants de connexion pour authentification
    @PostMapping(value = "/login")
    public ResponseEntity<String> authenticate(
            @ModelAttribute("form") Utilisateur usr ,
            final Model m)
    {
        /*logger.error( "password front:"+usrDTO.getPassword()+" -> "+encoder.encode( usrDTO.getPassword() ));
        usrDTO.setPassword( encoder.encode( usrDTO.getPassword() ) );*/
        logger.error( "username:"+usr.getUsername() );
        logger.error( "new password front:"+usr.getPassword() );

        ResponseEntity<String> reponseRest;
        MultiValueMap<String,String> paramClient = new LinkedMultiValueMap<>();
        String msgAuClient;
        ResponseEntity<String> reponseAuClient;

        UtilisateurDTO usrDTO=null;
        try{
            usrDTO = usr.getDTO();
            HttpHeaders headersRest = new HttpHeaders();
            //headersRest.setContentType( yaml );
            MultiValueMap<String, String> paramRest = new LinkedMultiValueMap<>();
            paramRest.add("username", usrDTO.getUsername());
            paramRest.add("password", usrDTO.getPassword());
            try {
                /* ici test match? encodage supplémentaire */
                //UserDetails uD = uDS.loadUserByUsername( usrDTO.getUsername() );
                //uD.
                /**/
                reponseRest = restTemplateHelper.postForAuth(configurationService.getUrl() + "/Authentification", paramRest, headersRest);
                logger.debug( reponseRest.getBody() );

                logger.debug("Authentification OK");
                logger.debug("Session : " + reponseRest.getHeaders().get("Set-Cookie").toString());

                //transfert au front-end
                paramClient.add("Set-Cookie",
                        //prends le header coté back-end générant un cookie(avec le n° de session)
                        reponseRest.getHeaders().get("Set-Cookie").get(0)
                                //transforme le contexte (répertoire du front-end à la place du back-end)
                                .replace(" Path=" + configurationService.getBackEndPath() + ";",
                                        " Path=" + configurationService.getFrontEndPath() + ";")
                );
                paramClient.add("Location", "choixRole");

                msgAuClient=reponseRest.getBody();
            } catch (HttpClientErrorException.Unauthorized e) {
                        logger.error("Connexion refusée par authentification back-end : " + e.toString());
                        paramClient.add("Location", "login?error=401");
                msgAuClient="Erreur : "+e.getMessage();
            } catch (HttpClientErrorException.Forbidden e) {
                        logger.error("Connexion refusée par back-end car interdit : " + e.toString());
                        paramClient.add("Location", "login?error=403");
                msgAuClient="Erreur : " + e.getMessage() + "(voir logs)";
            } catch (HttpClientErrorException.NotFound e) {
                        logger.error("Connexion refusée par back-end car rest introuvable : " + e.toString());
                        paramClient.add("Location", "login?error=404");
                msgAuClient="Erreur : " + e.getMessage() + "(voir logs)";
            } catch (HttpClientErrorException.NotAcceptable e) {
                        logger.error("Connexion refusée par back-end car réponse pas acceptable : " + e.toString());
                msgAuClient="Erreur : " + e.getMessage() + "(voir logs)";
            } catch (HttpClientErrorException.UnsupportedMediaType e) {
                        logger.error("Connexion refusée par back-end car média pas supporté : " + e.toString());
                msgAuClient="Erreur : " + e.getMessage() + "(voir logs)";
            } catch (HttpClientErrorException e) {
                        logger.error(e.getMessage());
                        logger.error(e.toString());
                        logger.error(e.getCause() == null ? "" : e.getCause().getMessage());
                msgAuClient="Erreur : " + e.getMessage() + "(voir logs)";
            } catch (ResourceAccessException e) {
                logger.error("Serveur back-end indisponible : " + e.getMessage());
                msgAuClient="Serveur back-end indisponible (voir logs)";
            } catch (RestClientException e) {
                logger.error(e.getMessage());
                msgAuClient="Serveur front-end converter non dispo (voir logs)";
            }catch (Exception e) {
                logger.error(e.toString());
                logger.error(e.getClass().toString());
                logger.error(e.getMessage());
                logger.error(e.getCause() == null ? "" : e.getCause().getMessage());
                msgAuClient="Autre erreur non gérée (voir logs)";
            }
        }
        catch(IllegalArgumentException e){
            msgAuClient = e.getMessage();
            paramClient.add("Err",e.getMessage());
            paramClient.add("Location","login?error=401");
        }
        reponseAuClient = new ResponseEntity<>(msgAuClient, paramClient, HttpStatus.SEE_OTHER);
        return reponseAuClient;
    }

    @GetMapping( value = "/logout" )
    public String logout( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ) {
        return logout2(cookieValue);
    }
    @GetMapping( value = "/logout2" )
    public String logout2( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue ) {
        logger.debug("Logout : Authentication received! Cookie : " + cookieValue);

        HttpHeaders headers = new HttpHeaders( );
        headers.add("Cookie",cookieValue);
        List<MediaType> accept = new ArrayList<>();
        //accept.add( yaml );
        //accept.add(MediaType.TEXT_XML);
        accept.add(MediaType.APPLICATION_JSON );
        headers.setAccept( accept );
        try{
            restTemplateHelper.getForEntity( String.class ,configurationService.getUrl()+"/logout", headers );
            return "login.html";
        }catch( Exception e ){
            return "/login.html";
        }
    }

}