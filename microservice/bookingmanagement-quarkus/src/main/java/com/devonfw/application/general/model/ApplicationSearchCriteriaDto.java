package com.devonfw.application.general.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Getter
@Setter
@ToString
public class ApplicationSearchCriteriaDto {

  @Schema(description = "Page Number", defaultValue = "0")
  private int pageNumber = 0;

  @Schema(description = "Page Size", defaultValue = "10")
  private int pageSize = 10;

  @Schema(description = "determine total")
  private boolean determineTotal = false;

}
