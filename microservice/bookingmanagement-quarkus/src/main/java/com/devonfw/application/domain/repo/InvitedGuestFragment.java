package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.BookingEntity;
import com.devonfw.application.domain.model.InvitedGuestEntity;
import com.devonfw.application.service.rest.model.BookingSearchCriteriaDto;
import com.devonfw.application.service.rest.model.InvitedGuestSearchCriteriaDto;
import org.springframework.data.domain.Page;

public interface InvitedGuestFragment {
    public Page<InvitedGuestEntity> findInvitedGuests(InvitedGuestSearchCriteriaDto criteria);
}
