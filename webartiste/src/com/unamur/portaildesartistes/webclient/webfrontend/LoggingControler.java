package com.unamur.portaildesartistes.webclient.webfrontend;

import  com.unamur.portaildesartistes.webclient.PropertiesConfigurationService;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PropertiesConfigurationService configurationService ;

    @GetMapping(value = "/login")//initialisation du login
    public String loginView(Map<String, Object> model) {
        UserDto userDto = new UserDto();
        model.put("userForm", userDto);
        return "loginForm";
    }

    @PostMapping(value = "/login")
    public ModelAndView login(@Valid @ModelAttribute("userForm") UserDto userForm,
                              BindingResult bindingResult, ModelAndView modelAndView) {

        modelAndView.setViewName("loginForm");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }
        Map<String, String> variables = new HashMap<String, String>(1);
        variables.put("loginName", userForm.getLogin());

        ResponseEntity<User> userExists =  restTemplate.getForEntity(configurationService.getUrl() +"user/users/{loginName}", User.class, variables);
        User user = userExists.getBody();
        if (user == null) {//ceci nous évite d'écrire une page de gestion des erreurs
            modelAndView.addObject("saveError", "Aucun utilisateur trouvé avec ce compte");
            return modelAndView;
        }
        modelAndView.setViewName("loginSuccess");
        return modelAndView;
    }

    @GetMapping(value = "/registration")//initialisation du formulaire de création du compte
    public String registrationView(Map<String, Object> model) {
        UserRegistrationForm userRegistrationForm = new UserRegistrationForm();
        model.put("registrationForm", userRegistrationForm);
        return "registrationForm";
    }

    @PostMapping(value = "/registration")
    public ModelAndView saveUser(@Valid @ModelAttribute("registrationForm") UserRegistrationForm userRegistrationForm,
                                 BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("registrationForm");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }
        Map<String, String> variables = new HashMap<String, String>(1);
        variables.put("loginName", userRegistrationForm.getLogin());

        ResponseEntity<User> userEntity =  restTemplate.getForEntity(configurationService.getUrl() +"user/users/{loginName}", User.class, variables);
        User userExists = userEntity.getBody();
        if (userExists != null) {//ceci nous évite d'écrire une page de gestion des erreurs
            logger.info("userExists", userExists.toString());
            bindingResult.rejectValue("login", "error.user", "Un utilisateur est déjà enregistré avec ce compte");//Note: la variable login doit être un attribut de l'objet User, sinon erreur
            return modelAndView;
        }

        //return "loginSuccess";
        ResponseEntity<User> userEntitySaved = restTemplate.postForEntity(configurationService.getUrl() +"user/users", new User(userRegistrationForm), User.class);//point de liaison entre le client REST et le serveur REST grace à RestTemplate
        User userSaved = userEntitySaved.getBody();
        if(null ==userSaved){
            modelAndView.addObject("saveError", "erreur de création du compte.");
            return modelAndView;
        }
        modelAndView.setViewName("loginSuccess");
        modelAndView.addObject("userSaved", userSaved);
        return modelAndView;
    }
}