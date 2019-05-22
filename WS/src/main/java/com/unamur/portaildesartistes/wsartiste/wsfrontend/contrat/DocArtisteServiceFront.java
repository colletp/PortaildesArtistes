package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.DTO.DocArtisteDTO;
import com.unamur.portaildesartistes.wsartiste.Business.DocArtisteServiceImpl;
import com.unamur.portaildesartistes.wsartiste.Business.UtilisateurServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class DocArtisteServiceFront extends ServiceFront<DocArtisteDTO>{
    private static final Logger logger = LoggerFactory.getLogger(DocArtisteServiceFront.class);

    @Autowired
    UtilisateurServiceImpl usrServImpl;

    @GetMapping("/gestionDocArtiste/myDocs")
    public List<DocArtisteDTO> myDocs( @SessionAttribute("userName") String myUser ){
		return ((DocArtisteServiceImpl)srvImpl).getByCitoyenId( usrServImpl.getUuidByName(myUser) );
    }
	
    @PutMapping("/gestionDocArtiste")
    public UUID creer( @RequestBody DocArtisteDTO objDTO ){ return super.create(objDTO); }
    @GetMapping("/gestionDocArtiste/{id}")
    public DocArtisteDTO getById( @PathVariable("id") UUID uuid ){ return super.read(uuid); }
    @PostMapping("/gestionDocArtiste")
    public void modif( @RequestBody DocArtisteDTO objDTO ){ super.update(objDTO); }
    @DeleteMapping("/gestionDocArtiste/{id}")
    public void suppr( @PathVariable("id") UUID id ){ super.delete(id); }

    @GetMapping("/gestionDocArtiste")
    public List<DocArtisteDTO> list(){ return super.list(); }

    @GetMapping("/gestionDocArtiste/lang/{lang}")
    public List<DocArtisteDTO> listByLang(@PathVariable("lang") String lang){ return ((DocArtisteServiceImpl)srvImpl).listByLang(lang); }
}
