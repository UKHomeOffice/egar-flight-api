package uk.gov.digital.ho.egar.flight.service.repository.datamodel;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.gov.digital.ho.egar.datamodel.PersistedRecord;
import uk.gov.digital.ho.egar.datamodel.PurgePeriod;
import uk.gov.digital.ho.egar.flight.shared.datamodel.FlightDetails;
import uk.gov.digital.ho.egar.services.v1.flight.Flight;

@Component
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "FLIGHT")
public class FlightDetailsDBEntity extends FlightDetails implements PersistedRecord{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column
	UUID uuidOfSourceUser;
	@Column
	LocalDateTime purgeDate;
	@Column
	PurgePeriod purgePeriod;
	
	public static FlightDetailsDBEntity initDBEntityForInterface(Flight flight) {
		FlightDetailsDBEntity returnVal = new FlightDetailsDBEntity();
		returnVal.setUuid(flight.getUuid());
		returnVal.setFrom(flight.getFrom());
		returnVal.setTo(flight.getTo());
		returnVal.setAircraft(flight.getAircraft());
		returnVal.setAircraftInFreeCirculation( flight.isAircraftInFreeCirculation());
		returnVal.setUkVatPaid (flight.isUkVatPaid());
		returnVal.setReason(flight.getReason());
		returnVal.setHazardousGoodsOnBoard(flight.isHazardousGoodsOnBoard());
		returnVal.setWithinCTA(flight.isWithinCTA());
		return returnVal;
	}
}
