package uk.gov.digital.ho.egar.flight.service.repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uk.gov.digital.ho.egar.flight.service.repository.datamodel.FlightDetailsDBEntity;


@Transactional
@Repository
public interface FlightRepository extends JpaRepository<FlightDetailsDBEntity, UUID>{
	List<FlightDetailsDBEntity> findByUuidAndUuidOfSourceUser(UUID uuid, UUID uuidOfSourceUser);
}
