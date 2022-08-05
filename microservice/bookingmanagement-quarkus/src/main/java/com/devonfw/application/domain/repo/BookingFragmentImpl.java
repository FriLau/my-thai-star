package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.BookingEntity;
import com.devonfw.application.service.rest.model.BookingSearchCriteriaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class BookingFragmentImpl implements BookingFragment {

    @Inject
    EntityManager entityManager;

    @Override
    public Page<BookingEntity> findBookings(BookingSearchCriteriaDto criteria)
    {
// TODO

        Pageable pageable = criteria.getPageable();
        return null;

    }
}
