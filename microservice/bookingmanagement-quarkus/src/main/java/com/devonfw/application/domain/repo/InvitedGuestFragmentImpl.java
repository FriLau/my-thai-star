package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.InvitedGuestEntity;
import com.devonfw.application.domain.model.QInvitedGuestEntity;
import com.devonfw.application.service.rest.model.InvitedGuestSearchCriteriaDto;
import com.devonfw.application.utils.QueryUtil;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
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
        QInvitedGuestEntity invitedGuestEntity = QInvitedGuestEntity.invitedGuestEntity;
        List<Predicate> predicates = new ArrayList<>();

        Long booking = criteria.getBookingId();
        if (booking != null) {
            predicates.add(invitedGuestEntity.booking.id.eq(booking));
        }
        String guestToken = criteria.getGuestToken();
        if ((guestToken != null) && !guestToken.isEmpty()) {
            predicates.add(invitedGuestEntity.guestToken.eq(guestToken));
        }
        String email = criteria.getEmail();
        if ((email != null) && !email.isEmpty()) {
            predicates.add(invitedGuestEntity.email.eq(email));
        }
        Boolean accepted = criteria.getAccepted();
        if (accepted != null) {
            predicates.add(invitedGuestEntity.accepted.eq(accepted));
        }
        var modificationDate = criteria.getModificationDate();
        if (modificationDate != null) {
            predicates.add(invitedGuestEntity.modificationDate.eq(modificationDate));
        }
        Long orderId = criteria.getOrderId();
        if (orderId != null) {
            predicates.add(invitedGuestEntity.orderId.eq(orderId));
        }

        JPAQuery<InvitedGuestEntity> query = new JPAQuery<InvitedGuestEntity>(this.entityManager).from(invitedGuestEntity);
        if (!predicates.isEmpty())
        {
            query.where(predicates.toArray(Predicate[]::new));
        }

        Pageable pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize());
        return QueryUtil.findPaginated(pageable, query, criteria.isDetermineTotal());
    }
}
