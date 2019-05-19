package com.unamur.portaildesartistes.webclient.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class PropertiesConfigurationService{

    @Autowired
    Environment env;

    public String getUrl() { return env.getProperty("back-end.server.url")+getBackEndPath(); }
    public String getBackEndPath() {
        return env.getProperty("back-end.server.servlet.contextPath");
    }

    public String getFrontEndPath() {
        return env.getProperty("server.servlet.contextPath");
    }

    public String getPingServeur() {
        return env.getProperty("app.serveur.pingServeur");
    }
    public String getPingServeurOk() {
        return env.getProperty("app.serveur.ok");
    }
    public String getPingServeurKo() {
        return env.getProperty("app.serveur.ko");
    }
    public String getMessage() {
        return env.getProperty("nextpage.message");
    }
    public String getProfileActif() {
        return env.getProperty("spring.profiles.active");
    }

/*
        @Value("${app.serveur.url}") // injection via application.properties
        private String url;

        @Value("${app.serveur.pingServeur}")
        private String pingServeur;

        @Value("${app.serveur.ok}")
        private String pingServeurOk;

        @Value("${app.serveur.ko}")
        private String pingServeurKo;

        @Value("${nextpage.message}")
        private String message;

        @Value("${spring.profiles.active}")
        private String profileActif;
        // getters, setters
        public String getUrl() {
            return url;
        }
        public String getPingServeur() {
            return pingServeur;
        }
        public String getPingServeurOk() {
            return pingServeurOk;
        }
        public String getPingServeurKo() {
            return pingServeurKo;
        }
        public String getMessage() {
            return message;
        }
        public String getProfileActif() {
            return profileActif;
        }
*/
}
