package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
import com.unamur.portaildesartistes.wsartiste.Business.ActiviteServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ActiviteServiceFront extends ServiceFront<ActiviteDTO>{
    private static final Logger logger = LoggerFactory.getLogger(ActiviteServiceFront.class);

    @PutMapping("/gestionActivite/{formId}")
    public UUID creer( @RequestBody ActiviteDTO objDTO,@PathVariable("formId") UUID formId)throws Exception{
        return ((ActiviteServiceImpl) srvImpl).insert(objDTO,formId);
    }
    @GetMapping("/gestionActivite/{id}")
    public ActiviteDTO getById( @PathVariable("id") UUID uuid ){ return super.read(uuid); }
    @PostMapping("/gestionActivite")
    public void modif( @RequestBody ActiviteDTO objDTO )throws Exception{ super.update(objDTO); }
    @DeleteMapping("/gestionActivite/{id}")
    public void suppr( @PathVariable("id") UUID id )throws Exception{
        super.delete(id);
    }

    @GetMapping("/gestionActivite")
    public List<ActiviteDTO> list(){ return super.list(); }

    //@GetMapping("/gestionActivite/secteur")
    //public List<ActiviteDTO> listActiviteSecteur(){ return activiteServiceImpl.list(); }
}
