package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
import com.unamur.portaildesartistes.wsartiste.Service.ActiviteServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ActiviteServiceFront extends ServiceFront<ActiviteDTO>{
    private static final Logger logger = LoggerFactory.getLogger(ActiviteServiceFront.class);

    @PutMapping("/gestionActivite")
    public UUID creer( @RequestBody ActiviteDTO objDTO ){ return super.create(objDTO); }
    @GetMapping("/gestionActivite/{id}")
    public ActiviteDTO getById( @PathVariable("id") UUID uuid ){ return super.read(uuid); }
    @PostMapping("/gestionActivite")
    public void modif( @RequestBody ActiviteDTO objDTO ){ super.update(objDTO); }
    @DeleteMapping("/gestionActivite/{id}")
    public void suppr( @PathVariable("id") UUID id ){ super.delete(id); }

    @GetMapping("/gestionActivite")
    public List<ActiviteDTO> list(){ return super.list(); }

    //@GetMapping("/gestionActivite/secteur")
    //public List<ActiviteDTO> listActiviteSecteur(){ return activiteServiceImpl.list(); }
}
