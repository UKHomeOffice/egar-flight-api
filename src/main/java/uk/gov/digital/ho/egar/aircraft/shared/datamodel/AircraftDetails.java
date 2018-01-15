package uk.gov.digital.ho.egar.aircraft.shared.datamodel;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import uk.gov.digital.ho.egar.aircraft.shared.datamodel.converters.AircraftTypeConverter;
import uk.gov.digital.ho.egar.aircraft.shared.datamodel.converters.PersonConverter;
import uk.gov.digital.ho.egar.services.v1.flight.Aircraft;
import uk.gov.digital.ho.egar.services.v1.people.Person;
import uk.gov.digital.ho.egar.services.v1.referencedata.AircraftType;
/**
 * 
 * JIRA requirements
 * # The user must be logged in
# All data must be stored in the datastore in draft at the end of the page.
# The system must store the data submitted in a suitable way for onward transmission.
# All fields are mandatory for submission, 
# If all fields are not completed the system must state which fields have not completed.
# If all fields are not completed the system must give the user an option to continue to the next page and acknowledge that the fields are not complete.
# The system must give the user the option to save as a draft and return to the start screen.
# The system must give the user the option to return to the previous page or move onto the next
# Validation must allow the User to save and continue through the rest of the process.
# Validation rules must match documentation 
 * @author Keshava.Grama
 *
 */
@Data
@MappedSuperclass
public abstract class AircraftDetails  implements Aircraft {
	@Id
	@Column
	protected UUID uuid;
	
	@Column
	protected String registrationNumber;
	
	@Column
	@Convert(converter= AircraftTypeConverter.class)
	@JsonDeserialize(as=AircraftTypeImpl.class)
	protected AircraftType aircraftType;
	
	@Column
	@Convert(converter= PersonConverter.class)
	@JsonDeserialize(as=PersonImpl.class)
	protected Person responsiblePerson;
	
	@Column
	protected String crewContactInUK;
	
	@Column
	protected String usualBase;
	
	@Column
	protected boolean euTaxed;
}
