package uk.gov.digital.ho.egar.aircraft.api.rest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;
import uk.gov.digital.ho.egar.aircraft.api.exceptions.AircraftApiException;
import uk.gov.digital.ho.egar.aircraft.api.exceptions.AircraftNotFoundAircraftApiException;
import uk.gov.digital.ho.egar.aircraft.api.exceptions.BadOperationAircraftApiException;
import uk.gov.digital.ho.egar.aircraft.model.Aircraft;
import uk.gov.digital.ho.egar.aircraft.model.AircraftWithUuid;
import uk.gov.digital.ho.egar.aircraft.services.AircraftService;
import uk.gov.digital.ho.egar.aircraft.services.impl.AircraftServiceDatabaseImpl;
import uk.gov.digital.ho.egar.aircraft.services.repository.AircraftPersistentRecordRepository;
import uk.gov.digital.ho.egar.aircraft.services.repository.AircraftPersistentRecordRepositoryTest;
import uk.gov.digital.ho.egar.aircraft.services.repository.model.AircraftPersistentRecord;
import uk.gov.digital.ho.egar.aircraft.utils.UriAircraftUtilities;
import uk.gov.digital.ho.egar.aircraft.utils.impl.UriAircraftUtilitiesImpl;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @See https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-testing-spring-boot-applications-testing-autoconfigured-jpa-test
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {AircraftPersistentRecordRepositoryTest.class})
@EnableAutoConfiguration
public class AircraftControllerTest {

    @Autowired
    private AircraftPersistentRecordRepository repo;

    private AircraftController restController;

    private static Errors errors;


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void initRestController() {
        if (this.restController == null) {
            AircraftService aircraftService = new AircraftServiceDatabaseImpl(repo);
            UriAircraftUtilities utilities = new UriAircraftUtilitiesImpl();
            this.restController = new AircraftController(aircraftService, utilities);
        }

        errors = mock(Errors.class);
        when(errors.hasErrors()).thenReturn(false);
    }

    @Test
    public void jpaSchemaCanBeMappedFromClasses() {
        assertThat(this.repo).isNotNull();
    }

    @Test
    public void canInitRestController() {
        assertThat(this.restController).isNotNull();
    }

    @Test
    public void canAddWithNoValues() throws AircraftApiException {
        // WITH
        repo.deleteAll();

        UUID userUuid = UUID.randomUUID();
        AircraftPersistentRecord record = new AircraftPersistentRecord();

        // WHEN
        ResponseEntity<Void> resp = restController.addAircraftRecord(userUuid, record, errors);

        //THEN
        // THEN
        assertThat(resp).isNotNull();
        assertThat(resp.getStatusCodeValue()).isEqualTo(303);
        assertThat(getAircraftUuid(resp)).isNotNull();
        assertThat(repo.count()).isEqualTo(1);
    }

    @Test
    public void canAddWithValues() throws AircraftApiException {
        // WITH
        repo.deleteAll();

        UUID userUuid = UUID.randomUUID();
        AircraftPersistentRecord record = AircraftPersistentRecord.builder()
                .base("abc")
                .registration("def")
                .taxesPaid(true)
                .type("helicopter")
                .build();

        // WHEN
        ResponseEntity<Void> resp = restController.addAircraftRecord(userUuid, record, errors);

        // THEN
        assertThat(resp).isNotNull();
        assertThat(resp.getStatusCodeValue()).isEqualTo(303);
        assertThat(getAircraftUuid(resp)).isNotNull();
        assertThat(repo.count()).isEqualTo(1);

        Aircraft persisted = repo.getOne(getAircraftUuid(resp));
        assertEquals(persisted.getBase(), "abc");
        assertEquals(persisted.getRegistration(), "def");
        assertEquals(persisted.getTaxesPaid(), true);
        assertEquals(persisted.getType(), "helicopter");

    }

    @Test(expected = BadOperationAircraftApiException.class)
    public void cannotAddWithAircraftId() throws AircraftApiException {
        // WITH
        repo.deleteAll();

        UUID userUuid = UUID.randomUUID();
        AircraftPersistentRecord record = AircraftPersistentRecord.builder()
                .aircraftUuid(UUID.randomUUID())
                .build();

        // WHEN
        restController.addAircraftRecord(userUuid, record, errors);
    }

    @Test(expected = BadOperationAircraftApiException.class)
    public void cannotAddWithUserId() throws AircraftApiException {
        // WITH
        repo.deleteAll();

        UUID userUuid = UUID.randomUUID();
        AircraftPersistentRecord record = AircraftPersistentRecord.builder()
                .userUuid(UUID.randomUUID())
                .build();

        // WHEN
        restController.addAircraftRecord(userUuid, record, errors);

    }


    @Test(expected = AircraftNotFoundAircraftApiException.class)
    public void cannotRetrieveNonExistentRecord() throws AircraftApiException {
        // WITH
        repo.deleteAll();

        // WHEN
        restController.getAircraftRecord(UUID.randomUUID(), UUID.randomUUID());

    }

