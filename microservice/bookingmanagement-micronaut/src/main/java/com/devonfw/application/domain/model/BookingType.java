package com.devonfw.application.domain.model;

import io.micronaut.core.annotation.ReflectiveAccess;

@ReflectiveAccess
public enum BookingType {

    COMMON, INVITED;

    public boolean isCommon() {

        return (this == COMMON);
    }

    public boolean isInvited() {

        return (this == INVITED);
    }

}
