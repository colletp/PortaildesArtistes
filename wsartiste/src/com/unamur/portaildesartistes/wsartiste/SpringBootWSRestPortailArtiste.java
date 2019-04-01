package com.unamur.portaildesartistes.wsartiste;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import javax.annotation.PostConstruct;


import java.util.TimeZone;

@SpringBootApplication(exclude = JpaRepositoriesAutoConfiguration.class)
// Classe d'entrée de l'application utilisée par SpringBoot
// Point d'entrée d'une application SpringBoot
// Doit être située à la racine du package principal !!!!!!
// @EnableWebMVC pas nécessaire car SpringBoot ajoute automatiquement si il detecte qu'une librairie sprinng-webmvc est présente dans les dépendances.
// TODO ??? @EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringBootWSRestPortailArtiste {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWSRestPortailArtiste.class, args);
    }

    @PostConstruct
    public void postConstruct() {
        // set the JVM timezone to UTC
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

    }

}
