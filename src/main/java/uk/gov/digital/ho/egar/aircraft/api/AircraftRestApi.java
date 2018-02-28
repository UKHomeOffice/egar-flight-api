package uk.gov.digital.ho.egar.aircraft.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import uk.gov.digital.ho.egar.aircraft.api.exceptions.AircraftApiException;
import uk.gov.digital.ho.egar.aircraft.model.Aircraft;
import uk.gov.digital.ho.egar.aircraft.model.AircraftWithUuid;

import java.util.List;
import java.util.UUID;

/**
 * The aircraft API which allows for CRUD operations for all aircraft details.
 */
public interface AircraftRestApi {

    /**
     * Add an aircraft for a user.
     * @param userUuid The user uuid
     * @param aircraft The aircraft
     * @return A response entity
     * @throws AircraftApiException Is thrown when the operation could not be carried out.
     */
    ResponseEntity<Void> addAircraftRecord(final UUID userUuid, final Aircraft aircraft, Errors errors) throws AircraftApiException;

    /**
     * Retrieves the aircraft for a user.
     * @param userUuid The user uuid.
     * @param aircraftUuid The aircraft uuid.
     * @return The aircraft.
     * @throws AircraftApiException Is thrown when the operation could not be carried out.
     */
    AircraftWithUuid getAircraftRecord(
             final UUID userUuid,
             final UUID aircraftUuid) throws AircraftApiException;


    /**
     * Updates an existing aircraft for a user.
     * @param userUuid The user uuid
     * @param aircraft The aircraft with updated values
     * @param aircraftUuid The aircraft uuid.
     * @return A response entity
     * @throws AircraftApiException Is thrown when the operation could not be carried out.
     */
    ResponseEntity<Void> updateAircraftRecord(
             final UUID userUuid,
             final UUID aircraftUuid,
             final Aircraft aircraft,
             final Errors errors) throws AircraftApiException;

    /**
     * Retrieves a list of aircraft retails
     * @param uuidOfUser
     * @param aircraftUuids list of aircraft uuids
     * @return a list of aircraft details
     */
	AircraftWithUuid[] bulkRetrieveAircrafts(final UUID uuidOfUser, 
											 final List<UUID> aircraftUuids);
}
