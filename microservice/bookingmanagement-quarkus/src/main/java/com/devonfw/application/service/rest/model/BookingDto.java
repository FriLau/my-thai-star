package com.devonfw.application.service.rest.model;


import com.devonfw.application.domain.model.BookingType;
import com.devonfw.application.general.domain.model.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
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

    //TODO: Check if needed
    @NotNull
    private String name;

    private String bookingToken;

    private String comment;

    @NotNull
    @Future
    private Instant bookingDate;

    private Instant expirationDate;

    private Instant creationDate;

    @NotNull
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

}
