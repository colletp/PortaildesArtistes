package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.DTO.ReponseDTO;
import com.unamur.portaildesartistes.wsartiste.Service.ReponseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ReponseServiceFront extends ServiceFront<ReponseDTO>{
    private static final Logger logger = LoggerFactory.getLogger(ReponseServiceFront.class);

    @PutMapping("/gestionReponse")
    public UUID creer( @RequestBody ReponseDTO objDTO ){ return super.create(objDTO); }
    @GetMapping("/gestionReponse/{id}")
    public ReponseDTO getById( @PathVariable("id") UUID uuid ){ return super.read(uuid); }
    @PostMapping("/gestionReponse")
    public void modif( @RequestBody ReponseDTO objDTO ){ super.update(objDTO); }
    @DeleteMapping("/gestionReponse/{id}")
    public void suppr( @PathVariable("id") UUID id ){ super.delete(id); }

    @GetMapping("/gestionReponse")
    public List<ReponseDTO> list(){ return super.list(); }

}
