package com.unamur.portaildesartistes.wsartiste;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfiguration.class);

    private ObjectMapper myObjectMapper;

    @Autowired
    private Environment env;

    @Autowired
    public void setMyObjectMapper(ApplicationContext context) {
        logger.info( "Using rest lang : "+env.getProperty("rest.lang.media-type") );
        myObjectMapper = (ObjectMapper) context.getBean( env.getProperty("rest.lang.media-type") );
    }

    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        myObjectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        jsonConverter.setObjectMapper(myObjectMapper);
        return jsonConverter;
    }
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add( customJackson2HttpMessageConverter() );
        super.addDefaultHttpMessageConverters( converters );
    }
}
