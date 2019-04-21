package com.unamur.portaildesartistes.webclient.security;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.webclient.corelayer.PropertiesConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

//    @Autowired
//    private RestTemplate restTemplate;
//    @Autowired
//    private PropertiesConfigurationService configurationService ;

    @Override
    public UserDetails loadUserByUsername(String userName)throws UsernameNotFoundException {
logger.error("loadUserByUsername");
/*        Objects.requireNonNull(userName);
        Map<String,String> param = new HashMap<>();
        param.put("username",userName);
        param.put("password","test");

        UserDetails user;
        UtilisateurDTO usr = new UtilisateurDTO();
        usr.setUsername( userName );
        user=usr;
        try {
            HttpEntity<UserDetails> request = new HttpEntity<>( usr );

            //UserDetails r = restTemplate.postForObject( configurationService.getUrl()+"/Authentification" , request, UserDetails.class , param );
            //user=r.getBody();

            ResponseEntity<UserDetails> r = restTemplate.postForEntity( configurationService.getUrl()+"/Authentification" , request, UserDetails.class , param );

            logger.error( r.getHeaders().toSingleValueMap().toString() );
            logger.error( r.getBody().toString() );
            logger.error( user.getUsername() );
            logger.error( user.getPassword() );
            logger.error( user.getAuthorities().toString() );
            if(user==null)throw new UsernameNotFoundException("User not found");
        }catch( RestClientException e ){
            logger.error( e.getMessage()+"/rest : "+e.getStackTrace().toString() );
            throw new UsernameNotFoundException("User not found");
        };
        logger.debug("role of the user" + user.getAuthorities() );

        return user;
*/
        return null;
    }

}
