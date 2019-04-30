package com.unamur.portaildesartistes.config;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    static public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    public AppAuthProvider appAuthProviderRest( @Autowired @Qualifier("donneeUtilisateurImpl") UserDetailsService uDS, PasswordEncoder encoder){
        AppAuthProvider appAuthProvider = new AppAuthProvider();
        appAuthProvider.setPasswordEncoder( encoder );
        appAuthProvider.setUserDetailsService( uDS );
        return appAuthProvider;
    }

    @Bean
    public AppAuthProvider appAuthProviderWeb( @Autowired @Qualifier("userDetailsServiceWeb") UserDetailsService uDS, PasswordEncoder encoder){
        AppAuthProvider appAuthProvider = new AppAuthProvider();
        appAuthProvider.setPasswordEncoder( encoder );
        appAuthProvider.setUserDetailsService( uDS );
        return appAuthProvider;
    }*/

}
