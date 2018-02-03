/**
 * 
 */
package uk.gov.digital.ho.egar.aircraft.api.exceptions;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * @author localuser
 *
 */
public class ConstraintViolationAircraftApiException extends BadOperationAircraftApiException {

	private static final long serialVersionUID = 1L;
	
	public ConstraintViolationAircraftApiException(ConstraintViolationException ex) {
		super(buildValidationErrorCauseMessage(ex)) ;
	}


	private static String buildValidationErrorCauseMessage(ConstraintViolationException ex) {
		
		StringBuffer sb = new StringBuffer();
		
		for ( ConstraintViolation<?> error : ex.getConstraintViolations() )
		{
			sb.append("Error:");
			sb.append(error.getPropertyPath());
			sb.append(" '");
			sb.append(error.getMessage());
			sb.append("';\r");
		}
		
		return sb.toString();
	}


	
}
