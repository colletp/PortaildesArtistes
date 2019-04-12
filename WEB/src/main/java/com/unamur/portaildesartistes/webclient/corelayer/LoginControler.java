package com.unamur.portaildesartistes.webclient.corelayer;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Controller
public class LoginControler {

    private static final Logger logger = LoggerFactory.getLogger(LoginControler.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PropertiesConfigurationService configService;

    @GetMapping(value = "/login")//initialisation du login
    public String loginView(Map<String, Object> model) {
        UtilisateurDTO userDto = new UtilisateurDTO();
        model.put("userForm", userDto);
        return "loginForm";
    }

}