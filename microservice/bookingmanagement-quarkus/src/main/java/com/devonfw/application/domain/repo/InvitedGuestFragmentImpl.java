package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.BookingEntity;
import com.devonfw.application.domain.model.InvitedGuestEntity;
import com.devonfw.application.service.rest.model.BookingSearchCriteriaDto;
import com.devonfw.application.service.rest.model.InvitedGuestSearchCriteriaDto;
import org.springframework.data.domain.Page;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class InvitedGuestFragmentImpl implements InvitedGuestFragment{

    @Inject
    EntityManager entityManager;

    @Override
    public Page<InvitedGuestEntity> findInvitedGuests(InvitedGuestSearchCriteriaDto criteria)
    {
    // TODO
        return null;

    }
}
