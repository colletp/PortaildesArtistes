package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.wsartiste.corelayer.UtilisateurBean;
import com.unamur.portaildesartistes.wsartiste.gestionutilisateur.UtilistateurServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UtilisateurServiceFront {

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurServiceFront.class);
    private static int i;
    private UtilistateurServiceImpl f_authentificationServiceImpl;

    public UtilisateurServiceFront()
    {
        f_authentificationServiceImpl = new UtilistateurServiceImpl();
    }

    @GetMapping("/gestionUtilisateur/list")
    public List<UtilisateurBean> list() {
        return f_authentificationServiceImpl.list();
    }

    @GetMapping("/gestionUtilisateur/insertOK")
    public Integer insertOK() {
        return f_authentificationServiceImpl.insertOK();
    }

    @GetMapping("/gestionUtilisateur/insertNotOK")
    public String insertNotOK() {
        f_authentificationServiceImpl.insertAndFail();
        return "ok";
    }
}
