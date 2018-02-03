package uk.gov.digital.ho.egar.aircraft.utils;

import java.net.URI;
import java.util.UUID;


/**
 * The uri aircraft utilities provide URI's for the provided parameters.
 * These can be used to construct redirection responses
 */
public interface UriAircraftUtilities {

    /**
     * Gets the Aircraft URI from the provided aircraft id
     * @param aircraftUuid the aircraft uuid.
     * @return The Aircraft URI
     * @throws RuntimeException When unable to construct a valid URI
     */
    URI getAircraftUri(final UUID aircraftUuid);

}
