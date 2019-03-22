package com.unamur.portaildesartistes.webclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.actuate.health.Health;
import javax.annotation.PostConstruct;

import java.util.TimeZone;

@org.springframework.boot.autoconfigure.SpringBootApplication(exclude = org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration.class)
// Classe d'entrée de l'application utilisée par SpringBoot
// Point d'entrée d'une application SpringBoot
// Doit être située à la racine du package principal !!!!!!
// @EnableWebMVC pas nécessaire car SpringBoot ajoute automatiquement si il detecte qu'une librairie sprinng-webmvc est présente dans les dépendances.
// TODO ??? @EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringBootWebClientPortailArtiste  {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringBootWebClientPortailArtiste.class, args);
    }

    @javax.annotation.PostConstruct
    public void postConstruct() {
        // set the JVM timezone to UTC
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));

    }
}