package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.DTO.DocArtisteDTO;
import com.unamur.portaildesartistes.wsartiste.Service.DocArtisteServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class DocArtisteServiceFront {

    private static final Logger logger = LoggerFactory.getLogger(DocArtisteServiceFront.class);

    @Autowired
    DocArtisteServiceImpl docArtisteServiceImpl;

    @GetMapping("/gestionDocArtiste")
    public List<DocArtisteDTO> listActivite(){ return docArtisteServiceImpl.list(); }

    @GetMapping("/gestionDocArtiste/{id}")
    public DocArtisteDTO ActiviteDetail( @PathVariable("id") UUID id ){ return docArtisteServiceImpl.getById(id); }

    @DeleteMapping("/gestionDocArtiste/{id}")
    public void ActiviteSuppr( @PathVariable("id") UUID id ){ docArtisteServiceImpl.delete(id); }

    @PutMapping("/gestionDocArtiste")
    public UUID ActiviteCreer( @RequestBody DocArtisteDTO act ){ return docArtisteServiceImpl.insert(act); }

    @PostMapping("/gestionDocArtiste")
    public void ActiviteModif( @RequestBody DocArtisteDTO act ){ docArtisteServiceImpl.update(act); }

}
