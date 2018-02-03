package uk.gov.digital.ho.egar.aircraft.services.repository.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.gov.digital.ho.egar.aircraft.model.Aircraft;
import uk.gov.digital.ho.egar.aircraft.model.AircraftWithUuid;

/**
 * The aircraft persisted record entity.
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "AIRCRAFT")
public class AircraftPersistentRecord implements AircraftWithUuid{

	@Id
	@Column
	private UUID aircraftUuid ;

	@Column
	private UUID userUuid ;

	@Column
	@Size(max = 200)
	private String base;

	@Column
	@Size(max = 15)
	private String registration;

	@Column
	private Boolean taxesPaid;

	@Column
	@Size(max = 35)
	private String type;

    /**
     * Copies an aircraft's details to the current entity.
     * @param src The aircraft details source
     * @return The updated entity.
     */
    public AircraftPersistentRecord copy(Aircraft src )
    {
        this.setBase(src.getBase());
        this.setRegistration(src.getRegistration());
        this.setTaxesPaid(src.getTaxesPaid());
        this.setType(src.getType());

        return this ;
    }

}

