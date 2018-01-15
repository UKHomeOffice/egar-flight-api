package uk.gov.digital.ho.egar.flight.services;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import uk.gov.digital.ho.egar.flight.api.requests.FlightSearchCritera;
import uk.gov.digital.ho.egar.services.v1.flight.Flight;

public interface FlightServices{
	
	public Flight addFlight(Flight request, UUID bearerToken) 
			throws AccessDeniedException, PersistenceException;
	public Flight getFlight(UUID flightUuid, UUID bearerToken) 
			throws AccessDeniedException, EntityNotFoundException;
	public Flight updateFlight(Flight request, UUID bearerToken, UUID uuid) 
			throws AccessDeniedException, EntityNotFoundException, PersistenceException;
	
	public List<Flight> searchForFlight(FlightSearchCritera searchParam,  UUID bearerToken)
			throws  AccessDeniedException;
}
