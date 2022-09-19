package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.BookingEntity;
import com.devonfw.application.domain.model.BookingType;
import com.devonfw.application.domain.model.QBookingEntity;
import com.devonfw.application.service.rest.model.BookingSearchCriteriaDto;
import com.devonfw.application.utils.QueryUtil;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class BookingFragmentImpl implements BookingFragment {

    @Inject
    EntityManager entityManager;

    /**
     * @param criteria the {@link BookingSearchCriteriaDto} with the criteria to search.
     * @return the {@link Page} of the {@link BookingEntity} objects that matched the search.
     */
    @Override
    public Page<BookingEntity> findBookings(BookingSearchCriteriaDto criteria)
    {
        QBookingEntity bookingEntity = QBookingEntity.bookingEntity;
        List<Predicate> predicates = new ArrayList<>();

        String name = criteria.getName();
        if ((name != null) && !name.isEmpty()) {
            predicates.add(bookingEntity.name.eq(name));
        }
        String bookingToken = criteria.getBookingToken();
        if (bookingToken != null && !bookingToken.isEmpty()) {
            predicates.add(bookingEntity.bookingToken.eq(bookingToken));
        }
        String comment = criteria.getComment();
        if (comment != null && !comment.isEmpty()) {
            predicates.add(bookingEntity.comment.eq(comment));
        }
        var bookingDate = criteria.getBookingDate();
        if (bookingDate != null) {
            predicates.add(bookingEntity.bookingDate.eq(bookingDate));
        }
        var expirationDate = criteria.getExpirationDate();
        if (expirationDate != null) {
            predicates.add(bookingEntity.expirationDate.eq(expirationDate));
        }
        var creationDate = criteria.getCreationDate();
        if (creationDate != null) {
            predicates.add(bookingEntity.creationDate.eq(creationDate));
        }
        String email = criteria.getEmail();
        if (email != null && !email.isEmpty()) {
            predicates.add(bookingEntity.email.eq(email));
        }
        Boolean canceled = criteria.getCanceled();
        if (canceled != null) {
            predicates.add(bookingEntity.canceled.eq(canceled));
        }
        BookingType bookingType = criteria.getBookingType();
        if (bookingType != null) {
            predicates.add(bookingEntity.bookingType.eq(bookingType));
        }
        Long table = criteria.getTableId();
        if (table != null) {
            predicates.add(bookingEntity.table.id.eq(table));
        }

        JPAQuery<BookingEntity> query = new JPAQuery<BookingEntity>(this.entityManager).from(bookingEntity);
        if (!predicates.isEmpty())
        {
            query.where(predicates.toArray(Predicate[]::new));
        }

        Pageable pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize());
        return QueryUtil.findPaginated(pageable, query, criteria.isDetermineTotal());
    }
}
