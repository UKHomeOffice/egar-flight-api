package uk.gov.digital.ho.egar.flight.shared.datamodel;

import lombok.Data;
import uk.gov.digital.ho.egar.services.v1.referencedata.IATACode;

@Data
public class IATACodeImpl implements IATACode{
	String iataCode;
}
