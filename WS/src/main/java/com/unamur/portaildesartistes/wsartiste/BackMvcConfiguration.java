package com.unamur.portaildesartistes.wsartiste;

import com.unamur.portaildesartistes.config.WebMvcConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackMvcConfiguration extends WebMvcConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(BackMvcConfiguration.class);
/* important a garder car il reprend la WebMvcConfiguration du common grace a son extends */
}
