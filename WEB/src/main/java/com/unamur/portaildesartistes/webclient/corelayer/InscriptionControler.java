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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public Boolean ValideInscript(UtilisateurDTO usrDTO){


        if(usrDTO.getUsername().length()<4){
            throw new IllegalArgumentException("Username vide ou trop court");
        }

        if(usrDTO.getPassword().length()<4){
            throw new IllegalArgumentException("Mots de passe vide ou trop court");
        }

        //Verifie que le mots de passe n'est pas composé de chiffre ou de lettre qui se suivent ou qui sont identiques
        String value=usrDTO.getPassword();
        int intArray[]=new int[value.length()];
        for (int i=0;i<value.length();i++){
            intArray[i]=Character.getNumericValue(value.charAt(i));
        }
        int score=1;
        for(int i=0;i<intArray.length-1;i++){
            if(intArray[i]==intArray[i+1]){
                score++;
            }else if (intArray[i]==intArray[i+1]+1||intArray[i]==intArray[i+1]-1){
                score++;
            }
        }
        if(score==intArray.length){
            throw new IllegalArgumentException("Mots de passe trop faible");
        }

        if(usrDTO.getPassword().length()>12){
            throw new IllegalArgumentException("Mots de passe trop long");
        }

        if(usrDTO.getCitoyen().getNom().length()<2){
            throw new IllegalArgumentException("Nom vide ou trop court");
        }

        if(usrDTO.getCitoyen().getPrenom().isEmpty()){
            throw new IllegalArgumentException("Prénom vide");
        }

        if(usrDTO.getCitoyen().getResideAdr().getRue().isEmpty()){
            throw new IllegalArgumentException("Rue vide");
        }

        if(usrDTO.getCitoyen().getResideAdr().getNumero().isEmpty()){
            throw new IllegalArgumentException("Numéro de rue vide");
        }

        //Vérification du format du numéro de rue
        String numRue=usrDTO.getCitoyen().getResideAdr().getNumero();
        for(int i=0;i<numRue.length();i++){
            int a=Character.getNumericValue(numRue.charAt(i));
            if(a>9||a<0){
                throw new IllegalArgumentException("Numéro de rue format incorrect");
            }
        }

        //Vérification du format de la localité
        String localite=usrDTO.getCitoyen().getResideAdr().getVille();
        for(int i=0;i<localite.length();i++){
            int a=Character.getNumericValue(localite.charAt(i));
            if(a<=9&&a>=0){
                throw new IllegalArgumentException("Localité format incorrect");
            }
        }

        //Vérification du format du numéro de téléphone
        String telephone=usrDTO.getCitoyen().getTel();
        for(int i=0;i<telephone.length();i++){
            int a=Character.getNumericValue(telephone.charAt(i));
            if(a>9||a<0){
                throw new IllegalArgumentException("Numéro de téléphone format incorrect");
            }
        }

        //Vérification du format du numéro de Gsm
        String gsm=usrDTO.getCitoyen().getGsm();
        for(int i=0;i<gsm.length();i++){
            int a=Character.getNumericValue(gsm.charAt(i));
            if(a>9||a<0){
                throw new IllegalArgumentException("Numéro de Gsm format incorrect");
            }
        }

        //Vérification du format du mail
        Pattern patternMail=Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
        Matcher testMail= patternMail.matcher(usrDTO.getCitoyen().getMail());
        if(!testMail.matches()){
            throw new IllegalArgumentException("Adresse mail format incorrect");
        }

        if(usrDTO.getCitoyen().getResideAdr().getVille().isEmpty()){
            throw new IllegalArgumentException("Ville vide");
        }

        if(usrDTO.getCitoyen().getNation().length()<3){
            throw new IllegalArgumentException("Nationnalité vide ou trop court");
        }

        if(usrDTO.getCitoyen().getNrn().length()!=11){
            throw new IllegalArgumentException("Numéro de registre national incorrect");
        }

        //Contrôle de la validité de la valeur reprise dans le NRN
        int nrn=Integer.parseInt(usrDTO.getCitoyen().getNrn());
        int val=nrn/100;
        //Date date=new Date(01-01-2000);
        //if(usrDTO.getCitoyen().getDateNaissance().after(date)){
            val=val+2000000000;
        //}
        int valControle = 97 - (val) % 97;
        if (valControle==0){
            valControle=97;
        }
        if(valControle!=(nrn%100)){
            throw new IllegalArgumentException("Numéro de registre national incorrect");
        }

        return true;
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

        ValideInscript(usrDTO);

        HttpHeaders headersRest = new HttpHeaders();
        //headersRest.setContentType( yaml );
logger.error( "username:"+usrDTO.getUsername() );
        ModelMap paramRest = new ModelMap();
        paramRest.addAttribute("usrForm", usrDTO );

        HttpEntity<ModelMap> request = new HttpEntity<>( paramRest, headersRest );
        String resp=null;

        MultiValueMap<String,String> paramClient = new LinkedMultiValueMap<>();
        try{
            resp = restTemplateHelper.postForEntity( String.class , configurationService.getUrl() + "/inscript", usrDTO , headersRest );
            logger.debug("Inscription OK : "+ resp );
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
