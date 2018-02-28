package uk.gov.digital.ho.egar.aircraft.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.gov.digital.ho.egar.aircraft.model.AircraftWithUuid;
import uk.gov.digital.ho.egar.aircraft.services.repository.model.AircraftPersistentRecord;

import javax.transaction.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * A Jpa repository for Aircraft persisted records
 */
@Transactional
public interface AircraftPersistentRecordRepository extends JpaRepository<AircraftPersistentRecord, UUID> {

	/**
	 * Finds a single aircraft by its id and user id
	 * @param aircraftUuid The aircraft uuid.
	 * @param userUuid The user uuid.
	 * @return The persisted aircraft.
	 */
	AircraftPersistentRecord findOneByAircraftUuidAndUserUuid(final UUID aircraftUuid, final UUID userUuid);

	
	/**
	 * Finds all aircrafts in list that match with the user uuid
	 * @param uuidOfUser
	 * @param aircraftUuids
	 * @return the persisted list of aircrafts
	 */
	List<AircraftPersistentRecord> findAllByUserUuidAndAircraftUuidIn(final UUID uuidOfUser, final List<UUID> aircraftUuids);
}
