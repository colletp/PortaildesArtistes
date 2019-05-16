package com.unamur.portaildesartistes.wsartiste.wsfrontend.contrat;

import com.unamur.portaildesartistes.DTO.SecteurDTO;
import com.unamur.portaildesartistes.wsartiste.Business.SecteurServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class SecteurServiceFront extends ServiceFront<SecteurDTO> {
    private static final Logger logger = LoggerFactory.getLogger(SecteurServiceFront.class);

    @PutMapping("/gestionSecteur")
    public UUID creer( @RequestBody SecteurDTO objDTO ){ return super.create(objDTO); }
    @GetMapping("/gestionSecteur/{id}")
    public SecteurDTO getById( @PathVariable("id") UUID uuid ){ return super.read(uuid); }
    @PostMapping("/gestionSecteur")
    public void modif( @RequestBody SecteurDTO objDTO ){ super.update(objDTO); }
    @DeleteMapping("/gestionSecteur/{id}")
    public void suppr( @PathVariable("id") UUID id ){ super.delete(id); }

    @GetMapping("/gestionSecteur")
    public List<SecteurDTO> list(){ return super.list(); }

    @GetMapping("/gestionSecteur/Activite")
    public List<SecteurDTO> listSecteurActivite(){ return ((SecteurServiceImpl)srvImpl).listSecteurActivite(); }

    @GetMapping("/gestionSecteur/SecteurActivite/Form/{formId}")
    public List<SecteurDTO> listSecteurActiviteByForm(@PathVariable("formId")UUID formId){ return ((SecteurServiceImpl)srvImpl).listSecteurActiviteByFormId( formId ); }
    @GetMapping("/gestionSecteur/SecteurActivite/Doc/{docId}")
    public List<SecteurDTO> listSecteurActiviteByDoc(@PathVariable("docId")UUID docId){ return ((SecteurServiceImpl)srvImpl).listSecteurActiviteByDocId( docId ); }
}
