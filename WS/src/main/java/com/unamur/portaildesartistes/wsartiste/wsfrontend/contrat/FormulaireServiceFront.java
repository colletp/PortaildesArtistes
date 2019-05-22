package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.DTO.FormulaireDTO;
import com.unamur.portaildesartistes.wsartiste.Business.FormulaireServiceImpl;
import com.unamur.portaildesartistes.wsartiste.Business.UtilisateurServiceImpl;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeUtilisateurImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class FormulaireServiceFront extends ServiceFront<FormulaireDTO>{
    private static final Logger logger = LoggerFactory.getLogger(FormulaireServiceFront.class);

    @Autowired
    private UtilisateurServiceImpl usrServImpl;

    @PutMapping("/gestionFormulaire/")
    public UUID creer( @SessionAttribute("userName") String myUser
            , @RequestBody FormulaireDTO objDTO ){
        objDTO.setCitoyenId( usrServImpl.getUuidByName(myUser) );
        return super.create(objDTO);
    }
    @GetMapping("/gestionFormulaire/{id}")
    public FormulaireDTO getById( @PathVariable("id") UUID uuid ){ return super.read(uuid); }
    @PostMapping("/gestionFormulaire/")
    public void modif( @RequestBody FormulaireDTO objDTO ){ super.update(objDTO); }
    @DeleteMapping("/gestionFormulaire/{id}")
    public void suppr( @PathVariable("id") UUID id ){ super.delete(id); }

    @GetMapping("/gestionFormulaire/")
    public List<FormulaireDTO> list(){ return super.list(); }

	
    @GetMapping("/gestionFormulaire/myForms")
    public List<FormulaireDTO> myForms( @SessionAttribute("userName") String myUser ){
		return ((FormulaireServiceImpl)srvImpl).getByCitoyenId( usrServImpl.getUuidByName(myUser) );
    }

    @GetMapping("/gestionFormulaire/invalidate/{formId}")
    public Integer invalidate( @PathVariable("formId") UUID formId , @SessionAttribute("userName") String myUser ){
		return ((FormulaireServiceImpl)srvImpl).invalidate( formId, usrServImpl.getUuidByName(myUser) );
	}
	
    @GetMapping("/gestionFormulaire/aTraiter/lang/{lang}")
    public List<FormulaireDTO> getATraiterByLang( @PathVariable("lang") String lang ){
        return ((FormulaireServiceImpl)srvImpl).listByLangNoTrt(lang);
    }
    @GetMapping("/gestionFormulaire/enCours/lang/{lang}")
    public List<FormulaireDTO> getEnCoursByLang( @PathVariable("lang") String lang ){
        return ((FormulaireServiceImpl)srvImpl).listByLangTrtNotDone(lang);
    }
    @GetMapping("/gestionFormulaire/fini/lang/{lang}")
    public List<FormulaireDTO> getFiniByLang( @PathVariable("lang") String lang ){
        return ((FormulaireServiceImpl)srvImpl).listByLangTrtDone(lang);
    }
}
