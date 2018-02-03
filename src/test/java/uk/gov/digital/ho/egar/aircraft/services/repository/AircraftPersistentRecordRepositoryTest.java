/**
 * 
 */
package uk.gov.digital.ho.egar.aircraft.services.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {AircraftPersistentRecordRepositoryTest.class})
@EnableAutoConfiguration
public class AircraftPersistentRecordRepositoryTest {

	@Autowired
    private AircraftPersistentRecordRepository repo;
	
	@Test
	public void jpaSchemaCanBeMappedFromClasses() {
	    assertThat(this.repo).isNotNull();
	}
	
}
