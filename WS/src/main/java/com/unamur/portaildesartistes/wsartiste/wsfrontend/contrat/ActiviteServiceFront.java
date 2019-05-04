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
public class ActiviteServiceFront {

    private static final Logger logger = LoggerFactory.getLogger(ActiviteServiceFront.class);

    @Autowired
    private ActiviteServiceImpl activiteServiceImpl;

    @GetMapping("/gestionActivite")
    public List<ActiviteDTO> listActivite(){ return activiteServiceImpl.list(); }

    //@GetMapping("/gestionActivite/secteur")
    //public List<ActiviteDTO> listActiviteSecteur(){ return activiteServiceImpl.list(); }

    @GetMapping("/gestionActivite/{id}")
    public ActiviteDTO ActiviteDetail( @PathVariable("id") UUID id ){ return activiteServiceImpl.getById(id); }

    @DeleteMapping("/gestionActivite/{id}")
    public void ActiviteSuppr( @PathVariable("id") UUID id ){ activiteServiceImpl.delete(id); }

    @PutMapping("/gestionActivite")
    public UUID ActiviteCreer( @RequestBody ActiviteDTO act ){ return activiteServiceImpl.insert(act); }

    @PostMapping("/gestionActivite")
    public void ActiviteModif( @RequestBody ActiviteDTO act ){ activiteServiceImpl.update(act); }

}
