package uk.gov.digital.ho.egar.flight.shared.datamodel;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import uk.gov.digital.ho.egar.services.v1.flight.GeographicLocation;
import uk.gov.digital.ho.egar.services.v1.flight.WhereWhen;

@Data
public class WhereWhenImpl implements WhereWhen{
	
	@JsonDeserialize(as=GeographicLocationImpl.class)
	GeographicLocation where;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	LocalDate when;
	
	LocalTime timeOfDay;
}
