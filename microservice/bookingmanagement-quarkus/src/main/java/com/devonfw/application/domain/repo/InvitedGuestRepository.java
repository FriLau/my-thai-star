package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.InvitedGuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvitedGuestRepository extends JpaRepository<InvitedGuestEntity, Long>, InvitedGuestFragment {

    @Query("SELECT invitedGuest FROM InvitedGuestEntity invitedGuest" //
            + " WHERE invitedGuest.invitedGuestToken = :invitedGuestToken")
    InvitedGuestEntity getInvitedGuestByToken(@Param("invitedGuestToken") String invitedGuestToken);
}
