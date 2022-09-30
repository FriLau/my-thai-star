package com.devonfw.application.service.rest.model;

import com.devonfw.application.general.model.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private Long orderId;

}
