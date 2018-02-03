package uk.gov.digital.ho.egar.aircraft.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.digital.ho.egar.aircraft.api.exceptions.AircraftApiException;
import uk.gov.digital.ho.egar.aircraft.api.exceptions.AircraftNotFoundAircraftApiException;
import uk.gov.digital.ho.egar.aircraft.api.exceptions.BadOperationAircraftApiException;
import uk.gov.digital.ho.egar.aircraft.api.exceptions.ConstraintViolationAircraftApiException;
import uk.gov.digital.ho.egar.aircraft.model.Aircraft;
import uk.gov.digital.ho.egar.aircraft.model.AircraftWithUuid;
import uk.gov.digital.ho.egar.aircraft.services.AircraftService;
import uk.gov.digital.ho.egar.aircraft.services.repository.AircraftPersistentRecordRepository;
import uk.gov.digital.ho.egar.aircraft.services.repository.model.AircraftPersistentRecord;

import javax.validation.ConstraintViolationException;
import java.util.UUID;

/**
 * A database implementation of the aircraft service.
 */
@Service
public class AircraftServiceDatabaseImpl implements AircraftService {

    /**
     * The database repository.
     */
	private AircraftPersistentRecordRepository repository;

    /**
     * Constructor taking a repository.
     * @param repository The repository.
     */
	public AircraftServiceDatabaseImpl(@Autowired final AircraftPersistentRecordRepository repository) {
		this.repository = repository;
	}

	@Override
	public AircraftWithUuid addAircraftRecord(final Aircraft aircraft, final UUID userUuid) throws AircraftApiException {

		AircraftPersistentRecord peristentAircraft ;

		if ( aircraft instanceof AircraftPersistentRecord )
		{
			peristentAircraft = (AircraftPersistentRecord) aircraft ;
			if ( peristentAircraft.getAircraftUuid() != null )
			{
				throw new BadOperationAircraftApiException("Aircraft passed in already had a UUID");
			}
			if ( peristentAircraft.getUserUuid() != null )
			{
				throw new BadOperationAircraftApiException("Aircraft passed in already had a user UUID");
			}
		}
		else
		{
			peristentAircraft = new AircraftPersistentRecord().copy(aircraft);
		}

		peristentAircraft.setUserUuid(userUuid);
		peristentAircraft.setAircraftUuid(UUID.randomUUID()); // NEW ID

		return saveAndFlush(peristentAircraft);
	}

	@Override
	public AircraftPersistentRecord getAircraftRecord(final UUID aircraftUuid, final UUID userUuid) throws AircraftNotFoundAircraftApiException {
        AircraftPersistentRecord record = this.repository.findOneByAircraftUuidAndUserUuid(aircraftUuid, userUuid);

        if ( record == null )
        {
            throw new AircraftNotFoundAircraftApiException(aircraftUuid,userUuid);
        }

        return record ;	}

	@Override
	public AircraftWithUuid updateAircraftRecord(final Aircraft aircraft, final UUID aircraftUuid, final UUID userUuid) throws AircraftApiException {

        AircraftPersistentRecord record = this.getAircraftRecord(aircraftUuid, userUuid);

        record.copy(aircraft);

        return saveAndFlush(record);


    }

	/**
	 * Simply does the exception catching on save.
	 */
	private AircraftWithUuid saveAndFlush(AircraftPersistentRecord aircraftPersistentRecord) throws AircraftApiException {
		try {
			return this.repository.saveAndFlush(aircraftPersistentRecord);
		}
		catch (ConstraintViolationException ex)
		{
			// Failed
			throw new ConstraintViolationAircraftApiException(ex);
		}
	}
}
