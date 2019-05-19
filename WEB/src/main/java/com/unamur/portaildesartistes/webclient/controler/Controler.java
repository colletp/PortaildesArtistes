package com.unamur.portaildesartistes.webclient.controler;

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

    protected T getObj( String cookieValue,UUID uuid,T objDTO, U clazz, Model model ){
        HttpHeaders headers = initHeadersRest(cookieValue);
        String className = objDTO.getClass().getSimpleName().substring(0,objDTO.getClass().getSimpleName().length()-3);
        try {
            logger.debug("Appel REST getObj:"+className+" ["+uuid.toString()+"]");
            return restTemplateHelper.getForEntity(clazz, configurationService.getUrl() + "/gestion" + className + "/"+uuid, headers);
        } catch (AuthenticationServiceException e) {
            model.addAttribute("Err","Connexion refusée par authentification back-end : " + e.toString());
        } catch (HttpClientErrorException.Unauthorized e) {
            model.addAttribute("Err","Connexion refusée par authentification back-end : " + e.toString());
        } catch (HttpClientErrorException.Forbidden e) {
            model.addAttribute("Err","Connexion refusée par authentification back-end : " + e.toString());
        } catch (HttpServerErrorException.InternalServerError e) {
            model.addAttribute("Err","Connexion perdue au back-end : " + e.toString());
        } catch (HttpServerErrorException e) {
            model.addAttribute("Err","Autre erreur du back-end : " + e.toString());
        }catch(ClassCastException e){
            model.addAttribute("Err", e.getMessage() );
        }
        return objDTO;
    }

    protected V getForm(String cookieValue, T objDTO, V objForm, UUID itemId, U clazz, String method, Model model ){
        switch( method.toUpperCase() ){
            case "":
            case "POST":
            case "GET":
                try{
                    objForm.setFromDTO( getObj(cookieValue,itemId,objDTO,clazz, model ) );
                    return objForm;
                }
                catch(Exception e){
                    model.addAttribute("Err", e.getMessage() );
                }
            default:
                return null;
        }
    }

    //Complete les données d'un formulaire qui sera affiché
    /*protected String getForm(String cookieValue, T objDTO, V objForm, UUID itemId, U clazz, String method, Model model){
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
                    model.addAttribute("Err", e.getMessage() );
                    return "/error.html";
                }
                break;
            default:
        }
        model.addAttribute("form", objForm );
        return className+"/"+method.toUpperCase()+".html";
    }*/

    protected UUID getMyId( String cookieValue ){
        HttpHeaders headers = initHeadersRest(cookieValue);
        return restTemplateHelper.getForEntity(UUID.class,configurationService.getUrl()+"/gestionUtilisateur/moi",headers );
    }

    protected UUID postForm( String cookieValue,final V form,final String method, Model model ){
		return postForm(cookieValue,form,method,"",model);
    }
    protected UUID postForm( String cookieValue,final V form,final String method,String newUri, Model model ){
        T objDTO;
        try{
            objDTO = form.getDTO();
			return postForm(cookieValue,objDTO,method,model);
        }catch(IllegalArgumentException e){
            model.addAttribute("Err", e.getMessage() );
        }
        return null;
    }
    protected UUID postForm( String cookieValue,T objDTO,String method, Model model ) {
        return postForm( cookieValue,objDTO,method,"",model);
    }
    protected UUID postForm( String cookieValue,T objDTO,String method,String newUri, Model model ){
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
                    model.addAttribute("Err", "Appel REST : "+method );
            }
            return objDTO.getId();
        }
        catch(ClassCastException e){
            model.addAttribute("Err", e.getMessage() );
        }
        return null;
    }

    protected List<T> list( String cookieValue,T objDTO, U clazz, Model model ){
        String className = objDTO.getClass().getSimpleName().substring(0, objDTO.getClass().getSimpleName().length() - 3);
        HttpHeaders headers = initHeadersRest(cookieValue);

        logger.debug("Appel REST list:"+className);
        try {
            return restTemplateHelper.getForList(clazz, configurationService.getUrl() + "/gestion" + className + "/", headers);
        } catch (AuthenticationServiceException e) {
            model.addAttribute("Err","Connexion refusée par authentification back-end : " + e.toString());
        } catch (HttpClientErrorException.Unauthorized e) {
            model.addAttribute("Err","Connexion refusée par authentification back-end : " + e.toString());
        } catch (HttpClientErrorException.Forbidden e) {
            model.addAttribute("Err","Connexion refusée par authentification back-end : " + e.toString());
        } catch (HttpServerErrorException.InternalServerError e) {
            model.addAttribute("Err","Connexion perdue au back-end : " + e.toString());
        } catch (HttpServerErrorException e) {
            model.addAttribute("Err","Autre erreur du back-end : " + e.toString());
        } catch (ServiceNotFoundException e) {
            model.addAttribute("Err","ServiceNotFoundException" + e.getMessage());
        } catch (ClassCastException e) {
            model.addAttribute("Err",e.getMessage());
        }
        return new ArrayList<>();
    }

    /*
    protected String list( String cookieValue,T objDTO, U clazz ,Model model) {
        String className = objDTO.getClass().getSimpleName().substring(0, objDTO.getClass().getSimpleName().length() - 3);
        try {
            model.addAttribute("form", list(cookieValue, objDTO, clazz));
            return className+"/list.html";
        }catch(Exception e){
            model.addAttribute("Err",e.getMessage());
            return "/login";
        }
    }*/

    protected void delete( String cookieValue,T objDTO,UUID itemId,Model model ){
        HttpHeaders headers = initHeadersRest(cookieValue);
        String className = objDTO.getClass().getSimpleName().substring(0,objDTO.getClass().getSimpleName().length()-3);

        try {
            restTemplateHelper.delete(configurationService.getUrl() + "/gestion" + className + "/" + itemId, headers);
        }catch(Exception e){
            model.addAttribute("Err",e.getMessage() );
        }
    }

	/*************************************
	* Public
	*
	**************************************/

}
