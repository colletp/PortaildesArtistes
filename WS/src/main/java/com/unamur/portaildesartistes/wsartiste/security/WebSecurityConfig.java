package com.unamur.portaildesartistes.wsartiste.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    @Qualifier("donneeUtilisateurImpl")
    UserDetailsService uDS;


    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        AppAuthProvider appAuthProvider = new AppAuthProvider();
        appAuthProvider.setPasswordEncoder( encoder() );
        appAuthProvider.setUserDetailsService( uDS );
        auth.authenticationProvider(appAuthProvider);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    AuthenticationSuccessHandler mySuccessHandler = new MySavedRequestAwareAuthenticationSuccessHandler();
    AuthenticationFailureHandler myFailureHandler = new SimpleUrlAuthenticationFailureHandler();
    LogoutSuccessHandler myLogoutHandler = new SimpleUrlLogoutSuccessHandler();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                    .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                    .authorizeRequests()
                    .antMatchers("/*")
                    .hasIpAddress("127.0.0.1/32")
                .and()
                    .authorizeRequests()
                    .antMatchers("/inscript")
                    .permitAll()
                .and()
                    .authorizeRequests()
                    .antMatchers("/gestionUtilisateur/list","/gestionUtilisateur2/list")
                    .authenticated()
                //.hasRole("Gestionnaire de formulaire FR")
                //.anyRequest()
                .and()
                    .formLogin()
                        .loginPage("/Authentification")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler( mySuccessHandler )
                        .failureHandler( myFailureHandler )
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy( SessionCreationPolicy.IF_REQUIRED )
                .and()
                    .logout()
                    .clearAuthentication(true)
                    .logoutUrl("/logout")
                    .logoutSuccessHandler( myLogoutHandler )
                ;
    }
}
