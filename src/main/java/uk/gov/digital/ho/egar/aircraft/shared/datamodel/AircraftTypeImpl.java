package uk.gov.digital.ho.egar.aircraft.shared.datamodel;

import org.springframework.stereotype.Component;

import lombok.Data;
import uk.gov.digital.ho.egar.services.v1.referencedata.AircraftType;

@Component("aircraftType")
@Data
public class AircraftTypeImpl implements AircraftType {
	String aircraftType;
}
