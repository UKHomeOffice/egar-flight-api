package uk.gov.digital.ho.egar.aircraft.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import uk.gov.digital.ho.egar.aircraft.services.repository.objects.AircraftDetailsDBEntity;

@Configuration
@EnableJpaRepositories(basePackages="uk.gov.digital.ho.egar.aircraft")
@EnableAutoConfiguration
public class AircraftConfig {
	@Bean
	AircraftDetailsDBEntity getAircraftDetailsDBEntity() {
		return new AircraftDetailsDBEntity();
	}
	
	
	
}
