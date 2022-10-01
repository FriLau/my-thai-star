package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.InvitedGuestEntity;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface InvitedGuestRepository extends CrudRepository<InvitedGuestEntity, Long> {

    @Query("SELECT invitedGuest FROM InvitedGuestEntity invitedGuest" //
            + " WHERE invitedGuest.guestToken = :token")
    InvitedGuestEntity getInvitedGuestByToken(String token);
}
