package com.unamur.portaildesartistes.wsartiste.gestionutilisateur;

import com.unamur.portaildesartistes.wsartiste.corelayer.AuthentificationBean;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

public class AuthentificationController {

    private static int i;

    @Autowired
    AuthentificationService authentificationService;

    @GetMapping("/gestionUtilisateur/list")
    public List<AuthentificationBean> list() {
        return authentificationService.list();
    }

    @GetMapping("/gestionUtilisateur/insertOK")
    public Integer insertOK() {
        return authentificationService.insertOK();
    }

    @GetMapping("/gestionUtilisateur/insertNotOK")
    public String insertNotOK() {
        authentificationService.insertAndFail();
        return "ok";
    }
}
