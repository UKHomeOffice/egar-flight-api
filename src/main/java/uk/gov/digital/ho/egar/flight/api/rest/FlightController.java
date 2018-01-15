package uk.gov.digital.ho.egar.flight.api.rest;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import uk.gov.digital.ho.egar.flight.api.FlightAPI;
import uk.gov.digital.ho.egar.flight.api.entity.FlightWebOperationsEntity;
import uk.gov.digital.ho.egar.flight.api.requests.FlightSearchCritera;
import uk.gov.digital.ho.egar.flight.services.FlightServices;
import uk.gov.digital.ho.egar.services.v1.flight.Flight;
import uk.gov.digital.ho.egar.shared.auth.api.token.UserWithUUID;

@RestController
@RequestMapping(FlightAPI.ROOT_PATH)
@Api(value = FlightAPI.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class FlightController implements FlightAPI {
	
	@Autowired
	private FlightServices flightService;
	
	@ApiOperation(value = "Add a new flight.",
			notes = "Add a new flight record to the GAR database",
			response = FlightWebOperationsEntity.class)
	@ApiResponses(value = {
			@ApiResponse(
					code = 201,
					message = "SUCCESSFUL",
					response = FlightWebOperationsEntity.class),
			@ApiResponse(
					code = 403,
					message = "UNAUTHORISED",
					response = AccessDeniedException.class),
			@ApiResponse(
					code = 400,
					message = "BAD_REQUEST",
					response = PersistenceException.class)})
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = FlightAPI.PATH_FLIGHT,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces=MediaType.APPLICATION_JSON_VALUE)
	public Flight addFlight(
			@RequestBody FlightWebOperationsEntity request)  throws AccessDeniedException, PersistenceException{
		UUID sourceUserUUID =  UserWithUUID.getUUIDOfUser();
		Flight serviceResponse =
				flightService.addFlight(request, sourceUserUUID);
		return FlightWebOperationsEntity.initResponseEntityForFlight(serviceResponse); 
	}
	
	@ApiOperation(value = "Retrieve a saved flight",
			notes = "Retrieve a saved flight",
			response = FlightWebOperationsEntity.class)
	@ApiResponses(value = {
			@ApiResponse(
					code = 201,
					message = "SUCCESSFUL",
					response = FlightWebOperationsEntity.class),
			@ApiResponse(
					code = 404,
					message = "NOT_FOUND",
					response = EntityNotFoundException.class),
			})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = FlightAPI.PATH_FLIGHT_ID,
	produces=MediaType.APPLICATION_JSON_VALUE)
	public Flight getFlight(
			@PathVariable(value="id")UUID id)  throws AccessDeniedException{
		UUID sourceUserUUID =  UserWithUUID.getUUIDOfUser();
		Flight serviceResponse = flightService.getFlight(id, sourceUserUUID);
		return FlightWebOperationsEntity.initResponseEntityForFlight(serviceResponse);
	}
	
	@ApiOperation(value = "Update existing flight.",
			notes = "Update existing flight",
			response = FlightWebOperationsEntity.class)
	@ApiResponses(value = {
			@ApiResponse(
					code = 202,
					message = "SUCCESSFUL_KEY",
					response = FlightWebOperationsEntity.class),
			@ApiResponse(
					code = 404,
					message = "NOT_FOUND",
					response = EntityNotFoundException.class),
			@ApiResponse(
					code = 403,
					message = "UNAUTHORISED",
					response = AccessDeniedException.class),
			@ApiResponse(
					code = 400,
					message = "BAD_REQUEST",
					response = PersistenceException.class)})
	@ResponseStatus(HttpStatus.ACCEPTED)
	@PostMapping(value = FlightAPI.PATH_FLIGHT_ID,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces=MediaType.APPLICATION_JSON_VALUE)
	public Flight updateFlight(
			FlightWebOperationsEntity request,
			@PathVariable(value="id")UUID id)  throws AccessDeniedException{
		UUID sourceUserUUID =  UserWithUUID.getUUIDOfUser();
		Flight serviceResponse = flightService.updateFlight(request, sourceUserUUID, id);
		return FlightWebOperationsEntity.initResponseEntityForFlight(serviceResponse);
	}

	public List<Flight> searchForFlight(FlightSearchCritera searchParam)
			throws AccessDeniedException {
		// TODO Auto-generated method stub
		return null;
	}
}
