package com.devonfw.application.service.rest.model;


import com.devonfw.application.domain.model.BookingType;
import com.devonfw.application.general.model.AbstractDto;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.micronaut.core.annotation.ReflectiveAccess;
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
@ReflectiveAccess
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class BookingDto extends AbstractDto {

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
    private String email;

    private Boolean canceled;

    private BookingType bookingType;

    private TableDto table;

    private Long orderId;

    private Long userId;

    private List<InvitedGuestDto> invitedGuests;

    private Integer assistants;
}
