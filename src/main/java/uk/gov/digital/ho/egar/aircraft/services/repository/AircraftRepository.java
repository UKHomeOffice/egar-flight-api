package uk.gov.digital.ho.egar.aircraft.services.repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import uk.gov.digital.ho.egar.aircraft.services.repository.objects.AircraftDetailsDBEntity;

@Transactional
public interface AircraftRepository extends CrudRepository<AircraftDetailsDBEntity, UUID> {
	List<AircraftDetailsDBEntity> findByUuidAndUuidOfSourceUser(UUID uuid, UUID uuidOfSourceUser);
	List<AircraftDetailsDBEntity> findByUuidOfSourceUserAndUsualBaseLike(UUID uuidOfSourceUser, String usualBase);
}
