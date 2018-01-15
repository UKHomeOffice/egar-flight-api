package uk.gov.digital.ho.egar.flight.shared.datamodel.converters;

import javax.persistence.AttributeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import uk.gov.digital.ho.egar.flight.shared.datamodel.GeographicLocationImpl;
import uk.gov.digital.ho.egar.flight.shared.datamodel.WhereWhenImpl;
import uk.gov.digital.ho.egar.services.v1.flight.GeographicLocation;
import uk.gov.digital.ho.egar.services.v1.flight.WhereWhen;

public class WhereWhenConverter implements AttributeConverter<WhereWhen, String>{

	protected JsonDeserializer<GeographicLocation>  geographicInsta = 
			(json, typeOfT,context) -> {
				Gson gson = new Gson();
				return json != null? 
						gson.fromJson(json, GeographicLocationImpl.class):
							null;

			};


	@Override
	public String convertToDatabaseColumn(WhereWhen arg0) {
		if (arg0 == null) {
			return null;
		}
		Gson  gsonConverter = new Gson();
		return gsonConverter.toJson(arg0);
	}

	@Override
	public WhereWhen convertToEntityAttribute(String arg0) {
		if (arg0 == null) {
			return null;
		}
		try {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(GeographicLocation.class, geographicInsta);
		Gson  gsonConverter = builder.create();
		return gsonConverter.fromJson(arg0, WhereWhenImpl.class);
		} catch(Exception e) {
			return null;
		}
	}


}
