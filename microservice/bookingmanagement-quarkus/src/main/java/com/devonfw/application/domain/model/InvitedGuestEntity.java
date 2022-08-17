package com.devonfw.application.domain.model;

import com.devonfw.application.general.domain.model.ApplicationPersistenceEntity;
import lombok.Getter;
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

    //TODO depe
    //private OrderEntity order;
    private Long idOrder;

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

    public Long getIdOrder() {
        return idOrder;
    }

}
