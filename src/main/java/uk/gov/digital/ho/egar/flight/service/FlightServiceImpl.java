package uk.gov.digital.ho.egar.flight.service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.gov.digital.ho.egar.flight.api.requests.FlightSearchCritera;
import uk.gov.digital.ho.egar.flight.service.repository.FlightRepository;
import uk.gov.digital.ho.egar.flight.service.repository.datamodel.FlightDetailsDBEntity;
import uk.gov.digital.ho.egar.flight.services.FlightServices;
import uk.gov.digital.ho.egar.services.v1.flight.Flight;

@Service
public class FlightServiceImpl implements FlightServices{

	@Autowired
	FlightRepository repository;
	
	public Flight addFlight(Flight request, UUID bearerToken)
			throws AccessDeniedException, PersistenceException {
		FlightDetailsDBEntity dbEntity = FlightDetailsDBEntity.initDBEntityForInterface(request);
		if (dbEntity.getUuid() == null) {
			dbEntity.setUuid(UUID.randomUUID());
		}
		repository.save(dbEntity);
		return dbEntity;
	}

	@Override
	public Flight getFlight(UUID flightUuid, UUID bearerToken) 
			throws AccessDeniedException, EntityNotFoundException {
		List<FlightDetailsDBEntity> repositoryResults = 
				repository.findByUuidAndUuidOfSourceUser(flightUuid, bearerToken);
		return repositoryResults!= null && repositoryResults.size() > 0? 
				repositoryResults.get(0):
					null;
	}

	@Override
	public Flight updateFlight(Flight request, UUID bearerToken, UUID uuid)
			throws AccessDeniedException, EntityNotFoundException, PersistenceException {
		FlightDetailsDBEntity dbEntity = null;
		if (request.getUuid() != uuid) {
			throw new PersistenceException("uuid of entity and path does not match");
		}
		Flight temp = getFlight(uuid, bearerToken);
		if (temp!= null) {
			dbEntity = FlightDetailsDBEntity.initDBEntityForInterface(request);
			repository.save(dbEntity);
		}
		else {
			throw new PersistenceException("Error updating flight");
		}
		return dbEntity;
	}

	@Override
	public List<Flight> searchForFlight(FlightSearchCritera searchParam, UUID bearerToken)
			throws AccessDeniedException {
		// TODO Auto-generated method stub
		return null;
	}
}
