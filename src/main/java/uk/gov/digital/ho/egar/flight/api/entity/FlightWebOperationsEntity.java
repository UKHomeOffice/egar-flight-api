package uk.gov.digital.ho.egar.flight.api.entity;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.UUID;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.gov.digital.ho.egar.aircraft.api.entity.AircraftWebEntity;
import uk.gov.digital.ho.egar.flight.api.rest.FlightController;
import uk.gov.digital.ho.egar.flight.shared.datamodel.WhereWhenImpl;
import uk.gov.digital.ho.egar.services.v1.flight.Aircraft;
import uk.gov.digital.ho.egar.services.v1.flight.Flight;
import uk.gov.digital.ho.egar.services.v1.flight.ReasonForFlight;
import uk.gov.digital.ho.egar.services.v1.flight.WhereWhen;

@Data
@EqualsAndHashCode(callSuper=false)
public class FlightWebOperationsEntity extends ResourceSupport implements Flight {
	UUID uuid;
	@JsonDeserialize(as=WhereWhenImpl.class)
	WhereWhen from = new WhereWhenImpl();
	@JsonDeserialize(as=WhereWhenImpl.class)
	WhereWhen to;
	@JsonDeserialize(as=AircraftWebEntity.class)
	Aircraft aircraft;
	boolean aircraftInFreeCirculation;
	boolean withinCTA;
	boolean ukVatPaid;
	ReasonForFlight reason;
	boolean hazardousGoodsOnBoard;
	
	
	protected void addLinkToSelf() {
		this.add(linkTo(FlightController.class).slash(this.getUuid()).withSelfRel());
	}
	
	public static FlightWebOperationsEntity initResponseEntityForFlight(Flight flight) {
		FlightWebOperationsEntity returnVal = new FlightWebOperationsEntity();
		returnVal.uuid = flight.getUuid();
		//returnVal.from = flight.getFrom();
		//returnVal.to = flight.getTo();
		returnVal.aircraftInFreeCirculation = flight.isAircraftInFreeCirculation();
		returnVal.ukVatPaid = flight.isUkVatPaid();
		returnVal.reason = flight.getReason();
		returnVal.hazardousGoodsOnBoard = flight.isHazardousGoodsOnBoard();
		returnVal.withinCTA = flight.isWithinCTA();
		if (returnVal.getUuid() != null) {
			returnVal.addLinkToSelf();
		}
		return returnVal;
	}
}