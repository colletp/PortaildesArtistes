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
public class FormulaireServiceFront {

    private static final Logger logger = LoggerFactory.getLogger(FormulaireServiceFront.class);

    @Autowired
    private FormulaireServiceImpl formulaireServiceImpl;

    @Autowired
    private UtilisateurServiceImpl utilisateurServiceImpl;

    @GetMapping("/gestionFormulaire")
    public List<FormulaireDTO> listFormulaire(){ return formulaireServiceImpl.list(); }

    @GetMapping("/gestionFormulaire/{id}")
    public FormulaireDTO FormulaireDetail( @PathVariable("id") UUID id ){ return formulaireServiceImpl.getById(id); }

    @DeleteMapping("/gestionFormulaire/{id}")
    public void FormulaireSuppr( @PathVariable("id") UUID id ){ formulaireServiceImpl.delete(id); }

    @PutMapping("/gestionFormulaire")
    public UUID FormulaireCreer(@SessionAttribute("userName") String myUser, @RequestBody FormulaireDTO frm ){
        frm.setCitoyenId( utilisateurServiceImpl.getUuidByName(myUser) );
        return formulaireServiceImpl.insert(frm); }

    @PostMapping("/gestionFormulaire")
    public void FormulaireModif( @RequestBody FormulaireDTO frm ){ formulaireServiceImpl.update(frm); }

}
