package uk.gov.digital.ho.egar.aircraft.shared.datamodel.converters;

import javax.persistence.AttributeConverter;

import com.google.gson.Gson;

import uk.gov.digital.ho.egar.aircraft.shared.datamodel.AircraftTypeImpl;
import uk.gov.digital.ho.egar.services.v1.referencedata.AircraftType;

public class AircraftTypeConverter implements AttributeConverter<AircraftType, String>{
	
	static Gson gsonConverter = null;
	{
		gsonConverter = new Gson();
	}
	
	@Override
	public String convertToDatabaseColumn(AircraftType arg0) {
		String returnVal = gsonConverter.toJson(arg0);
		return returnVal;
	}

	@Override
	public AircraftType convertToEntityAttribute(String arg0) {
		AircraftTypeImpl returnVal = gsonConverter.fromJson(arg0, AircraftTypeImpl.class);
		return returnVal;
	}

}
