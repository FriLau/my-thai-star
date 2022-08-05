package com.devonfw.application.service.rest.model;

import com.devonfw.application.general.domain.model.AbstractDto;
import lombok.*;

/**
 * Composite transport object of Table
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableDto extends AbstractDto {

    private static final long serialVersionUID = 1L;

    private Integer seatsNumber;

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.seatsNumber == null) ? 0 : this.seatsNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        // class check will be done by super type EntityTo!
        if (!super.equals(obj)) {
            return false;
        }
        TableDto other = (TableDto) obj;
        if (this.seatsNumber == null) {
            if (other.seatsNumber != null) {
                return false;
            }
        } else if (!this.seatsNumber.equals(other.seatsNumber)) {
            return false;
        }
        return true;
    }

}
