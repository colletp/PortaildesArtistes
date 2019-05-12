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
    protected RestTemplateHelper restTemplateHelper;

    @Autowired
    protected PropertiesConfigurationService configurationService ;

    @Autowired
    @Qualifier("getMediaTypeYaml")
    protected MediaType yaml;

    protected HttpHeaders initHeadersRest(String cookieValue){
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

    protected T getObj( String cookieValue,UUID uuid,T objDTO, U clazz ){
        HttpHeaders headers = initHeadersRest(cookieValue);
        String className = objDTO.getClass().getSimpleName().substring(0,objDTO.getClass().getSimpleName().length()-3);
        try {
            logger.error("Appel REST");
            return restTemplateHelper.getForEntity(clazz, configurationService.getUrl() + "/gestion" + className + "/"+uuid, headers);
        } catch (AuthenticationServiceException e) {
            logger.error("Connexion refusée par authentification back-end : " + e.toString());
            return null;
        } catch (HttpClientErrorException.Unauthorized e) {
            logger.error("Connexion refusée par authentification back-end : " + e.toString());
            return null;
        } catch (HttpClientErrorException.Forbidden e) {
            logger.error("Connexion refusée par authentification back-end : " + e.toString());
            return null;
        } catch (HttpServerErrorException.InternalServerError e) {
            logger.error("Connexion perdue au back-end : " + e.toString());
            return null;
        } catch (HttpServerErrorException e) {
            logger.error("Autre erreur du back-end : " + e.toString());
            return null;
        }catch(ClassCastException e){
            logger.error( e.getMessage() );
            return null;
        }
    }

	//Complete les données d'un formulaire qui sera affiché
    protected String getForm(String cookieValue, T objDTO, V objForm, UUID itemId, U clazz, String method, Model model){
    //protected String getForm( String cookieValue,V objForm,UUID itemId,U clazz,String method,Model model){
        String className = objDTO.getClass().getSimpleName().substring(0,objDTO.getClass().getSimpleName().length()-3);
        switch( method.toUpperCase() ){
            case "PUT":
                break;
            case "POST":
            case "GET":
                try{
                    objForm.setFromDTO( getObj(cookieValue,itemId,objDTO,clazz) );
                }
                catch(Exception e){
                    logger.error( e.getMessage() );
                    return "/error.html";
                }
                break;
            default:
        }
        model.addAttribute("form", objForm );
        return className+"/"+method.toUpperCase()+".html";
    }

    protected UUID getMyId( String cookieValue ){
        HttpHeaders headers = initHeadersRest(cookieValue);
        return restTemplateHelper.getForEntity(UUID.class,configurationService.getUrl()+"/gestionUtilisateur/moi",headers );
    }

    protected String postForm( String cookieValue,final V form,final String method){
		return postForm(cookieValue,form,method,"");
    }
    protected String postForm( String cookieValue,final V form,final String method,String newUri){
        T objDTO;
        try{
            objDTO = form.getDTO();
			return postForm(cookieValue,objDTO,method);
        }catch(Exception e){
            logger.error( e.getMessage() );
            return "/error.html";
        }
    }
    protected String postForm( String cookieValue,T objDTO,String method) {
        return postForm( cookieValue,objDTO,method,"");
    }
    protected String postForm( String cookieValue,T objDTO,String method,String newUri){
        HttpHeaders headers = initHeadersRest(cookieValue);
        String className = objDTO.getClass().getSimpleName().substring(0,objDTO.getClass().getSimpleName().length()-3);
        try{
            switch(method.toUpperCase()){
                case "PUT":
                    objDTO.setId(restTemplateHelper.putForEntity( UUID.class, configurationService.getUrl()+"/"+( (! newUri.equals("") )?newUri:("gestion"+className+"/")) , objDTO , headers ) );
                    break;
                case "POST":
                case "":
                    restTemplateHelper.postForEntity( configurationService.getUrl()+"/"+((! newUri.equals(""))?newUri:("gestion"+className+"/")) , objDTO , headers );
                    break;
                default :
                    logger.error( "Appel REST : "+method );
            }
        }
        catch(ClassCastException e){
            logger.error( e.getMessage() );
        }
        return className+"/get.html";
    }

    protected List<T> list( String cookieValue,T objDTO, U clazz)throws Exception {
        String className = objDTO.getClass().getSimpleName().substring(0, objDTO.getClass().getSimpleName().length() - 3);
        HttpHeaders headers = initHeadersRest(cookieValue);

        logger.error("Appel REST");
        try {
            return restTemplateHelper.getForList(clazz, configurationService.getUrl() + "/gestion" + className + "/", headers);
        } catch (AuthenticationServiceException e) {
            logger.error("Connexion refusée par authentification back-end : " + e.toString());
            throw new Exception( "Connexion refusée par authentification back-end : " + e.toString() );
        } catch (HttpClientErrorException.Unauthorized e) {
            logger.error("Connexion refusée par authentification back-end : " + e.toString());
            throw new Exception("Connexion refusée par authentification back-end : " + e.toString());
        } catch (HttpClientErrorException.Forbidden e) {
            logger.error("Connexion refusée par authentification back-end : " + e.toString());
            throw new Exception("Connexion refusée par authentification back-end : " + e.toString());
        } catch (HttpServerErrorException.InternalServerError e) {
            logger.error("Connexion perdue au back-end : " + e.toString());
            throw new Exception("Connexion perdue au back-end : " + e.toString());
        } catch (HttpServerErrorException e) {
            logger.error("Autre erreur du back-end : " + e.toString());
            throw new Exception("Autre erreur du back-end : " + e.toString());
        } catch (ServiceNotFoundException e) {
            logger.error("ServiceNotFoundException" + e.getMessage());
            throw new Exception("ServiceNotFoundException" + e.getMessage());
                    //HttpServletResponse.SC_UNAUTHORIZED
        } catch (ClassCastException e) {
            logger.error(e.getMessage());
            throw new Exception( e.getMessage() );
        }
    }

    protected String list( String cookieValue,T objDTO, U clazz ,Model model) {
        String className = objDTO.getClass().getSimpleName().substring(0, objDTO.getClass().getSimpleName().length() - 3);
        try {
            model.addAttribute("form", list(cookieValue, objDTO, clazz));
        }catch(Exception e){
            model.addAttribute("Err",e.getMessage());
            return "/login";
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
    /*
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
    }*/
}
