package com.unamur.portaildesartistes.webclient.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Override
    public void configure(final WebSecurity web) throws Exception {
        logger.debug("configure WebSecurity");
        web.ignoring().antMatchers("/static/**");
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    SimpleUrlAuthenticationSuccessHandler mySuccessHandler = new SimpleUrlAuthenticationSuccessHandler();
    //MySavedRequestAwareAuthenticationSuccessHandler();
    SimpleUrlAuthenticationFailureHandler myFailureHandler = new SimpleUrlAuthenticationFailureHandler();
    //SimpleUrlAuthenticationFailureHandler myFailureHandler = new MySavedRequestAwareAuthenticationFailureHandler();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.debug("configure HttpSecurity");

        http.csrf()
                .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
        /*
        .and()
        // The pages does not require login
        .authorizeRequests().antMatchers("/", "/login","/login2","/logout")
            .permitAll()
        // /userInfo page requires login as ROLE_USER or ROLE_ADMIN.
        // If no login, it will redirect to /login page.
        .and().authorizeRequests().antMatchers("/userInfo","/gestionUtilisateur","/gestionUtilisateur/*")
            .authenticated()
        //.access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")

        // For ADMIN only.
        .and().authorizeRequests().antMatchers("/admin")
                .access("hasRole('ROLE_ADMIN')")

        // When the user has logged in as XX.
        // But access a page that requires role YY,
        // AccessDeniedException will be thrown.
        .and().exceptionHandling().accessDeniedPage("/403")

        // Config for Login Form
        .and().formLogin()
            // Submit URL of login page.
            //.loginProcessingUrl("/login2")
            .loginPage("/login")
            //.defaultSuccessUrl("/userAccountInfo")
            //.failureUrl("/login?error=true")
            .successHandler(mySuccessHandler)
            .failureHandler(myFailureHandler)
            .usernameParameter("username")
            .passwordParameter("password")
            // Config for Logout Page
        .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
*/
    }
}