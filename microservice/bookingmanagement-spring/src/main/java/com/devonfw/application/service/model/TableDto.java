package com.devonfw.application.service.model;

import com.devonfw.application.general.model.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
