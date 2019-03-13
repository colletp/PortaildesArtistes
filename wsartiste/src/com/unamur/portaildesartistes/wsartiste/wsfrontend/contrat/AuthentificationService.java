package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class AuthentificationService{

    private static final Logger logger = LoggerFactory.getLogger(AuthentificationService.class);

    @GetMapping(value="/Authentification")
    public ResponseEntity<String>  getAuthentificationAccess(){
        logger.info("Service Démarré");
        return new ResponseEntity<String>(" Statut de retour du service : " + HttpStatus.OK.name(),
            HttpStatus.OK);

    }

}