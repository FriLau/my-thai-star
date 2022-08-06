package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity, Long>, BookingFragment {
}
