package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.InvitedGuestEntity;
import com.devonfw.application.domain.model.QInvitedGuestEntity;
import com.devonfw.application.service.model.InvitedGuestSearchCriteriaDto;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.List;

public class InvitedGuestFragmentImpl implements InvitedGuestFragment{

    @Inject
    EntityManager entityManager;

    /**
     * @param criteria the {@link InvitedGuestSearchCriteriaDto} with the criteria to search.
     * @return the {@link Page} of the {@link InvitedGuestEntity} objects that matched the search.
     */
    @Override
    public Page<InvitedGuestEntity> findInvitedGuests(InvitedGuestSearchCriteriaDto criteria)
    {
        Pageable pageable = criteria.getPageable();
        QInvitedGuestEntity invitedGuestEntity = QInvitedGuestEntity.invitedGuestEntity;

        JPAQuery<InvitedGuestEntity> query = new JPAQuery<>(this.entityManager);

        Long booking = criteria.getBookingId();
        if (booking != null) {
            query.where(invitedGuestEntity.booking.id.eq(booking));
        }
        String guestToken = criteria.getGuestToken();
        if ((guestToken != null) && !guestToken.isEmpty()) {
            query.where(invitedGuestEntity.guestToken.eq(guestToken));
        }
        String email = criteria.getEmail();
        if ((email != null) && !email.isEmpty()) {
            query.where(invitedGuestEntity.email.eq(email));
        }
        Boolean accepted = criteria.getAccepted();
        if (accepted != null) {
            query.where(invitedGuestEntity.accepted.eq(accepted));
        }
        Instant modificationDate = criteria.getModificationDate();
        if (modificationDate != null) {
            query.where(invitedGuestEntity.modificationDate.eq(modificationDate));
        }
        Long orderId = criteria.getOrderId();
        if (orderId != null) {
            query.where(invitedGuestEntity.orderId.eq(orderId));
        }

        List<InvitedGuestEntity> invitedGuestList = query.limit(pageable.getPageSize())
                .offset(pageable.getPageNumber() * pageable.getPageSize()).fetch();

        return new PageImpl<>(invitedGuestList, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()),
                invitedGuestList.size());
    }
}
