package com.unamur.portaildesartistes.wsartiste.gestionutilisateur;

import com.unamur.portaildesartistes.wsartiste.corelayer.AuthentificationBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class AuthentificationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthentificationController.class);
    private static int i;

    AuthentificationServiceImpl f_authentificationServiceImpl;

    @GetMapping("wsartiste/gestionUtilisateur/list")
    public List<AuthentificationBean> list() {
        return f_authentificationServiceImpl.list();
    }

    @GetMapping("wsartiste/gestionUtilisateur/insertOK")
    public Integer insertOK() {
        return f_authentificationServiceImpl.insertOK();
    }

    @GetMapping("/gestionUtilisateur/insertNotOK")
    public String insertNotOK() {
        f_authentificationServiceImpl.insertAndFail();
        return "ok";
    }
}
