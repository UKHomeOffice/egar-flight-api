package uk.gov.digital.ho.egar.flight.shared.datamodel.converters;

import javax.persistence.AttributeConverter;

import com.google.gson.Gson;

import uk.gov.digital.ho.egar.flight.shared.datamodel.GeographicLocationImpl;

public class GeographicLocationConverter implements AttributeConverter<GeographicLocationImpl, String>{
	public static Gson gsonConverter;
	{
		gsonConverter = new Gson();
	}

	@Override
	public String convertToDatabaseColumn(GeographicLocationImpl arg0) {
		if (arg0 != null) {
			return gsonConverter.toJson(arg0);
		}
		return null;
	}

	@Override
	public GeographicLocationImpl convertToEntityAttribute(String arg0) {
		if (arg0 != null) {
			return gsonConverter.fromJson(arg0, GeographicLocationImpl.class);
		}
		return null;
	}

}
