package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.DTO.FormulaireDTO;
import com.unamur.portaildesartistes.wsartiste.Service.FormulaireServiceImpl;
import com.unamur.portaildesartistes.wsartiste.Service.UtilisateurServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@RestController
public class FormulaireServiceFront extends ServiceFront<FormulaireDTO>{
    private static final Logger logger = LoggerFactory.getLogger(FormulaireServiceFront.class);

    @Autowired
    private UtilisateurServiceImpl utilisateurServiceImpl;

    @PutMapping("/gestionFormulaire")
    public UUID creer( @SessionAttribute("userName") String myUser, @RequestBody FormulaireDTO objDTO ){ objDTO.setCitoyenId( utilisateurServiceImpl.getUuidByName(myUser) ); return super.create(objDTO); }
    @GetMapping("/gestionFormulaire/{id}")
    public FormulaireDTO getById( @PathVariable("id") UUID uuid ){ return super.read(uuid); }
    @PostMapping("/gestionFormulaire")
    public void modif( @RequestBody FormulaireDTO objDTO ){ super.update(objDTO); }
    @DeleteMapping("/gestionFormulaire/{id}")
    public void suppr( @PathVariable("id") UUID id ){ super.delete(id); }

    @GetMapping("/gestionFormulaire")
    public List<FormulaireDTO> list(){ return super.list(); }

}
