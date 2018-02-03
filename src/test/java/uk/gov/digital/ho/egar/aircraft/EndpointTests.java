package uk.gov.digital.ho.egar.aircraft;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.gov.digital.ho.egar.aircraft.api.exceptions.AircraftApiException;
import uk.gov.digital.ho.egar.aircraft.api.exceptions.AircraftNotFoundAircraftApiException;
import uk.gov.digital.ho.egar.aircraft.model.Aircraft;
import uk.gov.digital.ho.egar.aircraft.model.AircraftWithUuid;
import uk.gov.digital.ho.egar.aircraft.services.repository.AircraftPersistentRecordRepository;
import uk.gov.digital.ho.egar.aircraft.services.repository.model.AircraftPersistentRecord;
import uk.gov.digital.ho.egar.aircraft.test.utils.FileReaderUtils;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.digital.ho.egar.aircraft.test.utils.matcher.RegexMatcher.matchesRegex;

@RunWith(SpringRunner.class)
@SpringBootTest(properties
        ={
        "eureka.client.enabled=false",
        "spring.cloud.config.discovery.enabled=false",
        "flyway.enabled=false",
        "spring.profiles.active=h2",
        "spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true"
})
@AutoConfigureMockMvc
public class EndpointTests {

    private static final String USER_HEADER_NAME = "x-auth-subject";
    public final String REGEX_UUID = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";

    @Autowired
    private AircraftPersistentRecordRepository repo;

