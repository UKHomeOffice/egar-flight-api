package uk.gov.digital.ho.egar.flight.shared.datamodel;

import lombok.Data;
import uk.gov.digital.ho.egar.services.v1.referencedata.ICAOCode;

@Data
public class ICAOCodeImpl implements ICAOCode{
	private String icaoCode;

}
