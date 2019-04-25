package com.unamur.portaildesartistes.wsartiste;

import com.unamur.portaildesartistes.config.WebMvcConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.TimeZone;

@SpringBootApplication(exclude = {JpaRepositoriesAutoConfiguration.class
                                    ,DataSourceAutoConfiguration.class
                                    ,DataSourceTransactionManagerAutoConfiguration.class
                            } )

// Disable Auto Config DataSource & DataSourceTransactionManager

// Classe d'entrée de l'application utilisée par SpringBoot
// Point d'entrée d'une application SpringBoot
// Doit être située à la racine du package principal !!!!!!
// @EnableWebMVC pas nécessaire car SpringBoot ajoute automatiquement si il detecte qu'une librairie sprinng-webmvc est présente dans les dépendances.
// TODO ???
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringBootWSRestPortailArtiste {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootWSRestPortailArtiste.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWSRestPortailArtiste.class, args);
    }

    @PostConstruct
    public void postConstruct() {
        // set the JVM timezone to UTC
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
    @Autowired
    @Bean(name = "dataSource")
    public DataSource getDataSource(DataSource dataSourceTest) {
        MyRoutingDataSource dataSource = new MyRoutingDataSource();
        dataSource.initDataSources(dataSourceTest);
        return dataSource;
    }

    // Load to Environment
    @Autowired
    private Environment env;

    @Bean(name = "dataSourceTest")
    public DataSource getDataSourceTest() throws SQLException {
        String instance = env.getProperty("spring.datasource.default");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name."+instance));
        dataSource.setUrl(env.getProperty("spring.datasource.url."+instance));
        dataSource.setUsername(env.getProperty("spring.datasource.username."+instance));
        dataSource.setPassword(env.getProperty("spring.datasource.password."+instance));
        logger.info("Initializing DataSourceTest:"+instance );
        return dataSource;
    }

    @Autowired
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager getTransactionManager(DataSource dataSource) {
        logger.info("transactionManager");
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(dataSource);
        return txManager;
    }
}
