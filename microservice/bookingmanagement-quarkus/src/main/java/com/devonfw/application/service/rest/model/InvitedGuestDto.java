package com.devonfw.application.service.rest.model;

import com.devonfw.application.domain.model.BookingEntity;
import com.devonfw.application.general.domain.model.AbstractDto;
import lombok.*;

import java.time.Instant;

/**
 * Composite transport object of InvitedGuest
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitedGuestDto extends AbstractDto {

    private static final long serialVersionUID = 1L;

    private Long bookingId;

    private String guestToken;

    private String email;

    private Boolean accepted;

    private Instant modificationDate;

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = super.hashCode();

        result = prime * result + ((this.bookingId == null) ? 0 : this.bookingId.hashCode());
        result = prime * result + ((this.guestToken == null) ? 0 : this.guestToken.hashCode());
        result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
        result = prime * result + ((this.accepted == null) ? 0 : this.accepted.hashCode());
        result = prime * result + ((this.modificationDate == null) ? 0 : this.modificationDate.hashCode());
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
        InvitedGuestDto other = (InvitedGuestDto) obj;

        if (this.bookingId == null) {
            if (other.bookingId != null) {
                return false;
            }
        } else if (!this.bookingId.equals(other.bookingId)) {
            return false;
        }
        if (this.guestToken == null) {
            if (other.guestToken != null) {
                return false;
            }
        } else if (!this.guestToken.equals(other.guestToken)) {
            return false;
        }
        if (this.email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!this.email.equals(other.email)) {
            return false;
        }
        if (this.accepted == null) {
            if (other.accepted != null) {
                return false;
            }
        } else if (!this.accepted.equals(other.accepted)) {
            return false;
        }
        if (this.modificationDate == null) {
            if (other.modificationDate != null) {
                return false;
            }
        } else if (!this.modificationDate.equals(other.modificationDate)) {
            return false;
        }
        return true;
    }
}
