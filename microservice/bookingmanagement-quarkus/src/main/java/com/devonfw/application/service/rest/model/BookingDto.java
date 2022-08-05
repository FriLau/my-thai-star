package com.devonfw.application.service.rest.model;


import com.devonfw.application.domain.model.BookingType;
import com.devonfw.application.general.domain.model.AbstractDto;
import lombok.*;

import java.time.Instant;
import java.util.List;


/**
 * Composite transport object of Booking
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class  BookingDto extends AbstractDto {

    private static final long serialVersionUID = 1L;

    //TODO: Check if needed
//    @NotNull
    private String name;

    private String bookingToken;

    private String comment;

//    @NotNull
//    @Future
    private Instant bookingDate;

    private Instant expirationDate;

    private Instant creationDate;

//    @NotNull
//    @EmailExtended
    private String email;

    private Boolean canceled;

    private BookingType bookingType;

    private Long orderId;

    private Integer assistants;

    private Long userId;

    private TableDto table;

    private List<InvitedGuestDto> invitedGuests;

    //TODO depe
    //private OrderEto order;

    //TODO depe
    //private List<OrderEto> orders;

    //TODO depe
    //private UserEto user;

    @Override
    public int hashCode() {
        //TODO add new variables

        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.bookingToken == null) ? 0 : this.bookingToken.hashCode());
        result = prime * result + ((this.comment == null) ? 0 : this.comment.hashCode());
        result = prime * result + ((this.bookingDate == null) ? 0 : this.bookingDate.hashCode());
        result = prime * result + ((this.expirationDate == null) ? 0 : this.expirationDate.hashCode());
        result = prime * result + ((this.creationDate == null) ? 0 : this.creationDate.hashCode());
        result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
        result = prime * result + ((this.canceled == null) ? 0 : this.canceled.hashCode());
        result = prime * result + ((this.bookingType == null) ? 0 : this.bookingType.hashCode());

//        result = prime * result + ((this.tableId == null) ? 0 : this.tableId.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        //TODO add new variables

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
        BookingDto other = (BookingDto) obj;
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        if (this.bookingToken == null) {
            if (other.bookingToken != null) {
                return false;
            }
        } else if (!this.bookingToken.equals(other.bookingToken)) {
            return false;
        }
        if (this.comment == null) {
            if (other.comment != null) {
                return false;
            }
        } else if (!this.comment.equals(other.comment)) {
            return false;
        }
        if (this.bookingDate == null) {
            if (other.bookingDate != null) {
                return false;
            }
        } else if (!this.bookingDate.equals(other.bookingDate)) {
            return false;
        }
        if (this.expirationDate == null) {
            if (other.expirationDate != null) {
                return false;
            }
        } else if (!this.expirationDate.equals(other.expirationDate)) {
            return false;
        }
        if (this.creationDate == null) {
            if (other.creationDate != null) {
                return false;
            }
        } else if (!this.creationDate.equals(other.creationDate)) {
            return false;
        }
        if (this.email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!this.email.equals(other.email)) {
            return false;
        }
        if (this.canceled == null) {
            if (other.canceled != null) {
                return false;
            }
        } else if (!this.canceled.equals(other.canceled)) {
            return false;
        }
        if (this.bookingType == null) {
            if (other.bookingType != null) {
                return false;
            }
        } else if (!this.bookingType.equals(other.bookingType)) {
            return false;
        }

//        if (this.tableId == null) {
//            if (other.tableId != null) {
//                return false;
//            }
//        } else if (!this.tableId.equals(other.tableId)) {
//            return false;
//        }

        return true;
    }
}
