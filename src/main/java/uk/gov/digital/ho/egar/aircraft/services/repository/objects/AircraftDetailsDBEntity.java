package uk.gov.digital.ho.egar.aircraft.services.repository.objects;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.gov.digital.ho.egar.aircraft.shared.datamodel.AircraftDetails;
import uk.gov.digital.ho.egar.datamodel.PersistedRecord;
import uk.gov.digital.ho.egar.datamodel.PurgePeriod;
import uk.gov.digital.ho.egar.services.v1.flight.Aircraft;

@Component
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "AIRCRAFT")
public class AircraftDetailsDBEntity extends AircraftDetails implements PersistedRecord{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private LocalDateTime purgeDate;
	@Column
	private PurgePeriod purgePeriod;
	@Column
	@NotNull
	private UUID uuidOfSourceUser;

	public static void initDBEntityForInterface(AircraftDetailsDBEntity dbEntity, Aircraft interfaceObj) {
		try {
			dbEntity.aircraftType = interfaceObj.getAircraftType();
			dbEntity.crewContactInUK = interfaceObj.getCrewContactInUK();
			dbEntity.euTaxed = interfaceObj.isEuTaxed();
			dbEntity.registrationNumber = interfaceObj.getRegistrationNumber();
			dbEntity.responsiblePerson = interfaceObj.getResponsiblePerson();
			dbEntity.usualBase = interfaceObj.getUsualBase();
			dbEntity.uuid = interfaceObj.getUuid();
			
		}catch(Exception e) {
			e.printStackTrace(System.out);
			e.printStackTrace(System.out);
		}
	}
}

