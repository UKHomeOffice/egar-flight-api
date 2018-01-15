package uk.gov.digital.ho.egar.flight.shared.datamodel;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import uk.gov.digital.ho.egar.aircraft.api.entity.AircraftWebEntity;
import uk.gov.digital.ho.egar.flight.shared.datamodel.converters.AircraftConverter;
import uk.gov.digital.ho.egar.flight.shared.datamodel.converters.WhereWhenConverter;
import uk.gov.digital.ho.egar.services.v1.flight.Aircraft;
import uk.gov.digital.ho.egar.services.v1.flight.Flight;
import uk.gov.digital.ho.egar.services.v1.flight.ReasonForFlight;
import uk.gov.digital.ho.egar.services.v1.flight.WhereWhen;

@Component("flight")
@Data
@MappedSuperclass
public abstract class FlightDetails implements Flight{
	
	@Id
	@Column
	UUID uuid;
	
	@Column
	@Convert(converter=WhereWhenConverter.class)
	@JsonDeserialize(as=WhereWhenImpl.class)
	transient WhereWhen from;
	@Column
	@Convert(converter=WhereWhenConverter.class)
	@JsonDeserialize(as=WhereWhenImpl.class)
	transient WhereWhen to;
	
	@Column
	@Convert(converter=AircraftConverter.class)
	@JsonDeserialize(as=AircraftWebEntity.class)
	Aircraft aircraft;
	
	@Column
	boolean aircraftInFreeCirculation;
	
	@Column
	boolean ukVatPaid;
	
	@Column
	boolean withinCTA;
	
	@Column
	@Enumerated(EnumType.STRING)
	ReasonForFlight reason;
	
	@Column
	boolean hazardousGoodsOnBoard;
}
