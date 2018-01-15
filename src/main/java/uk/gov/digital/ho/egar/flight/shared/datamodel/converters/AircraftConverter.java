package uk.gov.digital.ho.egar.flight.shared.datamodel.converters;

import java.util.UUID;

import javax.persistence.AttributeConverter;

import uk.gov.digital.ho.egar.aircraft.api.entity.AircraftWebEntity;
import uk.gov.digital.ho.egar.services.v1.flight.Aircraft;

public class AircraftConverter implements AttributeConverter<Aircraft, String>{

	@Override
	public String convertToDatabaseColumn(Aircraft arg0) {
		if(arg0!= null && arg0.getUuid()!= null) {
			return arg0.getUuid().toString();
		}
		return null;
	}

	@Override
	public Aircraft convertToEntityAttribute(String arg0) {
		if (arg0!= null) {
			AircraftWebEntity temp = new AircraftWebEntity();
			temp.setUuid(UUID.fromString(arg0));
		}
		return null;
	}

}
