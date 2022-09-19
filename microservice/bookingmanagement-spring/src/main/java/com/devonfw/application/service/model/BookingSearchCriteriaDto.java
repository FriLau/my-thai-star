package com.devonfw.application.service.model;

import com.devonfw.application.domain.model.BookingType;
import com.devonfw.application.general.model.AbstractSearchCriteriaDto;
import com.devonfw.application.general.model.ApplicationSearchCriteriaDto;
import com.devonfw.application.utils.StringSearchConfigTo;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * used to find {@link com.devonfw.application.mtsj.dishmanagement.common.api.Booking}s.
 *
 */
@Getter
@Setter
public class BookingSearchCriteriaDto extends ApplicationSearchCriteriaDto {

    private static final long serialVersionUID = 1L;

    private String name;

    private String bookingToken;

    private String comment;

    private Instant bookingDate;

    private Instant expirationDate;

    private Instant creationDate;

    private String email;

    private Boolean canceled;

    private BookingType bookingType;

    private Long tableId;

    private Long orderId;

    private Integer assistants;

    private Long userId;

    private StringSearchConfigTo nameOption;

    private StringSearchConfigTo bookingTokenOption;

    private StringSearchConfigTo commentOption;

    private StringSearchConfigTo emailOption;


    /**
     * The constructor.
     */
    public BookingSearchCriteriaDto(){

        super();
    }
}
