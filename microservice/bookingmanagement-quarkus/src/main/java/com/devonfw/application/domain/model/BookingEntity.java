package com.devonfw.application.domain.model;

import com.devonfw.application.general.domain.model.ApplicationPersistenceEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import java.time.Instant;
import java.util.List;

@Entity
@Setter
@Table(name = "Booking")
public class BookingEntity extends ApplicationPersistenceEntity {

    private String name;

    private String bookingToken;

    private String comment;

    private Instant bookingDate;

    private Instant expirationDate;

    private Instant creationDate;

    private String email;

    private Boolean canceled;

    private BookingType bookingType;

    private TableEntity table;

    //TODO depe
    //private OrderEntity order;
    private Long idOrder;

    //TODO depe
    //private UserEntity user;
    private Long idUser;

    private List<InvitedGuestEntity> invitedGuests;

    //TODO depe
    //private List<OrderEntity> orders;

// TODO
//    @Min(value = 1, message = "Assistants must be greater than 0")
//    @Digits(integer = 2, fraction = 0)
    private Integer assistants;

    public BookingEntity() {

        super();
        this.canceled = false;
    }

    public String getName() {
        return name;
    }

    public String getBookingToken() {
        return bookingToken;
    }

    public String getComment() {
        return comment;
    }

    public Instant getBookingDate() {
        return bookingDate;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public BookingType getBookingType() {
        return bookingType;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public Long getIdUser() {
        return idUser;
    }

    public Integer getAssistants() {
        return assistants;
    }

    /**
     * @return table
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idTable")
    public TableEntity getTable() {

        return this.table;
    }

    /**
     * @return invitedGuests
     */
    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY)
    public List<InvitedGuestEntity> getInvitedGuests() {

        return this.invitedGuests;
    }
}
