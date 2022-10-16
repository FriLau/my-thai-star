package com.devonfw.application.domain.model;

import com.devonfw.application.general.model.ApplicationPersistenceEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.Instant;

@Entity
@Setter
@Getter
@Table(name = "InvitedGuest")
public class InvitedGuestEntity extends ApplicationPersistenceEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookingId")
    private BookingEntity booking;

    private String guestToken;

    private String email;

    @Column(nullable = false, columnDefinition = "bool", length = 1)
    private Boolean accepted;

    private Instant modificationDate;

    private Long orderId;

    public InvitedGuestEntity() {

        super();
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
