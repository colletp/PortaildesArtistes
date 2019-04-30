package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.DTO;
import com.unamur.portaildesartistes.webclient.RestTemplateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Controler<T extends DTO , U extends java.lang.Class<T> > {
    private static final Logger logger = LoggerFactory.getLogger(Controler.class);

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public String handleHttpMediaTypeNotAcceptableException() {
        return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
    }

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

    public String getForm( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
                                ,@PathVariable("id") UUID itemId
                                ,T obj
                                ,U clazz
                                //,String formName
                                ,String formAction
                                ,Model model){
        HttpHeaders headers = initHeadersRest(cookieValue);
        String className = obj.getClass().getSimpleName();
        switch( formAction.toUpperCase() ){
            case "PUT":
                break;
            case "POST":
            case "GET":
                try{
                    obj = restTemplateHelper.getForEntity( clazz , configurationService.getUrl()+"/gestion"+className+"/"+itemId , headers );
                }
                catch(ClassCastException e){
                    logger.error( e.getMessage() );
                }
                break;
            default:
        }
        model.addAttribute("form", obj );
        return obj.getClass().getSimpleName()+"/"+formAction+".html";
    }

    public String postForm( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("usrForm") final T obj
            ,U clazz
            ,Model model) {
        return postForm( cookieValue,method,obj,clazz,model,"");
    }
    public String postForm( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,@ModelAttribute("_method") final String method
            ,@ModelAttribute("usrForm") final T obj
                            ,U clazz
            ,Model model,String newUri){
        logger.error("citoyen(post) "+method+" : Authentication received! Cookie : "+cookieValue );

        HttpHeaders headers = initHeadersRest(cookieValue);
        String className = obj.getClass().getSimpleName();

        try{
            switch(method.toUpperCase()){
                case "PUT":
                    logger.error( "Appel REST PUT" );
                    UUID uuid = restTemplateHelper.putForEntity( UUID.class, configurationService.getUrl()+"/"+(newUri!=""?newUri:("gestion"+className+"/")) , obj , headers );
                    obj.setId(uuid);
                    break;
                case "POST":
                    logger.error( "Appel REST POST" );
                    restTemplateHelper.postForEntity( configurationService.getUrl()+"/"+(newUri!=""?newUri:("gestion"+className+"/")) , obj , headers );
                    break;
                default :
                    logger.error( "Appel REST : "+method );
            }
        }
        catch(ClassCastException e){
            logger.error( e.getMessage() );
        }
        model.addAttribute("form", obj );
        return "get.html";
    }

    public String list( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
            ,T obj
            ,U clazz
            ,Model model){
        HttpHeaders headers = initHeadersRest(cookieValue);
        String className = obj.getClass().getSimpleName();

        List<T> lObj=null;
        try{
            logger.error( "Appel REST" );
            lObj = restTemplateHelper.getForList( clazz, configurationService.getUrl()+"/gestion"+className+"/" , headers );
        }
        catch(ClassCastException e){
            logger.error( e.getMessage() );
        }
        model.addAttribute("form", lObj );

        return "list.html";
    }

    public String delete( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue
                                ,@PathVariable("id") UUID itemId
                                ,U obj
                                ,Model model) {
        HttpHeaders headers = initHeadersRest(cookieValue);
        String className = obj.getClass().getSimpleName();

        restTemplateHelper.delete(configurationService.getUrl()+"/gestion"+className+"/"+itemId , headers );
        return "list.html";
    }

}
