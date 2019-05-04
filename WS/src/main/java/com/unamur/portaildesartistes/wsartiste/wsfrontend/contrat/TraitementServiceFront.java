package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.DTO.TraitementDTO;
import com.unamur.portaildesartistes.wsartiste.Service.TraitementServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class TraitementServiceFront {


    private static final Logger logger = LoggerFactory.getLogger(TraitementServiceFront.class);

    @Autowired
    private TraitementServiceImpl traitementServiceImpl;

    @GetMapping("/gestionTraitement")
    public List<TraitementDTO> listTraitement(){ return traitementServiceImpl.list(); }

    @GetMapping("/gestionTraitement/{id}")
    public TraitementDTO TraitementDetail( @PathVariable("id") UUID id ){ return traitementServiceImpl.getById(id); }

    @DeleteMapping("/gestionTraitement/{id}")
    public void TraitementSuppr( @PathVariable("id") UUID id ){ traitementServiceImpl.delete(id); }

    @PutMapping("/gestionTraitement")
    public UUID TraitementCreer(@RequestBody TraitementDTO frm ){ return traitementServiceImpl.insert(frm); }

    @PostMapping("/gestionTraitement")
    public void TraitementModif( @RequestBody TraitementDTO frm ){ traitementServiceImpl.update(frm); }


}
