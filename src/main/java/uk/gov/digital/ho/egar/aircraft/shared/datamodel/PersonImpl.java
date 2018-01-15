package uk.gov.digital.ho.egar.aircraft.shared.datamodel;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import groovy.transform.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import uk.gov.digital.ho.egar.services.v1.people.Gender;
import uk.gov.digital.ho.egar.services.v1.people.Person;

@Component("person")
@EqualsAndHashCode(callSuper=false)
public class PersonImpl implements Person {
	@JsonIgnore
	@Getter
	protected UUID uuid;
	@Getter
	@Setter
	protected String firstNames;
	@Getter
	@Setter
	protected String lastNames;
	@Getter
	@Setter
	protected Gender gender;
	@Getter
	@Setter
	@JsonFormat(pattern = "dd/MM/yyyy")
	protected LocalDate dateOfBirth;
}
