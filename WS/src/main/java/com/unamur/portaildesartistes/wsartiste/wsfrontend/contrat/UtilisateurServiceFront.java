package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.wsartiste.Business.UtilisateurServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UtilisateurServiceFront extends ServiceFront<UtilisateurDTO> {
    private static final Logger logger = LoggerFactory.getLogger(UtilisateurServiceFront.class);

    @PutMapping("/gestionUtilisateur")
    public UUID creer( @RequestBody UtilisateurDTO objDTO )throws Exception{ return super.create(objDTO); }
    @GetMapping("/gestionUtilisateur/{id}")
    public UtilisateurDTO getById( @PathVariable("id") UUID uuid ){ return super.read(uuid); }
    @PostMapping("/gestionUtilisateur")
    public void modif( @RequestBody UtilisateurDTO objDTO )throws Exception{ super.update(objDTO); }
    @DeleteMapping("/gestionUtilisateur/{id}")
    public void suppr( @PathVariable("id") UUID id )throws Exception{ super.delete(id); }

    @GetMapping("/gestionUtilisateur")
    public List<UtilisateurDTO> list(){ return super.list(); }

    @GetMapping("/gestionUtilisateur/moi")
    public UUID myUserId(@SessionAttribute("userName") String myUser){ return ((UtilisateurServiceImpl)(srvImpl)).getUuidByName(myUser); }

}
