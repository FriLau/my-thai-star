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
}
