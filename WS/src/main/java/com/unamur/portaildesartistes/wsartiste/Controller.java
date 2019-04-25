package com.unamur.portaildesartistes.wsartiste;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;
import com.unamur.portaildesartistes.DTO.CustomWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface Controller {

    @PostMapping(value = "/inscript")
    public @ResponseBody ResponseEntity wsInscript(@RequestBody CustomWrapper<UtilisateurDTO> test);

}