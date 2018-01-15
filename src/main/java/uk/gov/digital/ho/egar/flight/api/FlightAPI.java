package uk.gov.digital.ho.egar.flight.api;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import uk.gov.digital.ho.egar.constants.ServicePathConstants;
import uk.gov.digital.ho.egar.flight.api.entity.FlightWebOperationsEntity;
import uk.gov.digital.ho.egar.flight.api.requests.FlightSearchCritera;
import uk.gov.digital.ho.egar.services.v1.flight.Flight;

public interface FlightAPI {
	public static final String ROOT_SERVICE_NAME = "flight";
	public static final String ROOT_PATH = 
			ServicePathConstants.ROOT_PATH_SEPERATOR + 
			ServicePathConstants.ROOT_SERVICE_API + 
			ServicePathConstants.ROOT_PATH_SEPERATOR + 
			ServicePathConstants.SERVICE_VERSION_ONE +
			ServicePathConstants.ROOT_PATH_SEPERATOR +
			ROOT_SERVICE_NAME;
	
	
	
	public static final String PATH_FLIGHT = "/";
	public static final String PATH_FLIGHT_ID = "/{id}";
	
	public Flight addFlight(FlightWebOperationsEntity request) 
			throws AccessDeniedException, PersistenceException;
	public Flight getFlight(UUID flightUuid) 
			throws AccessDeniedException, EntityNotFoundException;
	public Flight updateFlight(FlightWebOperationsEntity request, UUID uuid) 
			throws AccessDeniedException, EntityNotFoundException, PersistenceException;
	
	public List<Flight> searchForFlight(FlightSearchCritera searchParam)
			throws  AccessDeniedException;
}
