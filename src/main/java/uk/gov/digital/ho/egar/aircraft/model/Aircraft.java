package uk.gov.digital.ho.egar.aircraft.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import uk.gov.digital.ho.egar.aircraft.services.repository.model.AircraftPersistentRecord;

/**
 * An interface describing the top level methods on an aircraft.
 */
@JsonDeserialize( as = AircraftPersistentRecord.class)
public interface Aircraft {

    /**
     * @return The aircraft type.
     */
    String getType();

    /**
     * @return The aircraft registration.
     */
    String getRegistration();

    /**
     * @return The aircrafts usual base.
     */
    String getBase();

    /**
     * @return Whether or not the aircraft has taxes paid.
     */
    Boolean getTaxesPaid();

}
