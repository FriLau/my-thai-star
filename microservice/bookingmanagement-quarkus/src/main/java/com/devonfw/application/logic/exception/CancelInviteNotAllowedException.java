package com.devonfw.application.logic.exception;

/**
 *  Exception thrown when the User tries to cancel a Booking in the hour before the start time
 */
public class CancelInviteNotAllowedException extends RuntimeException {
    public CancelInviteNotAllowedException() {
        super("The booking can not be cancelled.");
    }
}
