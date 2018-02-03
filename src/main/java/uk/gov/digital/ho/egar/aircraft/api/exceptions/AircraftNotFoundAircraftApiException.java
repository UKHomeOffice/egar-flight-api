package uk.gov.digital.ho.egar.aircraft.api.exceptions;

import java.util.UUID;

/**
 * @author localuser
 *
 */
public class AircraftNotFoundAircraftApiException extends DataNotFoundAircraftApiException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public AircraftNotFoundAircraftApiException(final UUID aircraftId , final UUID userId )
	{
		super(String.format("Can not find aircraft %s for user %s", aircraftId.toString(), userId.toString()));
	}
	
}
