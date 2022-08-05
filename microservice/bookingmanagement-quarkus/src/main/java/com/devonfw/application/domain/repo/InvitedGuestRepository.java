package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.InvitedGuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitedGuestRepository extends JpaRepository<InvitedGuestEntity, Long>, InvitedGuestFragment {
}
