package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.DTO.PrestationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class PrestationServiceFront extends ServiceFront<PrestationDTO> {
    private static final Logger logger = LoggerFactory.getLogger(PrestationServiceFront.class);

    @PutMapping("/gestionPrestation")
    public UUID creer(@SessionAttribute("userName") String myUser, @RequestBody PrestationDTO objDTO)throws Exception{
        return super.create(objDTO);
    }

    @GetMapping("/gestionPrestation/{id}")
    public PrestationDTO getById(@PathVariable("id") UUID uuid) {
        return super.read(uuid);
    }

    @PostMapping("/gestionPrestation")
    public void modif(@RequestBody PrestationDTO objDTO)throws Exception {
        super.update(objDTO);
    }

    @DeleteMapping("/gestionPrestation/{id}")
    public void suppr(@PathVariable("id") UUID id)throws Exception {
        super.delete(id);
    }

    @GetMapping("/gestionPrestation")
    public List<PrestationDTO> list() {
        return super.list();
    }

}

