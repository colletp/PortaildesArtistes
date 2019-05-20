package com.unamur.portaildesartistes.webclient;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.management.ServiceNotFoundException;
import java.io.IOException;
import java.util.List;

/*
    source : https://gist.github.com/slmanju/a85a6854cb7d37d069351edae06a4eb7
*/

@Component
public class RestTemplateHelper {
    private static final Logger logger = LoggerFactory.getLogger(RestTemplateHelper.class);

    private RestTemplate restTemplate;

    private ObjectMapper objectMapper;

    @Autowired
    public RestTemplateHelper(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    public <T> T getForEntity(Class<T> clazz, String url, HttpHeaders headers , Object... uriVariables)throws Exception{
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, headers , uriVariables);
            JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
            return readValue(response, javaType);
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.info("No data found {}", url);
            } else {
                logger.info("rest client exception", exception.getMessage());
            }
        }
        throw new Exception();
    }

    public <T> List<T> getForList(Class<T> clazz, String url, HttpHeaders headers , Object... uriVariables)throws Exception {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, headers ,uriVariables);
            CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return readValue(response, collectionType);
        } catch (HttpClientErrorException e) {
            switch( e.getStatusCode().value() ){
                case 404 :
                    logger.info("No data found {}", url);
                    throw new ServiceNotFoundException( "Session pas trouvée" );
                case 401:
                case 403:
                    logger.info("no auth", url);
                    throw new AuthenticationServiceException( "Session pas trouvée" );
                default:
                    logger.info("rest client exception", e.getMessage());
            }
            throw new Exception(e.getMessage());
        }catch(HttpServerErrorException e){
            logger.error(e.getMessage() );
            throw new Exception(e.getMessage());
        }catch(RestClientException e){
            logger.error(e.getMessage() );
            throw new Exception(e.getMessage());
        }
    }

    public <R> void postForEntity( String url, R body, HttpHeaders headers , Object... uriVariables)throws  Exception {
        HttpEntity<R> request = new HttpEntity<>(body);
        try{
            restTemplate.postForEntity(url, request, String.class, headers , uriVariables);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public <T, R> T postForEntity(Class<T> clazz, String url, R body, HttpHeaders headers , Object... uriVariables)throws Exception {
        HttpEntity<R> request = new HttpEntity<>(body);
        try{
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class, headers , uriVariables);
            JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
            return readValue(response, javaType);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public <R>  ResponseEntity<String> postForAuth(String url, R body, HttpHeaders headers , Object... uriVariables)
            //throws Exception
    {
        //try{
            return restTemplate.postForEntity(url, new HttpEntity<>(body , headers) , String.class, uriVariables);
        //}catch(Exception e){
        //    throw new Exception(e.getMessage());
        //}
    }


    public <T, R> T putForEntity(Class<T> clazz, String url, R body, HttpHeaders headers , Object... uriVariables)throws Exception {
        HttpEntity<R> request = new HttpEntity<>(body);
        try{
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class, headers , uriVariables);
            JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
            return readValue(response, javaType);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void delete(String url, HttpHeaders headers , Object... uriVariables)throws Exception {
        try {
            restTemplate.delete(url, headers , uriVariables);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    private <T> T readValue(ResponseEntity<String> response, JavaType javaType) {
        T result = null;
        if (response.getStatusCode() == HttpStatus.OK ||
                response.getStatusCode() == HttpStatus.CREATED) {
            try {
                result = objectMapper.readValue(response.getBody(), javaType);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.warn("No data found {}", response.getStatusCode());
        }
        return result;
    }

}