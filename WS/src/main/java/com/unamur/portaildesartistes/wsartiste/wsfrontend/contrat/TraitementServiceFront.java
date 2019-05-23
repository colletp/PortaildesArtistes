package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.DTO.TraitementDTO;
import com.unamur.portaildesartistes.wsartiste.Business.CitoyenServiceImpl;
import com.unamur.portaildesartistes.wsartiste.Business.GestionnaireServiceImpl;
import com.unamur.portaildesartistes.wsartiste.Business.TraitementServiceImpl;
import com.unamur.portaildesartistes.wsartiste.Business.UtilisateurServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class TraitementServiceFront extends ServiceFront<TraitementDTO>{
    private static final Logger logger = LoggerFactory.getLogger(TraitementServiceFront.class);

    @Autowired
    private GestionnaireServiceImpl gestServImpl;
    @Autowired
    private UtilisateurServiceImpl usrServImpl;
    @Autowired
    private TraitementServiceImpl trtServImpl;

    @PutMapping("/gestionTraitement/")
    public UUID creer( @SessionAttribute("userName") String myUser
            , @RequestBody TraitementDTO objDTO )throws Exception{
        objDTO.setGestId(  gestServImpl.getByCitoyenId( usrServImpl.getUuidByName(myUser) ).getId() );
        return super.create(objDTO);
    }

    @GetMapping("/gestionTraitement/{id}")
    public TraitementDTO getById( @PathVariable("id") UUID uuid ){ return super.read(uuid); }
    @PostMapping("/gestionTraitement")
    public void modif( @RequestBody TraitementDTO objDTO )throws Exception{ super.update(objDTO); }
    @DeleteMapping("/gestionTraitement/{id}")
    public void suppr( @PathVariable("id") UUID id )throws Exception{ super.delete(id); }
    @GetMapping("/gestionTraitement")
    public List<TraitementDTO> list(){ return super.list(); }

    @GetMapping("/gestionTraitement/lang/{lang}")
    public List<TraitementDTO> listByLang( @PathVariable("lang") String lang ){ return trtServImpl.listByLang( lang ); }

}
