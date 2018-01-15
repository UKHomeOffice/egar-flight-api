package uk.gov.digital.ho.egar.aircraft.shared.datamodel.converters;

import javax.persistence.AttributeConverter;

import com.google.gson.Gson;

import uk.gov.digital.ho.egar.aircraft.shared.datamodel.PersonImpl;
import uk.gov.digital.ho.egar.services.v1.people.Person;

public class PersonConverter implements AttributeConverter<Person, String>{
	
	static Gson gsonConverter = null;
	{
		gsonConverter = new Gson();
	}
	
	@Override
	public String convertToDatabaseColumn(Person arg0) {
		String returnVal = gsonConverter.toJson(arg0);
		return returnVal;
	}

	@Override
	public PersonImpl convertToEntityAttribute(String arg0) {
		PersonImpl returnVal = gsonConverter.fromJson(arg0, PersonImpl.class);
		return returnVal;
	}

}
