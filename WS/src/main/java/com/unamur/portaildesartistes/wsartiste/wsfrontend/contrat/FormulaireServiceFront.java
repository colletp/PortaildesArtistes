package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.DTO.FormulaireDTO;
import com.unamur.portaildesartistes.wsartiste.Service.FormulaireServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Component
public class FormulaireServiceFront {

    private static final Logger logger = LoggerFactory.getLogger(FormulaireServiceFront.class);

    @Autowired
    private FormulaireServiceImpl formulaireServiceImpl;

    @GetMapping("/gestionFormulaire")
    public List<FormulaireDTO> listFormulaire(){ return formulaireServiceImpl.list(); }

    @GetMapping("/gestionFormulaire/{id}")
    public FormulaireDTO FormulaireDetail( @PathVariable("id") UUID id ){ return formulaireServiceImpl.getById(id); }

    @DeleteMapping("/gestionFormulaire/{id}")
    public void FormulaireSuppr( @PathVariable("id") UUID id ){ formulaireServiceImpl.delete(id); }

    @PutMapping("/gestionFormulaire")
    public UUID FormulaireCreer( @RequestBody FormulaireDTO frm ){ return formulaireServiceImpl.insert(frm); }

    @PostMapping("/gestionFormulaire")
    public void FormulaireModif( @RequestBody FormulaireDTO frm ){ formulaireServiceImpl.update(frm); }

}
