package com.unamur.portaildesartistes.wsartiste;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// Utiliser à la place du fichier web.xml (springboot)
// Class de configuration qui remplace le fichier web.xml dans SpringBoot
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootWSRestPortailArtiste.class);
    }
}