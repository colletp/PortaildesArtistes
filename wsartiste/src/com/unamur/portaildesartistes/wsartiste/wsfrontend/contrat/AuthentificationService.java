package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@EnableAutoConfiguration

class AuthentificationService{

    private static final Logger logger = LoggerFactory.getLogger(AuthentificationService.class);

    @RequestMapping(value="/Authentification",method = RequestMethod.GET)
    public ResponseEntity<String>  getAuthentificationAccess(){
        logger.info("Service Démarré");
        return new ResponseEntity<String>(" Statut de retour du service : " + HttpStatus.OK.name(),
            HttpStatus.OK);

    }

}