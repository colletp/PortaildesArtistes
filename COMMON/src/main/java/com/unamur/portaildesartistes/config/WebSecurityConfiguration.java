package com.unamur.portaildesartistes.config;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    static public PasswordEncoder encoder() {return new BCryptPasswordEncoder();}
}
