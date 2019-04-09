package com.unamur.portaildesartistes.webclient.corelayer;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.unamur.portaildesartistes.webclient.corelayer.PropertiesConfigurationService;
import com.unamur.portaildesartistes.webclient.corelayer.bean.UtilisateurBean;

@Controller
public class LoginControler {

    private static final Logger logger = LoggerFactory.getLogger(LoginControler.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PropertiesConfigurationService configService;

    @GetMapping(value = "/login")//initialisation du login
    public String loginView(Map<String, Object> model) {
        UtilisateurBean userDto = new UtilisateurBean();
        model.put("userForm", userDto);
        return "loginForm";
    }

}