package com.unamur.portaildesartistes.wsartiste.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpSession;
import java.util.Collection;

public class AppAuthProvider extends DaoAuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(AppAuthProvider.class);

    @Override
    public Authentication authenticate( Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
        String name = auth.getName();
        logger.info("username="+name);
        logger.info("password="+auth.getCredentials().toString() );
        logger.error( "pass:"+auth.getCredentials() );
        logger.error( "pass to update:"+ getPasswordEncoder().encode( auth.getCredentials().toString() ) );
        UserDetails user = super.getUserDetailsService().loadUserByUsername(name);
        if (user == null) {
            throw new BadCredentialsException("Username/Password does not match for " + auth.getPrincipal());
        }

        // Database Password already encrypted:
        boolean passwordsMatch = getPasswordEncoder().matches( auth.getCredentials().toString() , user.getPassword() );
        if(!passwordsMatch) {
            throw new BadCredentialsException("Invalid username/password");
        }
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        for( GrantedAuthority g : authorities )logger.info( g.getAuthority() );
        return new UsernamePasswordAuthenticationToken( user, user.getPassword(), authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}