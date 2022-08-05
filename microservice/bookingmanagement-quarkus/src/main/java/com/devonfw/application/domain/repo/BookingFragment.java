package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.BookingEntity;
import com.devonfw.application.service.rest.model.BookingSearchCriteriaDto;
import org.springframework.data.domain.Page;

public interface BookingFragment {

    public Page<BookingEntity> findBookings(BookingSearchCriteriaDto criteria);
}
