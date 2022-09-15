package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.BookingEntity;
import com.devonfw.application.domain.model.BookingType;
import com.devonfw.application.domain.model.QBookingEntity;
import com.devonfw.application.service.model.BookingSearchCriteriaDto;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
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
        Pageable pageable = criteria.getPageable();
        QBookingEntity bookingEntity = QBookingEntity.bookingEntity;

        JPAQuery<BookingEntity> query = new JPAQuery<>(this.entityManager);
        query.from(bookingEntity);

        String name = criteria.getName();
        if ((name != null) && !name.isEmpty()) {
            query.where(bookingEntity.name.eq(name));
        }
        String bookingToken = criteria.getBookingToken();
        if (bookingToken != null && !bookingToken.isEmpty()) {
           query.where(bookingEntity.bookingToken.eq(bookingToken));
        }
        String comment = criteria.getComment();
        if (comment != null && !comment.isEmpty()) {
            query.where(bookingEntity.comment.eq(comment));
        }
        Instant bookingDate = criteria.getBookingDate();
        if (bookingDate != null) {
            query.where(bookingEntity.bookingDate.eq(bookingDate));
        }
        Instant expirationDate = criteria.getExpirationDate();
        if (expirationDate != null) {
            query.where(bookingEntity.expirationDate.eq(expirationDate));
        }
        Instant creationDate = criteria.getCreationDate();
        if (creationDate != null) {
            query.where(bookingEntity.creationDate.eq(creationDate));
        }
        String email = criteria.getEmail();
        if (email != null && !email.isEmpty()) {
            query.where(bookingEntity.email.eq(email));
        }
        Boolean canceled = criteria.getCanceled();
        if (canceled != null) {
            query.where(bookingEntity.canceled.eq(canceled));
        }
        BookingType bookingType = criteria.getBookingType();
        if (bookingType != null) {
            query.where(bookingEntity.bookingType.eq(bookingType));
        }
        Long table = criteria.getTableId();
        if (table != null) {
            query.where(bookingEntity.table.id.eq(table));
        }

        List<BookingEntity> bookingList = query.limit(pageable.getPageSize())
                .offset(pageable.getPageNumber() * pageable.getPageSize()).fetch();
        return new PageImpl<>(bookingList, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()),
        bookingList.size());

    }
}
