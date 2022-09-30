package com.devonfw.application.logic;

import com.devonfw.application.service.rest.model.BookingDto;
import com.devonfw.application.service.rest.model.BookingSearchCriteriaDto;
import com.devonfw.application.service.rest.model.InvitedGuestDto;
import com.devonfw.application.service.rest.model.InvitedGuestSearchCriteriaDto;
import com.devonfw.application.service.rest.model.TableDto;
import com.devonfw.application.service.rest.model.TableSearchCriteriaDto;
import org.springframework.data.domain.Page;

import java.util.List;

//TODO
//@ApplicationScoped
public interface BookingManagement {

//    _________________ Booking _________________

    public List<BookingDto> findAllBookings();

    public BookingDto findBooking(Long id);

    public BookingDto findBookingByToken(String token);

    public BookingDto createBooking(BookingDto booking);

    public boolean deleteBooking(Long bookingId);

    public Page<BookingDto> findBookingsByPost(BookingSearchCriteriaDto searchCriteriaDto);

    //    _________________ InvitedGuest _________________

    public List<InvitedGuestDto> findAllInvitedGuests();

    public InvitedGuestDto findInvitedGuest(Long id);

    public InvitedGuestDto findInvitedGuestByToken(String token);

    public List<InvitedGuestDto> findInvitedGuestByBooking(Long bookingId);

    public InvitedGuestDto saveInvitedGuest(InvitedGuestDto invitedGuest);

    public boolean deleteInvitedGuest(Long invitedGuestId);

    public Page<InvitedGuestDto> findInvitedGuestsByPost(InvitedGuestSearchCriteriaDto searchCriteriaDto);

    public InvitedGuestDto acceptInvite(String guestToken);

    public InvitedGuestDto declineInvite(String guestToken);

    public void cancelInvite(String bookingToken);



    //    _________________ Table _________________

    public List<TableDto> findAllTables();

    public TableDto findTable(Long id);

    public TableDto saveTable(TableDto table);

    public boolean deleteTable(Long tableId);

    public Page<TableDto> findTablesByPost(TableSearchCriteriaDto searchCriteriaDto);


}
