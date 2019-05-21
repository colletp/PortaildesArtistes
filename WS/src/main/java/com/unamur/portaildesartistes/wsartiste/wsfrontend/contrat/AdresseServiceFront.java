package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.DTO.AdresseDTO;
import com.unamur.portaildesartistes.wsartiste.Business.AdresseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class AdresseServiceFront extends ServiceFront<AdresseDTO> {
    private static final Logger logger = LoggerFactory.getLogger(AdresseServiceFront.class);

    @PutMapping("/gestionAdresse")
    public UUID creer(@RequestBody AdresseDTO objDTO) {
        return super.create(objDTO);
    }

    @GetMapping("/gestionAdresse/{id}")
    public AdresseDTO getById(@PathVariable("id") UUID uuid) {
        return super.read(uuid);
    }

    @PostMapping("/gestionAdresse")
    public void modif(@RequestBody AdresseDTO objDTO) {
        super.update(objDTO);
    }

    @DeleteMapping("/gestionAdresse/{id}")
    public void suppr(@PathVariable("id") UUID id) {
        super.delete(id);
    }

    @GetMapping("/gestionAdresse")
    public List<AdresseDTO> list() {
        return super.list();
    }

}
