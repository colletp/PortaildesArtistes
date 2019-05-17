package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.wsartiste.Business.UtilisateurServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CitoyenServiceFront extends ServiceFront<CitoyenDTO> {
    private static final Logger logger = LoggerFactory.getLogger(CitoyenServiceFront.class);

    @PutMapping("/gestionCitoyen")
    public UUID creer( @RequestBody CitoyenDTO objDTO ){ return super.create(objDTO); }
    @GetMapping("/gestionCitoyen/{id}")
    public CitoyenDTO getById( @PathVariable("id") UUID uuid ){ return super.read(uuid); }
    @PostMapping("/gestionCitoyen")
    public void modif( @RequestBody CitoyenDTO objDTO ){ super.update(objDTO); }
    @DeleteMapping("/gestionCitoyen/{id}")
    public void suppr( @PathVariable("id") UUID id ){ super.delete(id); }

    @GetMapping("/gestionCitoyen")
    public List<CitoyenDTO> list(){ return super.list(); }
}
