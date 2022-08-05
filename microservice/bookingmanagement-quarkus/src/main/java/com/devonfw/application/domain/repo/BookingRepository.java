package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.LongToIntFunction;


public interface BookingRepository extends JpaRepository<BookingEntity, Long>, BookingFragment {
}
