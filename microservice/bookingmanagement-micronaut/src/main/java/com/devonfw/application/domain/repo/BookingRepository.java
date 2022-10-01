package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.BookingEntity;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository<BookingEntity, Long> {

    @Query("SELECT booking FROM BookingEntity booking" //
            + " WHERE booking.bookingToken = :token")
    BookingEntity getBookingByToken(String token);

    @Query("SELECT booking FROM BookingEntity booking" //
            + " WHERE booking.table.id = :tableId")
    List<BookingEntity> getBookingsByTableId(Long tableId);

//    @Query("SELECT booking FROM BookingEntity booking" //
//            + " WHERE booking.bookingToken = :token")
//    BookingEntity getBookingByToken(@Param("token") String token);
//
//    @Query("SELECT booking FROM BookingEntity booking" //
//            + " WHERE booking.table.id = :tableId")
//    List<BookingEntity> getBookingsByTableId(@Param("tableId") Long tableId);
}
