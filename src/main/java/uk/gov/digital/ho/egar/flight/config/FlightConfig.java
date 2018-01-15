package uk.gov.digital.ho.egar.flight.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages="uk.gov.digital.ho.egar.flight")
@EnableAutoConfiguration
public class FlightConfig {

}
