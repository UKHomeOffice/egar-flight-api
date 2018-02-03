package uk.gov.digital.ho.egar.aircraft.services;

import java.util.UUID;

import uk.gov.digital.ho.egar.aircraft.api.exceptions.AircraftApiException;
import uk.gov.digital.ho.egar.aircraft.api.exceptions.AircraftNotFoundAircraftApiException;
import uk.gov.digital.ho.egar.aircraft.model.Aircraft;
import uk.gov.digital.ho.egar.aircraft.model.AircraftWithUuid;

/**
 * The aircraft service which allows operations on aircraft objects.
 */
public interface AircraftService {

    /**
     * Adds an aircraft record for a user.
     * @param aircraft The aircraft
     * @param userUuid The user uuid
     * @return The created aircraft
     * @throws AircraftApiException Is thrown when the operation can not be carried out.
     */
	AircraftWithUuid addAircraftRecord(final Aircraft aircraft, final UUID userUuid) throws AircraftApiException;

    /**
     * Retrieves and existing aircraft record for a user.
     * @param aircraftUuid The aircraft uuid.
     * @param userUuid The user uuid.
     * @return The aircraft
     * @throws AircraftApiException  Is thrown when the operation can not be carried out.
     */
	AircraftWithUuid getAircraftRecord(final UUID aircraftUuid, final UUID userUuid) throws AircraftApiException;

    /**
     * Updates and existin aircraft for a user.
     * @param aircraft The updated aircraft details
     * @param aircraftUuid The aircraft uuid
     * @param userUuid The user uuid
     * @return The updated aircraft
     * @throws AircraftApiException  Is thrown when the operation can not be carried out.
     */
	AircraftWithUuid updateAircraftRecord(final Aircraft aircraft, final UUID aircraftUuid, final UUID userUuid) throws AircraftApiException;
}
