package com.devonfw.application.service.rest.model;

import com.devonfw.application.general.domain.model.AbstractSearchCriteriaDto;
import lombok.Getter;
import lombok.Setter;

/**
 * used to find {@link com.devonfw.application.mtsj.dishmanagement.common.api.Table}s.
 *
 */
@Getter
@Setter
public class TableSearchCriteriaDto extends AbstractSearchCriteriaDto {

    private static final long serialVersionUID = 1L;

    private Integer seatsNumber;

    /**
     * The constructor.
     */
    public TableSearchCriteriaDto() {

        super();
    }
}
