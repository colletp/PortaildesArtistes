package com.unamur.portaildesartistes.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan("com.unamur.portaildesartistes")
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfiguration.class);

    @Autowired
    @Qualifier("customJackson2HttpMessageConverter")
    protected MappingJackson2HttpMessageConverter m;

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        logger.info( "configureMessageConverters" );
        converters.add( m );
        super.addDefaultHttpMessageConverters( converters );
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        logger.info( "configureContentNegotiation" );
        MediaType defMed=null;
        Map<String,MediaType> map = new HashMap<>();
        for( MediaType med : m.getSupportedMediaTypes() ){
            if(defMed==null)defMed = med;
            map.put(med.toString(),med);
        }
        configurer
                .favorPathExtension(true)
                .favorParameter(false)
                .ignoreAcceptHeader(false)
                .defaultContentType( defMed )
                .mediaTypes( map )
        ;
    }

}
