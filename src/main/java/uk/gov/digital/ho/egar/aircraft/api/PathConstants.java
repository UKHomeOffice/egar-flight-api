package uk.gov.digital.ho.egar.aircraft.api;

import uk.gov.digital.ho.egar.constants.ServicePathConstants;

/**
 * The path constants for the restful API.
 */
public interface PathConstants {
	public static final String ROOT_SERVICE_NAME = "aircraft";
	public static final String ROOT_PATH = 
			ServicePathConstants.ROOT_PATH_SEPERATOR + 
			ServicePathConstants.ROOT_SERVICE_API + 
			ServicePathConstants.ROOT_PATH_SEPERATOR +
			ServicePathConstants.SERVICE_VERSION_ONE +
			ServicePathConstants.ROOT_PATH_SEPERATOR +
			ROOT_SERVICE_NAME;

	public static final String AIRCRAFT_PATH_VARIABLE = "aircraft_uuid";

	public static final String PATH_ADD_AIRCRAFT = "/";
	public static final String PATH_AIRCRAFT_ID = "/{"+AIRCRAFT_PATH_VARIABLE+"}";
}
