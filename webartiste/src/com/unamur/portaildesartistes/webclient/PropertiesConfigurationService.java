package com.unamur.portaildesartistes.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

    @Service
    public class PropertiesConfigurationService{

        @Value("${app.serveur.url}") // injection via application.properties
        private String url="";

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
    }
