package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.DTO;
import com.unamur.portaildesartistes.webclient.RestTemplateHelper;
import com.unamur.portaildesartistes.webclient.dataForm.DataForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.management.ServiceNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Controler<T extends DTO , U extends java.lang.Class<T> , V extends DataForm<T> > {
    private static final Logger logger = LoggerFactory.getLogger(Controler.class);

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public String handleHttpMediaTypeNotAcceptableException() {
        return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
    }

	/*************************************
	* Config
	*
	**************************************/
    @Autowired
    private RestTemplateHelper restTemplateHelper;

    @Autowired
    private PropertiesConfigurationService configurationService ;

    @Autowired
    @Qualifier("getMediaTypeYaml")
    protected MediaType yaml;

    private HttpHeaders initHeadersRest(String cookieValue){
        HttpHeaders headers = new HttpHeaders( );
        headers.add("Cookie",cookieValue);
        List<MediaType> accept = new ArrayList<>();
        accept.add(MediaType.APPLICATION_JSON );
        headers.setAccept( accept );
        return headers;
    }
	/*************************************
	* Protected (inner actions -> Rest Call)
	*
	**************************************/

	//Complete les données d'un formulaire qui sera affiché
    protected String getForm( String cookieValue,T objDTO,UUID itemId,U clazz,String formAction,Model model){
        HttpHeaders headers = initHeadersRest(cookieValue);
        String className = objDTO.getClass().getSimpleName().substring(0,objDTO.getClass().getSimpleName().length()-3);
        switch( formAction.toUpperCase() ){
            case "PUT":
                break;
            case "POST":
            case "GET":
                try{
                    //charge les données qui préremplissent la page
                    objDTO = restTemplateHelper.getForEntity( clazz , configurationService.getUrl()+"/gestion"+className+"/"+itemId , headers );
                }
                catch(ClassCastException e){
                    logger.error( e.getMessage() );
                }
                break;
            default:
        }
        model.addAttribute("form", objDTO );
        return className+"/"+formAction+".html";
    }

    protected String postForm( String cookieValue,final V form,final String method,Model model){
        logger.error("postForm1");
		return postForm(cookieValue,form,method,model,"");
    }
    protected String postForm( String cookieValue,final V form,final String method,Model model,String newUri){
        logger.error("postForm2");
        T objDTO;
        try{
            objDTO = form.getDTO();
			return postForm(cookieValue,objDTO,method,model);
        }catch(Exception e){
            logger.error( e.getMessage() );
            return "?";
        }
    }
    protected String postForm( String cookieValue,T objDTO,String method,Model model) {
        logger.error("postForm3");
        return postForm( cookieValue,objDTO,method,model,"");
    }
    protected String postForm( String cookieValue,T objDTO,String method,Model model,String newUri){
        logger.error("postForm4");
        HttpHeaders headers = initHeadersRest(cookieValue);
        String className = objDTO.getClass().getSimpleName().substring(0,objDTO.getClass().getSimpleName().length()-3);
        try{
            switch(method.toUpperCase()){
                case "PUT":
                    logger.error( "Appel REST PUT" );
                    UUID uuid = restTemplateHelper.putForEntity( UUID.class, configurationService.getUrl()+"/"+( (! newUri.equals("") )?newUri:("gestion"+className+"/")) , objDTO , headers );
                    objDTO.setId(uuid);
                    break;
                case "POST":
                    logger.error( "Appel REST POST" );
                    restTemplateHelper.postForEntity( configurationService.getUrl()+"/"+((! newUri.equals(""))?newUri:("gestion"+className+"/")) , objDTO , headers );
                    break;
                default :
                    logger.error( "Appel REST : "+method );
            }
        }
        catch(ClassCastException e){
            logger.error( e.getMessage() );
        }
        model.addAttribute("form", objDTO );
        return className+"/get.html";
    }

    protected List<T> listObj( String cookieValue,T objDTO, U clazz ){
        HttpHeaders headers = initHeadersRest(cookieValue);
        String className = objDTO.getClass().getSimpleName().substring(0,objDTO.getClass().getSimpleName().length()-3);
        List ret = new ArrayList<T>();
        try {
            logger.error("Appel REST");
            return restTemplateHelper.getForList(clazz, configurationService.getUrl() + "/gestion" + className + "/", headers);
        } catch (AuthenticationServiceException e) {
            logger.error("Connexion refusée par authentification back-end : " + e.toString());
            return ret;
        } catch (HttpClientErrorException.Unauthorized e) {
            logger.error("Connexion refusée par authentification back-end : " + e.toString());
            return ret;
        } catch (HttpClientErrorException.Forbidden e) {
            logger.error("Connexion refusée par authentification back-end : " + e.toString());
            return ret;
        } catch (HttpServerErrorException.InternalServerError e) {
            logger.error("Connexion perdue au back-end : " + e.toString());
            return ret;
        } catch (HttpServerErrorException e) {
            logger.error("Autre erreur du back-end : " + e.toString());
            return ret;
        }catch(ServiceNotFoundException e){
            logger.error( "ServiceNotFoundException" + e.getMessage() );
            return ret;
            //HttpServletResponse.SC_UNAUTHORIZED
        }catch(ClassCastException e){
            logger.error( e.getMessage() );
            return ret;
        }
    }

    protected String list( String cookieValue,T objDTO, U clazz ,Model model){
        HttpHeaders headers = initHeadersRest(cookieValue);
        String className = objDTO.getClass().getSimpleName().substring(0,objDTO.getClass().getSimpleName().length()-3);

        try {
            logger.error("Appel REST");
            model.addAttribute("form", restTemplateHelper.getForList(clazz, configurationService.getUrl() + "/gestion" + className + "/", headers) );

        } catch (AuthenticationServiceException e) {
            logger.error("Connexion refusée par authentification back-end : " + e.toString());
            return "/login";
        } catch (HttpClientErrorException.Unauthorized e) {
            logger.error("Connexion refusée par authentification back-end : " + e.toString());
            return "/login";
        } catch (HttpClientErrorException.Forbidden e) {
            logger.error("Connexion refusée par authentification back-end : " + e.toString());
            return "/login";
        } catch (HttpServerErrorException.InternalServerError e) {
            logger.error("Connexion perdue au back-end : " + e.toString());
            return "/login";
        } catch (HttpServerErrorException e) {
            logger.error("Autre erreur du back-end : " + e.toString());
            return "/login";
        }catch(ServiceNotFoundException e){
            logger.error( "ServiceNotFoundException" + e.getMessage() );
            return "/login";
            //HttpServletResponse.SC_UNAUTHORIZED
        }catch(ClassCastException e){
            logger.error( e.getMessage() );
        }
        return className+"/list.html";
    }

    protected String delete( String cookieValue,T objDTO,UUID itemId,Model model ){
        HttpHeaders headers = initHeadersRest(cookieValue);
        String className = objDTO.getClass().getSimpleName().substring(0,objDTO.getClass().getSimpleName().length()-3);

        restTemplateHelper.delete(configurationService.getUrl()+"/gestion"+className+"/"+itemId , headers );
        return className+"/list.html";
    }

	/*************************************
	* Public
	*
	**************************************/

    public Boolean isValide(V form){

        T objDTO=null;
        try{
            objDTO = form.getDTO();
            return true;
        }
        catch(IllegalArgumentException e){
            return false;
        }
        catch(ParseException e){
            return false;
        }
    }
}
