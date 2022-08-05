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
import javax.persistence.Transient;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "Booking")
@Getter
@Setter
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

    private static final long serialVersionUID = 1L;

    public BookingEntity() {

        super();
        this.canceled = false;
    }

    /**
     * @return name
     */
    public String getName() {

        return this.name;
    }

    /**
     * @param name new value of {@link #getName}.
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * @return bookingToken
     */
    public String getBookingToken() {

        return this.bookingToken;
    }

    /**
     * @param bookingToken new value of {@link #getBookingToken}.
     */
    public void setBookingToken(String bookingToken) {

        this.bookingToken = bookingToken;
    }

    /**
     * @return comments
     */
    public String getComment() {

        return this.comment;
    }

    /**
     * @param comments new value of {@link #getComment}.
     */
    public void setComment(String comments) {

        this.comment = comments;
    }

    /**
     * @return bookingDate
     */
    public Instant getBookingDate() {

        return this.bookingDate;
    }

    /**
     * @param bookingDate new value of {@link #getBookingDate}.
     */
    public void setBookingDate(Instant bookingDate) {

        this.bookingDate = bookingDate;
    }

    /**
     * @return expirationDate
     */
    public Instant getExpirationDate() {

        return this.expirationDate;
    }

    /**
     * @param expirationDate new value of {@link #getExpirationDate}.
     */
    public void setExpirationDate(Instant expirationDate) {

        this.expirationDate = expirationDate;
    }

    /**
     * @return creationDate
     */
    public Instant getCreationDate() {

        return this.creationDate;
    }

    /**
     * @param creationDate new value of {@link #getCreationDate}.
     */
    public void setCreationDate(Instant creationDate) {

        this.creationDate = creationDate;
    }

    /**
     * @return canceled
     */
    public Boolean getCanceled() {

        return this.canceled;
    }

    /**
     * @param canceled new value of {@link #getCanceled}.
     */
    public void setCanceled(Boolean canceled) {

        this.canceled = canceled;
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
     * @param table new value of {@link #getTable}.
     */
    public void setTable(TableEntity table) {

        this.table = table;
    }

    /**
     * @return invitedGuests
     */
    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY)
    public List<InvitedGuestEntity> getInvitedGuests() {

        return this.invitedGuests;
    }

    /**
     * @param invitedGuests new value of {@link #getInvitedGuests}.
     */
    public void setInvitedGuests(List<InvitedGuestEntity> invitedGuests) {

        this.invitedGuests = invitedGuests;
    }

    /**
     * @return type
     */
    public BookingType getBookingType() {

        return this.bookingType;
    }

    /**
     * @param bookingType new value of {@link #getBookingType}.
     */
    public void setBookingType(BookingType bookingType) {

        this.bookingType = bookingType;
    }

    public String getEmail() {

        return this.email;
    }

    public void setEmail(String email) {

        this.email = email;

    }

    @Transient
    public Long getTableId() {

        if (this.table == null) {
            return null;
        }
        return this.table.getId();
    }

    public void setTableId(Long tableId) {

        if (tableId == null) {
            this.table = null;
        } else {
            TableEntity tableEntity = new TableEntity();
            tableEntity.setId(tableId);
            this.table = tableEntity;
        }
    }

    //TODO
//    /**
//     * @return order
//     */
//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "idOrder")
//    public OrderEntity getOrder() {
//
//        return this.order;
//    }
//
//    /**
//     * @param order new value of {@link #getOrder}.
//     */
//    public void setOrder(OrderEntity order) {
//
//        this.order = order;
//    }
//
//    @Transient
//    public Long getOrderId() {
//
//        if (this.order == null) {
//            return null;
//        }
//        return this.order.getId();
//    }
//
//    public void setOrderId(Long orderId) {
//
//        if (orderId == null) {
//            this.order = null;
//        } else {
//            OrderEntity orderEntity = new OrderEntity();
//            orderEntity.setId(orderId);
//            this.order = orderEntity;
//        }
//    }
//
//    /**
//     * @return orders
//     */
//    @OneToMany(mappedBy = "booking", fetch = FetchType.EAGER)
//    public List<OrderEntity> getOrders() {
//
//        return this.orders;
//    }
//
//    /**
//     * @param orders new value of {@link #getOrders}.
//     */
//    public void setOrders(List<OrderEntity> orders) {
//
//        this.orders = orders;
//    }

    /**
     * @return assistants
     */
    public Integer getAssistants() {

        return this.assistants;
    }

    /**
     * @param assistants new value of {@link #getAssistants}.
     */
    public void setAssistants(Integer assistants) {

        this.assistants = assistants;
    }

    //TODO
//    /**
//     * @return user
//     */
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "idUser")
//    public UserEntity getUser() {
//
//        return this.user;
//    }
//
//    /**
//     * @param user new value of {@link #getuser}.
//     */
//    public void setUser(UserEntity user) {
//
//        this.user = user;
//    }
//
//    @Transient
//    public Long getUserId() {
//
//        if (this.user == null) {
//            return null;
//        }
//        return this.user.getId();
//    }
//
//    public void setUserId(Long userId) {
//
//        if (userId == null) {
//            this.user = null;
//        } else {
//            UserEntity userEntity = new UserEntity();
//            userEntity.setId(userId);
//            this.user = userEntity;
//        }
//    }
}
