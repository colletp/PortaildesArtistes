package com.unamur.portaildesartistes.webclient.security;

import com.unamur.portaildesartistes.config.WebSecurityConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
//@ComponentScan("com.unamur.portaildesartistes.config")
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Override
    public void configure(final WebSecurity web) throws Exception {
        logger.debug("configure WebSecurity");
		//TODO: voir pourquoi il faut ajouter ce qu'il y a dans ce répertoire aux mapper pour que ça fonctionne
		//idéalement, il faudrait que tout ceci soit automatique
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.debug("configure HttpSecurity");
        http
            .csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }
}