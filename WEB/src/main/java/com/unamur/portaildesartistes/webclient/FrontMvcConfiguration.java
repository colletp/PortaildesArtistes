package com.unamur.portaildesartistes.webclient;

import com.unamur.portaildesartistes.config.WebMvcConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class FrontMvcConfiguration extends WebMvcConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(FrontMvcConfiguration.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.debug("addResourceHandlers");
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
        logger.debug("restTemplate");
        RestTemplate rest = new RestTemplate(getClientHttpRequestFactory());

        logger.error( m.getSupportedMediaTypes().toString() );
        /*
        List< HttpMessageConverter<?> > lstConv = Collections.singletonList( m );
        rest.setMessageConverters( lstConv );
        */
        return rest;
    }

    private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        //Connect timeout
        clientHttpRequestFactory.setConnectTimeout(10_000);
        //Read timeout
        clientHttpRequestFactory.setReadTimeout(10_000);
        return clientHttpRequestFactory;
    }
    @Bean
    @Description("Thymeleaf template resolver serving HTML")
    public ClassLoaderTemplateResolver templateResolver() {
        logger.debug("templateResolver");
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    @Description("Thymeleaf template engine with Spring integration")
    public SpringTemplateEngine templateEngine() {
        logger.debug("templateEngine");
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    @Description("Thymeleaf view resolver")
    public ViewResolver viewResolver() {
        logger.debug("viewResolver");
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine( templateEngine() );
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        logger.debug("addViewControllers");
        registry.addViewController("/").setViewName("index");
    }

    @Bean
    public LocaleResolver localeResolver() {
        logger.error("localeResolver");
        CookieLocaleResolver clr = new CookieLocaleResolver();
        //clr.setDefaultLocale(Locale.FRENCH);
        return clr;
    }
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        logger.error("localeChangeInterceptor");
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.error("addInterceptors");
        registry.addInterceptor(localeChangeInterceptor());
    }

}