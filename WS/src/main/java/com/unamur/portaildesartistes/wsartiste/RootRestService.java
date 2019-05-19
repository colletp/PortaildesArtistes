package com.unamur.portaildesartistes.wsartiste;

    import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
    import com.unamur.portaildesartistes.wsartiste.Business.UtilisateurServiceImpl;
    import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeUtilisateurImpl;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpHeaders;
    import org.springframework.util.LinkedMultiValueMap;
    import org.springframework.util.MultiValueMap;
    import org.springframework.web.bind.annotation.*;

    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;

    import javax.servlet.http.HttpSession;
    import java.util.UUID;

@RestController
// Classe Controleur pour Spring
// Mémorise les requêtes gérée par la classe
public class RootRestService {

    private static final Logger logger = LoggerFactory.getLogger(RootRestService.class);

    @Autowired
    UtilisateurServiceImpl usrServImpl;

    @GetMapping(value = "/")
    // Equivaut à l'ancienne nomenclature @RequestMapping(method=RequestMethod.GET, value = "/")
    public ResponseEntity<String> wsRunning(){
        logger.info("Service Démarré");
        return new ResponseEntity<>(" Statut de retour du service : " + HttpStatus.OK.name() , HttpStatus.OK);
    }

    @PostMapping(value = "/")
    // Equivaut à l'ancienne nomenclature @RequestMapping(method=RequestMethod.GET, value = "/")
    public ResponseEntity<String> wsRunning2( @CookieValue("JSESSIONID") String cookie ){
        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        headers.add("SetCookie","JSESSIONID="+cookie+";");
        logger.info("Service Démarré");
        return new ResponseEntity<>(" Statut de retour du service : " + HttpStatus.OK.name(), headers , HttpStatus.OK);
    }

    @PutMapping(value = "/inscript")
    public @ResponseBody ResponseEntity wsInscript( @RequestBody final UtilisateurDTO usrDTO){
        logger.error( usrDTO==null?"null":usrDTO.getUsername() );
        UUID id = usrServImpl.insert( usrDTO );
        return new ResponseEntity<>(" Statut de retour du service : " + HttpStatus.OK.name() + " / " + id.toString() , HttpStatus.OK);
    }

//    @PostMapping(value="/Authentification")
//    public void wsAuth(){}
}