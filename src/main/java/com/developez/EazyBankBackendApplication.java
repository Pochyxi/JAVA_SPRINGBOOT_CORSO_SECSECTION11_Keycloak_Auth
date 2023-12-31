package com.developez;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableMethodSecurity( prePostEnabled = true, securedEnabled = true, jsr250Enabled = true )
@PropertySource("classpath:application-secret.properties")
public class EazyBankBackendApplication {

    public static void main( String[] args ) {
        SpringApplication.run( EazyBankBackendApplication.class, args );
    }

}
