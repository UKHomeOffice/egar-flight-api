package uk.gov.digital.ho.egar.aircraft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Similar to the Aircraft interface but with identifying Ids.
 */
public interface AircraftWithUuid extends Aircraft {

	/**
	 * @return The aircrafts uuid.
	 */
	@JsonProperty("aircraft_uuid")
	UUID getAircraftUuid();

	void setAircraftUuid(final UUID val);


	/**
	 * @return The users uuid.
	 */
	@JsonIgnore
	UUID getUserUuid() ;

	void setUserUuid(final UUID val) ;

}
