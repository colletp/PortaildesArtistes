package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.wsartiste.corelayer.UtilisateurBean;
import com.unamur.portaildesartistes.wsartiste.gestionutilisateur.UtilistateurServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@RestController
public class UtilisateurServiceFront {
    private static final Logger logger = LoggerFactory.getLogger(UtilisateurServiceFront.class);

    @Autowired
    private UtilistateurServiceImpl utilisateurServiceImpl;

    @GetMapping("/gestionUtilisateur/list")
    public List<UtilisateurBean> list() {
        return utilisateurServiceImpl.list();
    }

    @GetMapping("/gestionUtilisateur/insertOK")
    public UUID insertOK() {
        return utilisateurServiceImpl.insertOK();
    }

    @GetMapping("/gestionUtilisateur/insertNotOK")
    public String insertNotOK() {
        utilisateurServiceImpl.insertAndFail();
        return "ok";
    }
}
