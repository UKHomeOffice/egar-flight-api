package uk.gov.digital.ho.egar.aircraft;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.digital.ho.egar.aircraft.api.PathConstants;
import uk.gov.digital.ho.egar.aircraft.api.rest.AircraftController;
import uk.gov.digital.ho.egar.aircraft.model.Aircraft;
import uk.gov.digital.ho.egar.aircraft.services.AircraftService;
import uk.gov.digital.ho.egar.aircraft.services.repository.model.AircraftPersistentRecord;
import uk.gov.digital.ho.egar.aircraft.utils.UriAircraftUtilities;
import uk.gov.digital.ho.egar.aircraft.utils.impl.UriAircraftUtilitiesImpl;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static uk.gov.digital.ho.egar.aircraft.test.utils.FileReaderUtils.readFileAsString;

public class DeserialisationTests {

    public static final String USERID_HEADER = "x-auth-subject";
    public static final String AUTH_HEADER = "Authorization";

    @Mock
    private AircraftService aircraftServices;

    private AircraftController underTest;

    private MockMvc mockMvc;

    @Before
    public void contextLoads() {
        MockitoAnnotations.initMocks(this);

        UriAircraftUtilities utilities = new UriAircraftUtilitiesImpl();

        underTest = new AircraftController(aircraftServices, utilities);

        mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
    }

    @Test
    public void deserialisesCorrectly() throws Exception {
        String jsonRep = readFileAsString("files/DeserialisationRequest.json");
        UUID userUuid = UUID.randomUUID();

        AircraftPersistentRecord savedAircraft = new AircraftPersistentRecord();
        savedAircraft.setAircraftUuid(UUID.randomUUID());

        when(aircraftServices.addAircraftRecord(any(Aircraft.class), eq(userUuid))).thenReturn(savedAircraft);

        this.mockMvc
                .perform(post("http://localhost:8082"+PathConstants.ROOT_PATH + PathConstants.PATH_ADD_AIRCRAFT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_HEADER, "TEST")
                        .header(USERID_HEADER, userUuid)
                        .content(jsonRep));



        ArgumentCaptor<Aircraft> captor = ArgumentCaptor.forClass(Aircraft.class);

        Mockito.verify(aircraftServices).addAircraftRecord(captor.capture(), any());

        Aircraft target = captor.getValue();

        //Aircraft assertions
        assertEquals(target.getBase(),("EGLL"));
        assertEquals(target.getRegistration(), ("abc123"));
        assertEquals(target.getType(), ("helicopter"));
        assertTrue(target.getTaxesPaid());
    }

    @Test
    public void deserialisesCorrectlyWithNullBoolean() throws Exception {
        String jsonRep = readFileAsString("files/DeserialisationWithNullBooleanRequest.json");
        UUID userUuid = UUID.randomUUID();

        AircraftPersistentRecord savedAircraft = new AircraftPersistentRecord();
        savedAircraft.setAircraftUuid(UUID.randomUUID());

        when(aircraftServices.addAircraftRecord(any(Aircraft.class), eq(userUuid))).thenReturn(savedAircraft);

        this.mockMvc
                .perform(post("http://localhost:8082"+PathConstants.ROOT_PATH + PathConstants.PATH_ADD_AIRCRAFT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_HEADER, "TEST")
                        .header(USERID_HEADER, userUuid)
                        .content(jsonRep));



        ArgumentCaptor<Aircraft> captor = ArgumentCaptor.forClass(Aircraft.class);

        Mockito.verify(aircraftServices).addAircraftRecord(captor.capture(), any());

        Aircraft target = captor.getValue();

        //Aircraft assertions
        assertEquals(target.getBase(),("EGLL"));
        assertEquals(target.getRegistration(), ("abc123"));
        assertEquals(target.getType(), ("helicopter"));
        assertNull(target.getTaxesPaid());
    }
}
