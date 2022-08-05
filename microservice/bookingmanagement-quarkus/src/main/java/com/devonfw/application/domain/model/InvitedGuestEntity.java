package com.devonfw.application.domain.model;

import com.devonfw.application.general.domain.model.ApplicationPersistenceEntity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "InvitedGuest")
public class InvitedGuestEntity extends ApplicationPersistenceEntity {

    private BookingEntity booking;

    private String guestToken;

    private String email;

    private Boolean accepted;

    private Instant modificationDate;

    //TODO depe
    //private OrderEntity order;

    private static final long serialVersionUID = 1L;

    public InvitedGuestEntity() {

        super();
    }

    /**
     * @return booking
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idBooking")
    public BookingEntity getBooking() {

        return this.booking;
    }

    /**
     * @param booking new value of {@link #getBooking}.
     */
    public void setBooking(BookingEntity booking) {

        this.booking = booking;
    }

    /**
     * @return guestToken
     */
    public String getGuestToken() {

        return this.guestToken;
    }

    /**
     * @param guestToken new value of {@link #getGuestToken}.
     */
    public void setGuestToken(String guestToken) {

        this.guestToken = guestToken;
    }

    /**
     * @return email
     */
    public String getEmail() {

        return this.email;
    }

    /**
     * @param email new value of {@link #getEmail}.
     */
    public void setEmail(String email) {

        this.email = email;
    }

    /**
     * @return accepted
     */
    public Boolean getAccepted() {

        return this.accepted;
    }

    /**
     * @param accepted new value of {@link #getAccepted}.
     */
    public void setAccepted(Boolean accepted) {

        this.accepted = accepted;
    }

    /**
     * @return modificationDate
     */
    public Instant getModificationDate() {

        return this.modificationDate;
    }

    /**
     * @param modificationDate new value of {@link #getModificationDate}.
     */
    public void setModificationDate(Instant modificationDate) {

        this.modificationDate = modificationDate;
    }

    @Transient
    public Long getBookingId() {

        if (this.booking == null) {
            return null;
        }
        return this.booking.getId();
    }

    public void setBookingId(Long bookingId) {

        if (bookingId == null) {
            this.booking = null;
        } else {
            BookingEntity bookingEntity = new BookingEntity();
            bookingEntity.setId(bookingId);
            this.booking = bookingEntity;
        }
    }

//    //TODO depe
//    /**
//     * @return order
//     */
//    @OneToOne
//    @JoinColumn(name = "idOrder")
//    public OrderEntity getOrder() {
//
//        return this.order;
//    }
//
//    //TODO depe
//    /**
//     * @param order new value of {@link #getOrder}.
//     */
//    public void setOrder(OrderEntity order) {
//
//        this.order = order;
//    }

}
