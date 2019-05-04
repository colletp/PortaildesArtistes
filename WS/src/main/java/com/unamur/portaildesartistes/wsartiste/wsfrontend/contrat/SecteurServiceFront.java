package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.DTO.ActiviteDTO;
import com.unamur.portaildesartistes.DTO.SecteurDTO;
import com.unamur.portaildesartistes.wsartiste.Service.ActiviteServiceImpl;
import com.unamur.portaildesartistes.wsartiste.Service.SecteurServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class SecteurServiceFront {

    private static final Logger logger = LoggerFactory.getLogger(SecteurServiceFront.class);

    @Autowired
    private SecteurServiceImpl secteurServiceImpl;

    @GetMapping("/gestionSecteur")
    public List<SecteurDTO> list(){ return secteurServiceImpl.list(); }

    @GetMapping("/gestionSecteur/Activite")
    public List<SecteurDTO> listSecteurActivite(){ return secteurServiceImpl.listActivite(); }

    @GetMapping("/gestionSecteur/{id}")
    public SecteurDTO get( @PathVariable("id") UUID id ){ return secteurServiceImpl.getById(id); }

    @DeleteMapping("/gestionSecteur/{id}")
    public void suppr( @PathVariable("id") UUID id ){ secteurServiceImpl.delete(id); }

    @PutMapping("/gestionSecteur")
    public UUID creer( @RequestBody SecteurDTO sec ){ return secteurServiceImpl.insert(sec); }

    @PostMapping("/gestionSecteur")
    public void modif( @RequestBody SecteurDTO sec ){ secteurServiceImpl.update(sec); }

}
