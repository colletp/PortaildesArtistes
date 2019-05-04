package com.unamur.portaildesartistes.webclient.corelayer;

    import com.unamur.portaildesartistes.webclient.RestTemplateHelper;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.client.HttpClientErrorException;
    import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestRestService {

    private static final Logger logger = LoggerFactory.getLogger(TestRestService.class);

    @Autowired
    private RestTemplateHelper restTemplateHelper;

    @Autowired
    private PropertiesConfigurationService configurationService ;

    @GetMapping(value="/validationrestservice")
//@PreAuthorize("hasRole('AUTHOR')")
    public String validationrestservice(Model model) {

        String reponseServeur, reponseServerAvailable;
        int codeReponseServeur;
        try {
            reponseServeur = restTemplateHelper.getForEntity(String.class, configurationService.getUrl(), new HttpHeaders());
            reponseServerAvailable = configurationService.getPingServeurOk();
            codeReponseServeur=200;
            logger.info(configurationService.getPingServeur(), reponseServerAvailable);
        }catch(HttpClientErrorException e){
            codeReponseServeur = e.getStatusCode().value();
            logger.error("Réponse du serveur: "+codeReponseServeur+" ==> Serveur indisponible, votre application ne fonctionnera pas correctement");
            reponseServerAvailable=configurationService.getPingServeurKo();
        }

        model.addAttribute("urlServeur", configurationService.getUrl() );
        model.addAttribute("pingServeur", reponseServerAvailable );
        model.addAttribute("profileActif", configurationService.getProfileActif() );

        return "validationrestservice.html";
    }

    @GetMapping("/suivant")
    ModelAndView next(ModelAndView modelAndView) {
        modelAndView.setViewName("suivant");
        modelAndView.addObject("message", configurationService.getMessage());
        return modelAndView;
    }
}
