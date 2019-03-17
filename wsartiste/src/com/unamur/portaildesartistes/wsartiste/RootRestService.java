package com.unamur.portaildesartistes.wsartiste;

    // Facade Logger : slf4j
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    // Spécifie à SpringBoot qu'il s'agit d'une classe contrôleur
    import org.springframework.web.bind.annotation.RestController;
    // Correspond à @RequestMapping(mezthod=RequestMethod.Get,value="/")
    import org.springframework.web.bind.annotation.GetMapping;

    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;

@RestController
// Classe Controleur pour Spring
// Mémorise les requêtes gérée par la classe
public class RootRestService {

    private static final Logger logger = LoggerFactory.getLogger(RootRestService.class);

    @GetMapping(value = "/")
    // Equivaut à l'ancienne nomenclature @RequestMapping(method=RequestMethod.GET, value = "/")
    public ResponseEntity<String> wsRunning()
    {
        logger.info("Service Démarré");
        return new ResponseEntity<>(" Statut de retour du service : " + HttpStatus.OK.name(),
            HttpStatus.OK);
    }

}