package com.pragma.powerup.infrastructure.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application-prod.yml")
@Profile("prod")
public class PropertiesSourceProd {
}
