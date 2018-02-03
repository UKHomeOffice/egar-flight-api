package uk.gov.digital.ho.egar.aircraft.utils.impl;

import org.springframework.stereotype.Component;
import uk.gov.digital.ho.egar.aircraft.api.PathConstants;
import uk.gov.digital.ho.egar.aircraft.utils.UriAircraftUtilities;
import uk.gov.digital.ho.egar.constants.ServicePathConstants;

import java.net.URI;
import java.util.UUID;


/**
 * The uri aircraft utilities provide URI's for the provided parameters.
 * Utilises the service name and identifiers found in the aircraft api.
 * These can be used to construct redirection responses
 */
@Component
public class UriAircraftUtilitiesImpl implements UriAircraftUtilities {

	public static class URIFormatException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;

		public URIFormatException(Exception cause) {super(cause);}
	}

	private URI buildUri(String uriPath)  {
			try {
				return new URI(uriPath);
			} catch (Exception e) {
				throw new URIFormatException(e);
			}
		}

    @Override
    public URI getAircraftUri(UUID aircraftUuid)  {
        return buildUri(PathConstants.ROOT_PATH + ServicePathConstants.ROOT_PATH_SEPERATOR + aircraftUuid + ServicePathConstants.ROOT_PATH_SEPERATOR);
    }
}

