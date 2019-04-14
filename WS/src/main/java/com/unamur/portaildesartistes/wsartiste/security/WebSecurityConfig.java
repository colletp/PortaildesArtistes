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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    @Qualifier("donneeCitoyenImpl")
    UserDetailsService uDS;


    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        /*auth.inMemoryAuthentication()
                .withUser("admin").password(encoder().encode("adminPass")).roles("ADMIN")
                .and()
                .withUser("user").password(encoder().encode("userPass")).roles("USER");
                */
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

    SimpleUrlAuthenticationSuccessHandler mySuccessHandler = new MySavedRequestAwareAuthenticationSuccessHandler();
    SimpleUrlAuthenticationFailureHandler myFailureHandler = new SimpleUrlAuthenticationFailureHandler();
    //SimpleUrlAuthenticationFailureHandler myFailureHandler = new MySavedRequestAwareAuthenticationFailureHandler();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                    .authorizeRequests()
                    //.antMatchers("/gestionUtilisateur").authenticated()
                    .antMatchers("/gestionUtilisateur/list")
                .authenticated()
                //.hasRole("Gestionnaire de formulaire FR")
                //.anyRequest()
                .and()
                    .formLogin()
                        .loginPage("/Authentification")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(mySuccessHandler)
                        .failureHandler(myFailureHandler)
                .and()
                .logout();

/*        http.csrf().disable();
        // The pages does not require login
        http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();
        // /userInfo page requires login as ROLE_USER or ROLE_ADMIN.
        // If no login, it will redirect to /login page.
        http.authorizeRequests().antMatchers("/userInfo","/gestionUtilisateur","/gestionUtilisateur/*").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");

        // For ADMIN only.
        http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");

        // When the user has logged in as XX.
        // But access a page that requires role YY,
        // AccessDeniedException will be thrown.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

        // Config for Login Form
        http.authorizeRequests().and().formLogin()//
            // Submit URL of login page.
            .loginProcessingUrl("/j_spring_security_check") // Submit URL
            .loginPage("/login")//
            .defaultSuccessUrl("/userAccountInfo")//
            .failureUrl("/login?error=true")//
            .usernameParameter("username")//
            .passwordParameter("password")
            // Config for Logout Page
            .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
*/
    }
}
