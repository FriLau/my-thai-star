package com.devonfw.application.domain.model;

import com.devonfw.application.general.domain.model.ApplicationPersistenceEntity;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Setter
@Table(name = "InvitedGuest")
public class InvitedGuestEntity extends ApplicationPersistenceEntity {

    private BookingEntity booking;

    private String guestToken;

    private String email;

    private Boolean accepted;

    private Instant modificationDate;

    private Long orderId;

    public InvitedGuestEntity() {

        super();
    }

    /**
     * @return booking
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookingId")
    public BookingEntity getBooking() {

        return this.booking;
    }

    public String getGuestToken() {
        return guestToken;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    @Transient
    public Long getBookingId()
    {

        if (this.booking == null)
        {
            return null;
        }
        return this.booking.getId();
    }

    public void setBookingId(Long bookingId)
    {
        if (bookingId == null)
        {
            this.booking = null;
        } else
        {
            BookingEntity bookingEntity = new BookingEntity();
            bookingEntity.setId(bookingId);
            this.booking = bookingEntity;
        }
    }

}
