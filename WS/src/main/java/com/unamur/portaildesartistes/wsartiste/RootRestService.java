package com.unamur.portaildesartistes.wsartiste;

    import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
    import com.unamur.portaildesartistes.DTO.CustomWrapper;
    import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeUtilisateurImpl;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.servlet.ModelAndView;

    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.util.UUID;

@RestController
// Classe Controleur pour Spring
// Mémorise les requêtes gérée par la classe
public class RootRestService implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(RootRestService.class);

    @Autowired
    DonneeUtilisateurImpl usrImpl;

    @GetMapping(value = "/")
    // Equivaut à l'ancienne nomenclature @RequestMapping(method=RequestMethod.GET, value = "/")
    public ResponseEntity<String> wsRunning(){
        logger.info("Service Démarré");
        return new ResponseEntity<>(" Statut de retour du service : " + HttpStatus.OK.name() , HttpStatus.OK);
    }

    @Override
    @PostMapping(value = "/inscript")
    public @ResponseBody ResponseEntity wsInscript(
                @RequestBody final CustomWrapper<UtilisateurDTO> usrDTO
                ){
        logger.error( usrDTO==null?"null":usrDTO.getObject().getUsername() );
        UUID id = usrImpl.insert( usrDTO.getObject() );
        return new ResponseEntity<>(" Statut de retour du service : " + HttpStatus.OK.name() + " / " + id.toString() , HttpStatus.OK);
    }

}