    @Test(expected = AircraftNotFoundAircraftApiException.class)
    public void cannotRetrieveForDifferentUser() throws AircraftApiException {
        // WITH
        repo.deleteAll();
        UUID userUuid = UUID.randomUUID();
        UUID aircraftUuid = UUID.randomUUID();
        AircraftPersistentRecord record = AircraftPersistentRecord.builder()
                .userUuid(userUuid)
                .aircraftUuid(aircraftUuid)
                .build();
        repo.saveAndFlush(record);

        // WHEN
        restController.getAircraftRecord(UUID.randomUUID(), aircraftUuid);
    }

    @Test
    public void canRetrieveAircraft() throws AircraftApiException {
        // WITH
        repo.deleteAll();
        UUID userUuid = UUID.randomUUID();
        UUID aircraftUuid = UUID.randomUUID();
        AircraftPersistentRecord record = AircraftPersistentRecord.builder()
                .base("abc")
                .registration("def")
                .taxesPaid(true)
                .type("helicopter")
                .userUuid(userUuid)
                .aircraftUuid(aircraftUuid)
                .build();
        repo.saveAndFlush(record);

        // WHEN
        AircraftWithUuid resp = restController.getAircraftRecord(userUuid, aircraftUuid);

        //THEN
        assertThat(repo.count()).isEqualTo(1);

        assertEquals(resp.getAircraftUuid(), aircraftUuid);
        assertEquals(resp.getUserUuid(), userUuid);
        assertEquals(resp.getBase(), "abc");
        assertEquals(resp.getRegistration(), "def");
        assertEquals(resp.getTaxesPaid(), true);
        assertEquals(resp.getType(), "helicopter");
    }

    @Test(expected = AircraftNotFoundAircraftApiException.class)
    public void cannotUpdateNonExistentRecord() throws AircraftApiException {
        // WITH
        repo.deleteAll();
        UUID userUuid = UUID.randomUUID();
        UUID aircraftUuid = UUID.randomUUID();
        AircraftPersistentRecord record = AircraftPersistentRecord.builder()
                .userUuid(userUuid)
                .aircraftUuid(aircraftUuid)
                .build();

        // WHEN
        restController.updateAircraftRecord(userUuid, aircraftUuid,record, errors);

    }

    @Test(expected = AircraftNotFoundAircraftApiException.class)
    public void cannotUpdateForDifferentUser() throws AircraftApiException {
        // WITH
        repo.deleteAll();
        UUID userUuid = UUID.randomUUID();
        UUID aircraftUuid = UUID.randomUUID();
        AircraftPersistentRecord record = AircraftPersistentRecord.builder()
                .userUuid(userUuid)
                .aircraftUuid(aircraftUuid)
                .build();
        repo.saveAndFlush(record);

        // WHEN
        restController.updateAircraftRecord(UUID.randomUUID(),  aircraftUuid,record, errors);
    }

    @Test
    public void canUpdate() throws AircraftApiException {
        // WITH
        repo.deleteAll();
        UUID userUuid = UUID.randomUUID();
        UUID aircraftUuid = UUID.randomUUID();
        AircraftPersistentRecord record = AircraftPersistentRecord.builder()
                .base("abc")
                .registration("def")
                .taxesPaid(true)
                .type("helicopter")
                .userUuid(userUuid)
                .aircraftUuid(aircraftUuid)
                .build();
        repo.saveAndFlush(record);

        AircraftPersistentRecord request = AircraftPersistentRecord.builder()
                .base("EGLL")
                .registration("ABC123")
                .taxesPaid(false)
                .type("chopper")
                .userUuid(userUuid)
                .aircraftUuid(aircraftUuid)
                .build();

        // WHEN
        ResponseEntity<Void> resp = restController.updateAircraftRecord(userUuid, aircraftUuid, request, errors);

        // THEN
        assertThat(resp).isNotNull();
        assertThat(resp.getStatusCodeValue()).isEqualTo(303);
        assertThat(getAircraftUuid(resp)).isNotNull();
        assertThat(repo.count()).isEqualTo(1);

        Aircraft persisted = repo.getOne(getAircraftUuid(resp));
        assertEquals(persisted.getBase(), "EGLL");
        assertEquals(persisted.getRegistration(), "ABC123");
        assertEquals(persisted.getTaxesPaid(), false);
        assertEquals(persisted.getType(), "chopper");
    }

    private UUID getAircraftUuid(ResponseEntity<Void> response) {

        assertThat(response.getHeaders().containsKey("Location")).isTrue();

        String redirectPath = response.getHeaders().getLocation().getPath();
        String[] delimitedPath = redirectPath.split("/");
        String stringId = delimitedPath[delimitedPath.length - 1];
        return UUID.fromString(stringId);
    }
}
