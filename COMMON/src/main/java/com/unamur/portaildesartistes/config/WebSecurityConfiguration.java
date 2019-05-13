package com.unamur.portaildesartistes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    static public PasswordEncoder encoder() {return new BCryptPasswordEncoder();}
}
