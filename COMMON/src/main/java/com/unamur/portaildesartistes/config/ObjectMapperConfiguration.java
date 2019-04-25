package com.unamur.portaildesartistes.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.dataformat.xml.XmlFactory;
//import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Configuration
@Component
public class ObjectMapperConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ObjectMapperConfiguration.class);
    private static final MediaType MEDIA_TYPE_YAML = MediaType.valueOf("text/yaml");
    private static final MediaType MEDIA_TYPE_YML = MediaType.valueOf("text/yml");

    @Bean
    public static MediaType getMediaTypeYaml(){return MEDIA_TYPE_YAML;}
    @Bean
    public static MediaType getMediaTypeYml(){return MEDIA_TYPE_YML;}

    //This is our default JSON ObjectMapper. Add @Primary to inject is as default bean.
    @Bean @Primary public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    @Bean public ObjectMapper yamlObjectMapper() {
        return new ObjectMapper(new YAMLFactory());
    }

/*    @Bean public ObjectMapper xmlObjectMapper() {
        JacksonXmlModule xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);
        return new XmlMapper(xmlModule);
     //return new ObjectMapper(new XmlFactory());
    }
*/

    @Autowired
    private Environment env;

    private ObjectMapper myObjectMapper;

    @Autowired
    public void setMyObjectMapper(ApplicationContext context) {
        String format = env.getProperty("rest.lang.media-type");
        logger.info( "Using rest lang : "+ format );
        myObjectMapper = (ObjectMapper) context.getBean( format );
    }

    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        logger.info( "customJackson2HttpMessageConverter" );
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        myObjectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        logger.info( myObjectMapper.getFactory().getFormatName() );
        switch(myObjectMapper.getFactory().getFormatName()){
            case "YAML":
                jsonConverter.setSupportedMediaTypes( Arrays.asList( MEDIA_TYPE_YAML, MEDIA_TYPE_YML ) );
                break;
            case "JSON":
                jsonConverter.setSupportedMediaTypes( Arrays.asList( MediaType.APPLICATION_JSON_UTF8 ) );
                break;
            case "XML":
                jsonConverter.setSupportedMediaTypes( Arrays.asList( MediaType.APPLICATION_XML,MediaType.TEXT_XML ) );
                break;
            default:
                jsonConverter.setSupportedMediaTypes( null );
        }
        jsonConverter.setObjectMapper(myObjectMapper);
        return jsonConverter;
    }

}
