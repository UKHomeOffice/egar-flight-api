package uk.gov.digital.ho.egar.aircraft.api.rest;

import static uk.gov.digital.ho.egar.aircraft.api.PathConstants.AIRCRAFT_PATH_VARIABLE;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import uk.gov.digital.ho.egar.aircraft.api.AircraftRestApi;
import uk.gov.digital.ho.egar.aircraft.api.PathConstants;
import uk.gov.digital.ho.egar.aircraft.api.exceptions.AircraftApiException;
import uk.gov.digital.ho.egar.aircraft.model.Aircraft;
import uk.gov.digital.ho.egar.aircraft.model.AircraftWithUuid;
import uk.gov.digital.ho.egar.aircraft.services.AircraftService;
import uk.gov.digital.ho.egar.aircraft.utils.UriAircraftUtilities;
import uk.gov.digital.ho.egar.shared.auth.api.token.AuthValues;

@RestController
@RequestMapping(PathConstants.ROOT_PATH)
@Api(value = PathConstants.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class AircraftController implements AircraftRestApi {

    /**
     * The user header name.
     */
    private static final String USER_HEADER_NAME = "x-auth-subject";

    /**
     * The aircraft service
     */
    private final AircraftService aircraftServices;

    /**
     * The uri aircraft utilities.
     */
    private final UriAircraftUtilities uriAircraftUtilities;

    /**
     * Constructor that takes an aircraft service and uri aircraft utilities.
     * @param aircraftServices The aircraft service.
     * @param uriAircraftUtilities The uri aircraft utilities
     */
    public AircraftController(@Autowired final AircraftService aircraftServices,
                              @Autowired final UriAircraftUtilities uriAircraftUtilities) {
        this.aircraftServices = aircraftServices;
        this.uriAircraftUtilities = uriAircraftUtilities;
    }

    /**
     * A quick & dirty fix to change 400 due to a ServletRequestBindingException to a 401.
     * 99% of expected ServletRequestBindingException will be due to a missing header.
     */
    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public @ResponseBody AircraftApiException handleException(ServletRequestBindingException ex) {
        return new AircraftApiException(ex.getMessage()) ;
    }

    @Override
    @ApiOperation(value = "Add a new aircraft record.",
            notes = "Add a new aircraft record to the GAR database")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 303,
                    message = "See other location when successful"),
            @ApiResponse(
                    code = 400,
                    message = "The request that was sent was invalid."),
            @ApiResponse(
                    code = 401,
                    message = "The operation was unauthorized."),
            @ApiResponse(
                    code = 403,
                    message = "The operation was forbidden")})
    @PostMapping(value = PathConstants.PATH_ADD_AIRCRAFT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public ResponseEntity<Void> addAircraftRecord(
            @RequestHeader(USER_HEADER_NAME) UUID userUuid,
            @Valid @RequestBody Aircraft aircraft, Errors errors) throws AircraftApiException {
    	
        if (errors.hasErrors()){
            return new ResponseEntity(new ApiErrors(errors), HttpStatus.BAD_REQUEST);
        }
        
        AircraftWithUuid serviceResponse = aircraftServices.addAircraftRecord(aircraft, userUuid);

        //Creating the redirection location URI
        URI redirectLocation = uriAircraftUtilities.getAircraftUri(serviceResponse.getAircraftUuid());

        //Creating the response headers
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(redirectLocation);

        return new ResponseEntity<Void>(responseHeaders, HttpStatus.SEE_OTHER);
    }


    @Override
    @ApiOperation(value = "Get an aircraft.",
            notes = "Get the current state for an aircraft record",
            response = AircraftWithUuid.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved."),
            @ApiResponse(
                    code = 400,
                    message = "The request that was sent was invalid"),
            @ApiResponse(
                    code = 401,
                    message = "The operation was unauthorized"),
            @ApiResponse(
                    code = 403,
                    message = "The operation was forbidden")
    })
    @GetMapping(value = PathConstants.PATH_AIRCRAFT_ID,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AircraftWithUuid getAircraftRecord(
            @RequestHeader(USER_HEADER_NAME) UUID userUuid,
            @PathVariable(AIRCRAFT_PATH_VARIABLE) UUID aircraftUuid) throws AircraftApiException {
        //Retrieves the aircraft from the service.
        AircraftWithUuid serviceResponse = aircraftServices.getAircraftRecord(aircraftUuid, userUuid);
        return serviceResponse;
    }

    @Override
    @ApiOperation(value = "Update an aircraft record.",
            notes = "amend an aircraft record in the GAR database")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 303,
                    message = "See other location when successful"),
            @ApiResponse(
                    code = 401,
                    message = "The operation was unauthorized"),
            @ApiResponse(
                    code = 400,
                    message = "The request that was sent was invalid"),
            @ApiResponse(
                    code = 403,
                    message = "The operation was forbidden")})
    @PostMapping(value = PathConstants.PATH_AIRCRAFT_ID,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public ResponseEntity<Void> updateAircraftRecord(
            @RequestHeader(USER_HEADER_NAME) UUID userUuid,
            @PathVariable(AIRCRAFT_PATH_VARIABLE) UUID aircraftUuid,
            @Valid @RequestBody Aircraft aircraft,
            Errors errors) throws AircraftApiException {

        if (errors.hasErrors()){
            return new ResponseEntity(new ApiErrors(errors), HttpStatus.BAD_REQUEST);
        }


        //Updates the aircraft
        AircraftWithUuid serviceResponse = aircraftServices.updateAircraftRecord(aircraft, aircraftUuid, userUuid);

        //Creating the redirection location URI
        URI redirectLocation = uriAircraftUtilities.getAircraftUri(serviceResponse.getAircraftUuid());

        //Creating the response headers
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(redirectLocation);

        return new ResponseEntity<Void>(responseHeaders, HttpStatus.SEE_OTHER);
    }
//---------------------------------------------------------------------------------------------------------
    
    /**
     * A get endpoint that bulk retrieves a list of GARs
     * -------------------------------------------------------------------------------------------
     * @throws GarDataserviceException 
     */
    
    
    @Override
    @ApiOperation(value = "Bulk retrieve a list of Aircrafts.",
            notes = "Retrieve a list of aircrafts for existing General Aviation Reports for a user")
    @ApiResponses(value = {
    		@ApiResponse(
                    code = 200,
                    message = "Successfully retrieved.",
                    response =  AircraftWithUuid[].class),
            @ApiResponse(
                    code = 401,
                    message = "The operation was unauthorized")})
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = PathConstants.PATH_BULK,
    			consumes = MediaType.APPLICATION_JSON_VALUE,
           		produces = MediaType.APPLICATION_JSON_VALUE)
    public AircraftWithUuid[] bulkRetrieveAircrafts(@RequestHeader(AuthValues.USERID_HEADER) UUID uuidOfUser, 
    									   @RequestBody List<UUID> aircraftUuids) {
    	
    	return aircraftServices.getBulkAircrafts(uuidOfUser,aircraftUuids);
    }
    
    
    public static class ApiErrors {

        @JsonProperty("message")
        private final List<String> errorMessages = new ArrayList<>();

        public ApiErrors(Errors errors) {
            for(final FieldError error : errors.getFieldErrors()){
                errorMessages.add(error.getField() + ": " + error.getDefaultMessage());
            }
            for(final ObjectError error : errors.getGlobalErrors()){
                errorMessages.add(error.getObjectName() + ": " + error.getDefaultMessage());
            }
        }
    }

}
