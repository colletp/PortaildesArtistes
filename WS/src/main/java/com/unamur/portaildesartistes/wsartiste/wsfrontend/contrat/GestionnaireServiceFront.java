package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.DTO.GestionnaireDTO;
import com.unamur.portaildesartistes.wsartiste.Business.GestionnaireServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class GestionnaireServiceFront extends ServiceFront<GestionnaireDTO> {
    private static final Logger logger = LoggerFactory.getLogger(GestionnaireServiceFront.class);

    @GetMapping("/gestionGestionnaire/citoyen/{id}")
    public GestionnaireDTO getByCitoyenId( @PathVariable("id") UUID uuid ){ return ((GestionnaireServiceImpl)srvImpl).getByCitoyenId(uuid); }

    @PutMapping("/gestionGestionnaire")
    public UUID creer( @RequestBody GestionnaireDTO objDTO ){ return super.create(objDTO); }
    @GetMapping("/gestionGestionnaire/{id}")
    public GestionnaireDTO getById( @PathVariable("id") UUID uuid ){ return super.read(uuid); }
    @PostMapping("/gestionGestionnaire")
    public void modif( @RequestBody GestionnaireDTO objDTO ){ super.update(objDTO); }
    @DeleteMapping("/gestionGestionnaire/{id}")
    public void suppr( @PathVariable("id") UUID id ){ super.delete(id); }

    @GetMapping("/gestionGestionnaire")
    public List<GestionnaireDTO> list(){ return super.list(); }
}
