package com.devonfw.application.service.rest.model;

import com.devonfw.application.general.model.AbstractSearchCriteriaDto;
import com.devonfw.application.general.model.ApplicationSearchCriteriaDto;
import com.devonfw.application.utils.StringSearchConfigTo;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * used to find {@link com.devonfw.application.mtsj.dishmanagement.common.api.InvitedGuest}s.
 *
 */
@Getter
@Setter
public class InvitedGuestSearchCriteriaDto extends ApplicationSearchCriteriaDto {

    private static final long serialVersionUID = 1L;

    private Long bookingId;

    private String guestToken;

    private String email;

    private Boolean accepted;

    private Instant modificationDate;

    private Long orderId;

    private StringSearchConfigTo guestTokenOption;

    private StringSearchConfigTo emailOption;

    /**
     * The constructor.
     */
    public InvitedGuestSearchCriteriaDto() {

        super();
    }
}
