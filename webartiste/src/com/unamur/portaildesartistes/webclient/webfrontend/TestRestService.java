package com.unamur.portaildesartistes.webclient.webfrontend;

    import com.unamur.portaildesartistes.webclient.PropertiesConfigurationService;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.client.RestTemplate;
    import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestRestService {

    private static final Logger logger = LoggerFactory.getLogger(TestRestService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PropertiesConfigurationService configurationService ;

    @GetMapping(value="/")
    ModelAndView IsServerAvailable(ModelAndView modelAndView) {

        ResponseEntity<String> reponseServeur = restTemplate.getForEntity(configurationService.getUrl(), String.class);
        int codeReponseServeur= reponseServeur.getStatusCodeValue();
        String reponsePing="";
        if(codeReponseServeur!=200){
            logger.error("Réponse du serveur: "+codeReponseServeur+" ==> Serveur indisponible, votre application ne fonctionnera pas correctement");
            reponsePing=configurationService.getPingServeurKo();
        }else{
            reponsePing=configurationService.getPingServeurOk();
            logger.info(configurationService.getPingServeur(),reponsePing);
        }
        //construction de la vue
        modelAndView.setViewName("validationrestservice");
        modelAndView.addObject("urlServeur", configurationService.getUrl());
        modelAndView.addObject("pingServeur", reponsePing);
        modelAndView.addObject("profileActif", configurationService.getProfileActif());
        return modelAndView;
    }

    @GetMapping(value = "/error")
    public String error() {
        return "error";
    }

    @GetMapping("/suivant")
    ModelAndView next(ModelAndView modelAndView) {
        modelAndView.setViewName("suivant");
        modelAndView.addObject("message", configurationService.getMessage());
        return modelAndView;
    }
}
