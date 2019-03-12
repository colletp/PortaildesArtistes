package com.unamur.portaildesartistes.wsartiste;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    import org.springframework.web.bind.annotation.*;

    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;

@RestController
// Classe Controleur pour Spring
public class RootRestService {

    private static final Logger logger = LoggerFactory.getLogger(RootRestService.class);

    @GetMapping(value = "/")
    // Equivaut à l'ancienne nomenclature @RequestMapping(method=RequestMethod.GET, value = "/")
    public ResponseEntity<String> wsRunning()
    {
        logger.info("Service Démarré");
        return new ResponseEntity<String>(" Statut de retour du service : " + HttpStatus.OK.name(),
            HttpStatus.OK);
    }

}