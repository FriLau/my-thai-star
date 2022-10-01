package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    @Query("SELECT booking FROM BookingEntity booking" //
            + " WHERE booking.bookingToken = :bookingToken")
    BookingEntity getBookingByToken(@Param("bookingToken") String bookingToken);

    @Query("SELECT booking FROM BookingEntity booking" //
            + " WHERE booking.table.id = :tableId")
    List<BookingEntity> getBookingsByTableId(@Param("tableId") Long tableId);
}
