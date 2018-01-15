package uk.gov.digital.ho.egar.test.aircraft.config;

import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("uk.gov.digital.ho.egar.aircraft")
public class AircraftPersistenceTestConfig {
//	@Bean
//	AircraftController getAircraftController() {
//		return new AircraftController();
//	}

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory factory = 
				new TomcatEmbeddedServletContainerFactory();
		return factory;
	}
}
