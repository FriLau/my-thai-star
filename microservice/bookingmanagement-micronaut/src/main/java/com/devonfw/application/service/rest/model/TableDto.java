package com.devonfw.application.service.rest.model;

import com.devonfw.application.general.model.AbstractDto;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.micronaut.core.annotation.ReflectiveAccess;
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
@ReflectiveAccess
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TableDto extends AbstractDto {

    private static final long serialVersionUID = 1L;

    private Integer seatsNumber;
}
