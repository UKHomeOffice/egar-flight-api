package uk.gov.digital.ho.egar.aircraft.api.entity;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.UUID;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.gov.digital.ho.egar.aircraft.api.rest.AircraftController;
import uk.gov.digital.ho.egar.aircraft.shared.datamodel.AircraftTypeImpl;
import uk.gov.digital.ho.egar.aircraft.shared.datamodel.PersonImpl;
import uk.gov.digital.ho.egar.services.v1.flight.Aircraft;
import uk.gov.digital.ho.egar.services.v1.people.Person;
import uk.gov.digital.ho.egar.services.v1.referencedata.AircraftType;

@Data
@EqualsAndHashCode(callSuper=false)
public class AircraftWebEntity extends ResourceSupport implements Aircraft {
	UUID uuid;
	@JsonDeserialize(as= AircraftTypeImpl.class)
	AircraftType aircraftType;
	String registrationNumber;
	String usualBase;
	boolean euTaxed;
	@JsonDeserialize(as= PersonImpl.class)
	Person responsiblePerson;
	String crewContactInUK;
	
	protected void addLinkToSelf() {
		this.add(linkTo(AircraftController.class).slash(this.getUuid()).withSelfRel());
	}
	
	public static AircraftWebEntity initWebResponseFromInterface(Aircraft interfaceObj) {
		AircraftWebEntity webResponse = new AircraftWebEntity();
		webResponse.aircraftType = interfaceObj.getAircraftType() == null ? new AircraftTypeImpl() : interfaceObj.getAircraftType();
		webResponse.crewContactInUK = interfaceObj.getCrewContactInUK();
		webResponse.euTaxed = interfaceObj.isEuTaxed();
		webResponse.registrationNumber = interfaceObj.getRegistrationNumber();
		//webResponse.responsiblePerson = interfaceObj.getResponsiblePerson() == null ? new PersonImpl():  interfaceObj.getResponsiblePerson()  ;
		webResponse.usualBase = interfaceObj.getUsualBase();
		webResponse.uuid = interfaceObj.getUuid();
		if (webResponse.uuid != null) {
			webResponse.addLinkToSelf();
		}
		return webResponse;
	}
}
