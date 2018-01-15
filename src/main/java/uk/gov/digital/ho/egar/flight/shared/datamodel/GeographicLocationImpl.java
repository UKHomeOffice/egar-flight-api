package uk.gov.digital.ho.egar.flight.shared.datamodel;

import org.springframework.stereotype.Component;

import lombok.Data;
import uk.gov.digital.ho.egar.services.v1.flight.GeographicLocation;

@Component("geographicLocation")
@Data
public class GeographicLocationImpl implements GeographicLocation{
	//@JsonDeserialize(as=ICAOCodeImpl.class)
	String icaoCode;
	//@JsonDeserialize(as=IATACodeImpl.class)
	String iataCode;
	String latitude;
	String longitude;
	String postcode;
	String freeTextLocation;
	String via;
	
	@Override
	public boolean validateGeographicLocation() {
		// TODO Auto-generated method stub
		return false;
	}

}
