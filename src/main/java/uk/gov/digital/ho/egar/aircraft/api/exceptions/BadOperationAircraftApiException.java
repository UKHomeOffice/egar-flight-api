package uk.gov.digital.ho.egar.aircraft.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  
 */
@ResponseStatus(value=HttpStatus.FORBIDDEN) 
public class BadOperationAircraftApiException extends AircraftApiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * @param message
	 */
	public BadOperationAircraftApiException(String message) {
		super(message);
	}




	
}