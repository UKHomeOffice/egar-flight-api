package uk.gov.digital.ho.egar.aircraft.services;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import uk.gov.digital.ho.egar.aircraft.api.requests.AircraftSearchCriteria;
import uk.gov.digital.ho.egar.services.v1.flight.Aircraft;

public interface AircraftServices {
	Aircraft addAircraftRecord(Aircraft aircraft, UUID bearerToken) 
			throws AccessDeniedException, PersistenceException;
	Aircraft getAircraftRecord(UUID id,UUID bearerToken) 
			throws AccessDeniedException, EntityNotFoundException;
	Aircraft updateAircraftRecord(Aircraft aircraft,UUID id, UUID bearerToken) 
			throws AccessDeniedException, EntityNotFoundException, PersistenceException;
	Aircraft deleteAircraftRecord(Aircraft aircraft,UUID id, UUID bearerToken);
	
	List<Aircraft> searchForAircraft(AircraftSearchCriteria searchCriteria, UUID bearerToken) 
			throws AccessDeniedException;
}
