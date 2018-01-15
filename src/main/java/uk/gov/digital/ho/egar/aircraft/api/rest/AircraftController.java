package uk.gov.digital.ho.egar.aircraft.api.rest;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import uk.gov.digital.ho.egar.aircraft.AircraftAPI;
import uk.gov.digital.ho.egar.aircraft.api.entity.AircraftWebEntity;
import uk.gov.digital.ho.egar.aircraft.api.requests.AircraftSearchCriteria;
import uk.gov.digital.ho.egar.aircraft.services.AircraftServices;
import uk.gov.digital.ho.egar.aircraft.shared.responses.AircraftServiceResponse;
import uk.gov.digital.ho.egar.services.v1.flight.Aircraft;
import uk.gov.digital.ho.egar.shared.auth.api.token.UserWithUUID;

@RestController
@RequestMapping(AircraftAPI.ROOT_PATH)
@Api(value = AircraftAPI.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class AircraftController implements AircraftAPI {

	@Autowired
	AircraftServices services;
	
	/* (non-Javadoc)
	 * @see uk.gov.digital.ho.egar.aircraft.api.rest.AircradtAPI#addAircraftRecord(uk.gov.digital.ho.egar.aircraft.api.entity.AircraftWebEntity)
	 */
	@ApiOperation(value = "Add a new aircraft record.", 
			notes = "Add a new aircraft record to the GAR database",
			response = AircraftWebEntity.class)
	@ApiResponses(value = { 
			@ApiResponse(
					code = 201,
					message = AircraftServiceResponse.SUCCESSFUL_KEY,
					response = AircraftWebEntity.class),
			@ApiResponse(
					code = 400,
					message = AircraftServiceResponse.DUPLICATE_KEY,
					response = PersistenceException.class),
			@ApiResponse(
					code = 403,
					message = AircraftServiceResponse.UNAUTHORISED,
					response = AccessDeniedException.class)})
	@PostMapping(value = AircraftAPI.PATH_ADD_AIRCRAFT,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces=MediaType.APPLICATION_JSON_VALUE)
	public Aircraft addAircraftRecord(
			@RequestBody AircraftWebEntity aircraft)
					throws AccessDeniedException, PersistenceException{
		Aircraft serviceResponse = null;
		UUID sourceUserUUID =  UserWithUUID.getUUIDOfUser();
		serviceResponse  = services.addAircraftRecord(aircraft, sourceUserUUID);
		return AircraftWebEntity.initWebResponseFromInterface(serviceResponse);
	}


	/* (non-Javadoc)
	 * @see uk.gov.digital.ho.egar.aircraft.api.rest.AircradtAPI#getAircraftRecord(java.util.UUID)
	 */
	@ApiOperation(value = "Get an aircraft.", 
			notes = "Get the current state for an aircraft record",
			response = AircraftWebEntity.class)
	@ApiResponses(value = { 
			@ApiResponse(
					code = 200,
					message = AircraftServiceResponse.SUCCESSFUL_KEY,
					response = AircraftWebEntity.class),
			@ApiResponse(
					code = 404,
					message = AircraftServiceResponse.ENTITY_NOT_FOUND,
					response = EntityNotFoundException.class),
			@ApiResponse(
					code = 403,
					message = AircraftServiceResponse.UNAUTHORISED,
					response = AccessDeniedException.class)})
	@GetMapping(value = AircraftAPI.PATH_AIRCRAFT_ID,
	produces=MediaType.APPLICATION_JSON_VALUE)
	public Aircraft getAircraftRecord(
			@PathVariable("id") UUID id) throws AccessDeniedException {
		Aircraft serviceResponse = null;
		UUID sourceUserUUID =  UserWithUUID.getUUIDOfUser();
		serviceResponse  = services.getAircraftRecord(id, sourceUserUUID);
		return AircraftWebEntity.initWebResponseFromInterface(serviceResponse);
	}
	
	/* (non-Javadoc)
	 * @see uk.gov.digital.ho.egar.aircraft.api.rest.AircradtAPI#updateAircraftRecord(uk.gov.digital.ho.egar.aircraft.api.entity.AircraftWebEntity, java.util.UUID)
	 */
	@ApiOperation(value = "Update an aircraft record.", 
			notes = "amend an aircraft record in the GAR database",
			response = AircraftWebEntity.class)
	@ApiResponses(value = { 
			@ApiResponse(
					code = 202,
					message = AircraftServiceResponse.SUCCESSFUL_KEY,
					response = AircraftWebEntity.class),
			@ApiResponse(
					code = 400,
					message = AircraftServiceResponse.FAILED_KEY,
					response = PersistenceException.class),
			@ApiResponse(
					code = 403,
					message = AircraftServiceResponse.UNAUTHORISED,
					response = AccessDeniedException.class)})
	@PostMapping(value = AircraftAPI.PATH_AIRCRAFT_ID,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces=MediaType.APPLICATION_JSON_VALUE)
	public Aircraft updateAircraftRecord(
			@RequestBody AircraftWebEntity aircraft,
			@PathVariable("id") UUID id) throws AccessDeniedException {
		Aircraft serviceResponse = null;
		UUID sourceUserUUID =  UserWithUUID.getUUIDOfUser();		
		serviceResponse  = services.updateAircraftRecord(aircraft, id, sourceUserUUID);
		return AircraftWebEntity.initWebResponseFromInterface(serviceResponse);
	}
	
	/* (non-Javadoc)
	 * @see uk.gov.digital.ho.egar.aircraft.api.rest.AircradtAPI#searchForAircraft(uk.gov.digital.ho.egar.aircraft.api.requests.AircraftSearchCriteria)
	 */
	@ApiOperation(value = "Search for an aircraft.", 
			notes = "Search for an aircraft record",
			response = AircraftWebEntity.class,
			responseContainer="List")
	@ApiResponses(value = { 
			@ApiResponse(
					code = 200,
					message = AircraftServiceResponse.SUCCESSFUL_KEY,
					response = AircraftWebEntity.class,
					responseContainer="List"),
			@ApiResponse(
					code = 403,
					message = AircraftServiceResponse.UNAUTHORISED,
					response = AccessDeniedException.class)})
	@PostMapping(value = AircraftAPI.PATH_AIRCRAFT_SEARCH,
	produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Aircraft> searchForAircraft(
			@RequestBody AircraftSearchCriteria searchCriteria) throws AccessDeniedException {
		List<Aircraft> serviceResponse = null;
		UUID sourceUserUUID =  UserWithUUID.getUUIDOfUser();
		serviceResponse = services.searchForAircraft(searchCriteria, sourceUserUUID);
		serviceResponse = serviceResponse.stream().map((temp)->AircraftWebEntity.initWebResponseFromInterface(temp)).collect(Collectors.toList());
		return serviceResponse;
	}

	/* (non-Javadoc)
	 * @see uk.gov.digital.ho.egar.aircraft.api.rest.AircradtAPI#deleteAircraftRecord(uk.gov.digital.ho.egar.aircraft.api.entity.AircraftWebEntity, java.util.UUID)
	 */
	public Aircraft deleteAircraftRecord(
			@RequestBody AircraftWebEntity aircraft, 
			@PathVariable("id") UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

}
