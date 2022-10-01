package com.devonfw.application.general.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
//import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Getter
@Setter
@ToString
public class ApplicationSearchCriteriaDto {

  private int pageNumber = 0;

  private int pageSize = 10;

  private boolean determineTotal = false;

}
