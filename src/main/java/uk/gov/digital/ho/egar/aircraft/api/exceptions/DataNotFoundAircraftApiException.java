package uk.gov.digital.ho.egar.aircraft.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST) 
abstract public class DataNotFoundAircraftApiException extends AircraftApiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DataNotFoundAircraftApiException() {
	}

	/**
	 * @param message
	 */
	public DataNotFoundAircraftApiException(String message) {
		super(message);
	}


}