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
public class ReponseServiceFront {

    private static final Logger logger = LoggerFactory.getLogger(ReponseServiceFront.class);

    @Autowired
    private ReponseServiceImpl reponseServiceImpl;

    @GetMapping("/gestionReponse")
    public List<ReponseDTO> listReponse(){ return reponseServiceImpl.list(); }

    @GetMapping("/gestionReponse/{id}")
    public ReponseDTO ReponseDetail(@PathVariable("id") UUID id ){ return reponseServiceImpl.getById(id); }

    @DeleteMapping("/gestionReponse/{id}")
    public void ReponseSuppr( @PathVariable("id") UUID id ){ reponseServiceImpl.delete(id); }

    @PutMapping("/gestionReponse")
    public UUID ReponseCreer( @RequestBody ReponseDTO frm ){ return reponseServiceImpl.insert(frm); }

    @PostMapping("/gestionReponse")
    public void ReponseModif( @RequestBody ReponseDTO frm ){ reponseServiceImpl.update(frm); }

}
