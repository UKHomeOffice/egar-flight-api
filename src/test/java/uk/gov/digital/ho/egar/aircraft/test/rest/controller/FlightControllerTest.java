package uk.gov.digital.ho.egar.aircraft.test.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import javax.ws.rs.core.MediaType;
import org.springframework.http.MediaType;

import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import uk.gov.digital.ho.egar.FlightControllerApplication;
import uk.gov.digital.ho.egar.aircraft.AircraftAPI;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(properties = { "eureka.client.enabled=false", "spring.cloud.config.discovery.enabled=false",
		"spring.profiles.active=test" })
@AutoConfigureMockMvc
public class FlightControllerTest {

	protected final Logger logger = LoggerFactory.getLogger(FlightControllerTest.class);
	@Autowired
    private FlightControllerApplication app;
	
	@Autowired
	private MockMvc mockMvc;
;
	private static String PATHTOTEST = AircraftAPI.ROOT_PATH + AircraftAPI.PATH_ADD_AIRCRAFT;
	String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1dWlkIjoiNzNiMTY3NmUtNjBiYS00Y2UyLWJiNjAtZTM4Y2E3NjU1MTM5Iiwic3ViIjoiMTIzNDU2Nzg5MCIsIm5hbWUiOiJKb2huIERvZSIsImFkbWluIjp0cnVlLCJqdGkiOiI1NTA1MGRmZi0wYTgwLTRmOTQtOTliMS0yZTdmNzcxYmFiNDEiLCJpYXQiOjE1MTAxMzkwMjEsImV4cCI6MTUxMDE0MjY3M30.kEm09EPiazSTIRXpMhdxyNHzq4-qj7BAHVNPTofhdnI";
	


	@Test
	public void shouldLoadContext() {
		logger.info("TEST: Checking if application loads");
		assertThat(app).isNotNull();
	}
	
	@Test
	public void shouldExpectTokenToHaveThreeParts() throws Exception {
		
		String[] parts = token.split(".");
		assertThat(parts.length == 3);
		
	}
	

	
	@Test
	public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception  {
		logger.info("TEST NOT AUTHENTICATED: " + PATHTOTEST);
		mockMvc.perform(MockMvcRequestBuilders.get(PATHTOTEST)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status()
				.is4xxClientError());
	}
	
	@Test
	public void shouldAllowAccessToAuthenticatedUsersToAddAircraft() throws Exception {
		String addAirline = new JSONObject().put("registrationNumber", "ABC123").toString();
		String JWTToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJiMDhmODZhZi0zNWRhLTQ4ZjItOGZhYi1jZWYzOTA0NjYwYmQifQ.-xN_h82PHVTCMA9vdoHrcZxH-x5mb11y1537t3rGzcM";
		mockMvc.perform(post(PATHTOTEST)
				.header("Authorization",  "Bearer " + JWTToken )
				.contentType(MediaType.APPLICATION_JSON)
				.content(addAirline)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
		
	}
	
	@Test
	public void shouldAllowAccessToAuthenticatedUsersToGetAircraft() throws Exception {
		String uuid = "gete535twew42yo9484834ywt5q3g2qqa6";
		mockMvc.perform(get(PATHTOTEST)
				.param("uuid", uuid )
				.header("Authorization",  "Bearer " + token)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
	}
		

}
