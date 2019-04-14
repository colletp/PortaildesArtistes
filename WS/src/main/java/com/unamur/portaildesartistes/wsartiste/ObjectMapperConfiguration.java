package com.unamur.portaildesartistes.wsartiste;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ObjectMapperConfiguration {

    //This is our default JSON ObjectMapper. Add @Primary to inject is as default bean.
    @Bean @Primary public ObjectMapper objectMapper() { return new ObjectMapper(); }
    @Bean public ObjectMapper yamlObjectMapper() { return new ObjectMapper(new YAMLFactory()); }
    @Bean public ObjectMapper xmlObjectMapper() {
        JacksonXmlModule xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);
        return new XmlMapper(xmlModule);

     //return new ObjectMapper(new XmlFactory());
    }
}
