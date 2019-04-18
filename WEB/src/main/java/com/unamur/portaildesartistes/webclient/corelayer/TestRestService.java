package com.unamur.portaildesartistes.webclient.corelayer;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
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

    @GetMapping(value="/validationrestservice.html")
//@PreAuthorize("hasRole('AUTHOR')")
    public String validationrestservice(Model model) {
        ResponseEntity<String> reponseServeur = restTemplate.getForEntity(configurationService.getUrl(), String.class);
        int codeReponseServeur= reponseServeur.getStatusCodeValue();
        String reponseServerAvailable;
        if(codeReponseServeur!=200){
            logger.error("Réponse du serveur: "+codeReponseServeur+" ==> Serveur indisponible, votre application ne fonctionnera pas correctement");
            reponseServerAvailable=configurationService.getPingServeurKo();
        }else{
            reponseServerAvailable=configurationService.getPingServeurOk();
            logger.info(configurationService.getPingServeur(),reponseServerAvailable);
        }

        model.addAttribute("urlServeur", configurationService.getUrl() );
        model.addAttribute("pingServeur", reponseServerAvailable );
        model.addAttribute("profileActif", configurationService.getProfileActif() );

        return "validationrestservice.html";
    }

    @GetMapping(value="/mainCSS.css")
    public String css(Model model) {
        return "mainCSS.css";
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
