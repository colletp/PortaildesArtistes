package com.unamur.portaildesartistes.webclient.security;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.webclient.RestTemplateHelper;
import com.unamur.portaildesartistes.webclient.corelayer.PropertiesConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
//@ComponentScan("")
public class UserDetailsServiceWeb implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceWeb.class);

    @Autowired
    RestTemplateHelper restTemplate;
    @Autowired
    PropertiesConfigurationService pcs;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        HttpHeaders headers = new HttpHeaders( );
        headers.add("username",s);

        UtilisateurDTO user;
        try {
            user = restTemplate.getForEntity( UtilisateurDTO.class,pcs.getUrl()+"/" , headers );
            if(user==null)throw new UsernameNotFoundException("User not found");
        }catch( RestClientException e ){
            logger.error( e.getMessage() );
            throw new UsernameNotFoundException("User not found");
        }catch( Exception e ){
            logger.error( e.getMessage() );
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
