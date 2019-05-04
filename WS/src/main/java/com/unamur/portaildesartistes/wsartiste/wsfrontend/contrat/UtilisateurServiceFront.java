package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.wsartiste.Service.UtilisateurServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UtilisateurServiceFront {
    private static final Logger logger = LoggerFactory.getLogger(UtilisateurServiceFront.class);

    @Autowired
    private UtilisateurServiceImpl usrServiceImpl;

    @GetMapping("/gestionUtilisateur")
    public List<UtilisateurDTO> listUtilisateur( @CookieValue( value = "JSESSIONID",defaultValue = "" )String cookieValue){
        logger.error( cookieValue );
        return usrServiceImpl.list();
    }

    @GetMapping("/gestionUtilisateur/{id}")
    public UtilisateurDTO utilisateurDetail( @PathVariable("id") UUID id ){ return usrServiceImpl.getById(id); }

    @DeleteMapping("/gestionUtilisateur/{id}")
    public void utilisateurSuppr( @PathVariable("id") UUID id ){ usrServiceImpl.delete(id); }

    @PutMapping("/gestionUtilisateur")
    public UUID utilisateurCreer( @RequestBody UtilisateurDTO usr ){
        return usrServiceImpl.insert(usr);
    }

    @PostMapping("/gestionUtilisateur")
    public void utilisateurModif( @RequestBody UtilisateurDTO usr ){ usrServiceImpl.update(usr); }

}
