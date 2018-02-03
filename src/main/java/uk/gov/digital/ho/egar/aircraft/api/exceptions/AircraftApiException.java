package uk.gov.digital.ho.egar.aircraft.api.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import uk.gov.digital.ho.egar.shared.util.exceptions.NoCallStackException;

/**
 * A base exception type that does not pick uo the stack trace.
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY) // Suppress empty arrays & nulls
public class AircraftApiException extends NoCallStackException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AircraftApiException() {
		this(null,null);		
	}

	public AircraftApiException(String message) {
		this(message,null);
	}

	public AircraftApiException(Throwable cause) {
		this(null,cause);
	}

	public AircraftApiException(String message, Throwable cause) {
		super(message, cause);
        }

}
