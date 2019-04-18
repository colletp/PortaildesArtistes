package com.unamur.portaildesartistes.webclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;


@Configuration
@EnableWebMvc
@ComponentScan("com.unamur.portaildesartistes.webclient")
public class MvcConfiguration implements WebMvcConfigurer, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(MvcConfiguration.class);
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/img/**",
                "/css/**",
                "/js/**")
                .addResourceLocations(
                        "classpath:/static/img/",
                        "classpath:/static/css/",
                        "classpath:/static/js/");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(getClientHttpRequestFactory());
    }

    private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        //Connect timeout
        clientHttpRequestFactory.setConnectTimeout(10_000);
        //Read timeout
        clientHttpRequestFactory.setReadTimeout(10_000);
        return clientHttpRequestFactory;
    }
}