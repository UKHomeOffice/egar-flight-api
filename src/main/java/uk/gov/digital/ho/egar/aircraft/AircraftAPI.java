package uk.gov.digital.ho.egar.aircraft;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

import javax.persistence.PersistenceException;

import uk.gov.digital.ho.egar.aircraft.api.entity.AircraftWebEntity;
import uk.gov.digital.ho.egar.aircraft.api.requests.AircraftSearchCriteria;
import uk.gov.digital.ho.egar.constants.ServicePathConstants;
import uk.gov.digital.ho.egar.services.v1.flight.Aircraft;

public interface AircraftAPI {
	public static final String ROOT_SERVICE_NAME = "aircraft";
	public static final String ROOT_PATH = 
			ServicePathConstants.ROOT_PATH_SEPERATOR + 
			ServicePathConstants.ROOT_SERVICE_API + 
			ServicePathConstants.ROOT_PATH_SEPERATOR +
			ServicePathConstants.SERVICE_VERSION_ONE +
			ServicePathConstants.ROOT_PATH_SEPERATOR +
			ROOT_SERVICE_NAME;
	
	public static final String PATH_ADD_AIRCRAFT = "/";
	public static final String PATH_AIRCRAFT_ID = "/{id}";
	public static final String PATH_AIRCRAFT_SEARCH = "/query";
	
	Aircraft addAircraftRecord(AircraftWebEntity aircraft) throws AccessDeniedException, PersistenceException;

	Aircraft getAircraftRecord(UUID id) throws AccessDeniedException;

	Aircraft updateAircraftRecord(AircraftWebEntity aircraft, UUID id) throws AccessDeniedException;

	List<Aircraft> searchForAircraft(AircraftSearchCriteria searchCriteria) throws AccessDeniedException;

	Aircraft deleteAircraftRecord(AircraftWebEntity aircraft, UUID id);
}