    @Autowired
    private AircraftApplication app;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        assertThat(app).isNotNull();
    }


    @Test
    public void jpaSchemaCanBeMappedFromClasses() {
        assertThat(this.repo).isNotNull();
    }

    @Test()
    public void cannotPostWithoutUserAuth() throws Exception {

        // WITH

        String jsonAircraft = FileReaderUtils.readFileAsString("files/CannotPostWithoutUserAuthRequest.json");

        // WHEN
        this.mockMvc
                .perform(post("/api/v1/aircraft/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(jsonAircraft)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());

        // THEN
    }

    @Test
    public void canPostWithNoValues() throws Exception {
        // WITH
        repo.deleteAll();

        UUID userUuid = UUID.randomUUID();

        String jsonAircraft = FileReaderUtils.readFileAsString("files/CanPostWithNoValuesRequest.json");

        // WHEN
        MvcResult resp = this.mockMvc
                .perform(post("/api/v1/aircraft/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(jsonAircraft)
                        .header(USER_HEADER_NAME, userUuid)
                )
                .andDo(print())
                .andExpect(status().isSeeOther())
                .andReturn();

        // THEN
        assertThat(repo.count()).isEqualTo(1);

        AircraftWithUuid persisted = repo.getOne(getAircraftUuid(resp));
        assertNull(persisted.getBase());
        assertNull(persisted.getRegistration());
        assertNull(persisted.getTaxesPaid());
        assertNull(persisted.getType());
        assertEquals(persisted.getUserUuid(), userUuid);
    }

    @Test
    public void canPostWithValues() throws Exception {
        // WITH
        repo.deleteAll();

        UUID userUuid = UUID.randomUUID();

        String jsonAircraft = FileReaderUtils.readFileAsString("files/CanPostWithValuesRequest.json");

        // WHEN
        MvcResult resp = this.mockMvc
                .perform(post("/api/v1/aircraft/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(jsonAircraft)
                        .header(USER_HEADER_NAME, userUuid)
                )
                .andDo(print())
                .andExpect(status().isSeeOther())
                .andReturn();

        // THEN
        assertThat(repo.count()).isEqualTo(1);

        AircraftWithUuid persisted = repo.getOne(getAircraftUuid(resp));
        assertEquals(persisted.getBase(), "EGLL");
        assertEquals(persisted.getRegistration(), "abc123");
        assertEquals(persisted.getTaxesPaid(), true);
        assertEquals(persisted.getType(), "helicopter");
        assertEquals(persisted.getUserUuid(), userUuid);
    }

    @Test
    public void cannotPostAddWithAircraftId() throws Exception {
        // WITH
        repo.deleteAll();

        UUID userUuid = UUID.randomUUID();

        String jsonAircraft = FileReaderUtils.readFileAsString("files/CannotPostWithAircraftIdRequest.json");

        // WHEN
        this.mockMvc
                .perform(post("/api/v1/aircraft/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(jsonAircraft)
                        .header(USER_HEADER_NAME ,userUuid)
                )
                .andDo(print())
                .andExpect(status().isForbidden());

        // THEN
        assertThat(repo.count()).isEqualTo(0);
    }

    @Test
    public void cannotRetrieveNonExistentRecord() throws Exception {
        // WITH
        repo.deleteAll();

        UUID userUuid = UUID.randomUUID();
        UUID aircraftUUID = UUID.randomUUID();
        // WHEN
        this.mockMvc
                .perform(get("/api/v1/aircraft/"+aircraftUUID)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header(USER_HEADER_NAME ,userUuid)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

        // THEN

    }

    @Test
    public void cannotRetrieveForDifferentUser() throws Exception {
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
        this.mockMvc
                .perform(get("/api/v1/aircraft/"+aircraftUuid)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header(USER_HEADER_NAME ,UUID.randomUUID())
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

        // THEN
    }

    @Test
    public void canRetrieveAircraft() throws Exception {
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
        this.mockMvc
                .perform(get("/api/v1/aircraft/"+aircraftUuid)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header(USER_HEADER_NAME ,userUuid)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.aircraft_uuid", matchesRegex(REGEX_UUID)))
                .andExpect(jsonPath("$.registration", is("def")))
                .andExpect(jsonPath("$.base", is("abc")))
                .andExpect(jsonPath("$.type", is("helicopter")))
                .andExpect(jsonPath("$.taxesPaid", is(true)));


        //THEN
        assertThat(repo.count()).isEqualTo(1);
    }

    @Test
    public void cannotUpdateNonExistentRecord() throws Exception {
        // WITH
        repo.deleteAll();

        UUID userUuid = UUID.randomUUID();
        UUID aircraftUuid = UUID.randomUUID();

        String jsonAircraft = FileReaderUtils.readFileAsString("files/CannotUpdateNonExistentRecordRequest.json");

        // WHEN
        this.mockMvc
                .perform(post("/api/v1/aircraft/"+aircraftUuid)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(jsonAircraft)
                        .header(USER_HEADER_NAME ,userUuid)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void cannotUpdateForDifferentUser() throws Exception {
        // WITH
        repo.deleteAll();
        UUID userUuid = UUID.randomUUID();
        UUID aircraftUuid = UUID.randomUUID();
        AircraftPersistentRecord record = AircraftPersistentRecord.builder()
                .userUuid(userUuid)
                .aircraftUuid(aircraftUuid)
                .build();
        repo.saveAndFlush(record);

        String jsonAircraft = FileReaderUtils.readFileAsString("files/CannotUpdateForDifferentUserRequest.json");

        // WHEN
        this.mockMvc
                .perform(post("/api/v1/aircraft/"+aircraftUuid)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(jsonAircraft)
                        .header(USER_HEADER_NAME ,UUID.randomUUID())
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void canPostUpdate() throws Exception {
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

        String jsonAircraft = FileReaderUtils.readFileAsString("files/CanPostUpdateRequest.json");

        // WHEN
        MvcResult resp = this.mockMvc
                .perform(post("/api/v1/aircraft/" + aircraftUuid)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(jsonAircraft)
                        .header(USER_HEADER_NAME, userUuid)
                )
                .andDo(print())
                .andExpect(status().isSeeOther())
                .andReturn();
        // THEN
        assertThat(resp).isNotNull();
        assertThat(getAircraftUuid(resp)).isNotNull();
        assertThat(repo.count()).isEqualTo(1);

        Aircraft persisted = repo.getOne(getAircraftUuid(resp));
        assertEquals(persisted.getBase(), "EGLL");
        assertEquals(persisted.getRegistration(), "ABC123");
        assertEquals(persisted.getTaxesPaid(), false);
        assertEquals(persisted.getType(), "chopper");
    }

    private UUID getAircraftUuid(MvcResult response) {

        assertThat(response.getResponse().containsHeader("Location")).isTrue();

        String redirectPath = response.getResponse().getHeader("Location");
        String[] delimitedPath = redirectPath.split("/");
        String stringId = delimitedPath[delimitedPath.length - 1];
        return UUID.fromString(stringId);
    }
}
