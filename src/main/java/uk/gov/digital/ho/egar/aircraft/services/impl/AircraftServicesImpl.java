package uk.gov.digital.ho.egar.aircraft.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.gov.digital.ho.egar.aircraft.api.requests.AircraftSearchCriteria;
import uk.gov.digital.ho.egar.aircraft.services.AircraftServices;
import uk.gov.digital.ho.egar.aircraft.services.repository.AircraftRepository;
import uk.gov.digital.ho.egar.aircraft.services.repository.objects.AircraftDetailsDBEntity;
import uk.gov.digital.ho.egar.services.v1.flight.Aircraft;

@Service
public class AircraftServicesImpl implements AircraftServices{

	@Autowired
	AircraftRepository repository;


	@Override
	public Aircraft addAircraftRecord(
			Aircraft aircraft,
			UUID bearerToken) {
		AircraftDetailsDBEntity dbEntity = new AircraftDetailsDBEntity();
		AircraftDetailsDBEntity.initDBEntityForInterface(dbEntity, aircraft);
		dbEntity.setUuidOfSourceUser(bearerToken);
		try {
			if(dbEntity.getUuid() == null) {
				dbEntity.setUuid(UUID.randomUUID());
			}
			repository.save(dbEntity);
		}catch(Exception e) {
			throw new PersistenceException("Error adding new aircraft");
		}
		return dbEntity;
	}

	@Override
	public Aircraft getAircraftRecord(
			UUID id,
			UUID bearerToken) {
		AircraftDetailsDBEntity repositoryResult = 
				repository.findByUuidAndUuidOfSourceUser(id, bearerToken).get(0);
		return repositoryResult;
	}

	@Override
	public Aircraft updateAircraftRecord(
			Aircraft aircraft,
			UUID id, 
			UUID bearerToken){
		Aircraft temp = getAircraftRecord(id,bearerToken);
		AircraftDetailsDBEntity dbEntity;
		if (temp != null) {
			dbEntity = new AircraftDetailsDBEntity();
			AircraftDetailsDBEntity.initDBEntityForInterface(dbEntity, aircraft);
			dbEntity.setUuidOfSourceUser(bearerToken);
			dbEntity.setUuid(id);
			repository.save(dbEntity);
		}
		else {
			throw new PersistenceException("unable to update aircraft");
		}
		return dbEntity;
	}

	@Override
	public List<Aircraft> searchForAircraft(
			AircraftSearchCriteria searchCriteria,
			UUID bearerToken) {
		List<Aircraft> returnList = null;
		try {
			Iterator<AircraftDetailsDBEntity> repositoryResult = 
					repository.findByUuidOfSourceUserAndUsualBaseLike(
							bearerToken,
							"%"+searchCriteria.getFullOrPartialUsualBase()+"%").iterator();
			returnList = new ArrayList<Aircraft>();
			repositoryResult.forEachRemaining(returnList::add);
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
		}
		return returnList;
	}

	@Override
	public Aircraft deleteAircraftRecord(Aircraft aircraft, UUID id, UUID bearerToken) {
		// TODO Auto-generated method stub
		return null;
	}
}
