package com.unamur.portaildesartistes.wsartiste;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface Controller {

    @PutMapping(value = "/inscript")
    public @ResponseBody ResponseEntity wsInscript(@RequestBody UtilisateurDTO test);

}