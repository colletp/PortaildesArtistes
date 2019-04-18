package com.unamur.portaildesartistes.webclient.security;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.webclient.corelayer.LoginControler;
import com.unamur.portaildesartistes.webclient.corelayer.PropertiesConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PropertiesConfigurationService configurationService ;

    @Override
    public UserDetails loadUserByUsername(String userName)throws UsernameNotFoundException {
        Objects.requireNonNull(userName);
        //CitoyenDTO user = new CitoyenDTO();
        ResponseEntity<String> r = restTemplate.postForObject( configurationService.getUrl()+"/Authentification" , null, ResponseEntity.class);
        logger.error( r.getHeaders().toSingleValueMap().toString() );
        logger.error( r.getBody() );
        return null;
    }

}
