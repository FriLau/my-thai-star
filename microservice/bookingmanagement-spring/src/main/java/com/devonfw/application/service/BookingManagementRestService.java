package com.devonfw.application.service;

import com.devonfw.application.logic.BookingManagement;
import com.devonfw.application.service.model.BookingDto;
import com.devonfw.application.service.model.BookingSearchCriteriaDto;
import com.devonfw.application.service.model.InvitedGuestDto;
import com.devonfw.application.service.model.InvitedGuestSearchCriteriaDto;
import com.devonfw.application.service.model.TableDto;
import com.devonfw.application.service.model.TableSearchCriteriaDto;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.created;
import static javax.ws.rs.core.Response.status;

// TODO
// Remove Repository Fragment + Remove Rest Functions (/)
// Add native (/)
// Remove Tests (/)
// ReadMe
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestMapping("/booking-management/v1")
@RestController
public class BookingManagementRestService {

    @Autowired
    private final BookingManagement bookingManagement;

    BookingManagementRestService(BookingManagement bookingManagement)
    {
        this.bookingManagement = bookingManagement;
    }

    // Test to see all bookings
    @GetMapping("/booking")
    public List<BookingDto> getBookings()
    {
        return this.bookingManagement.findAllBookings();
    }

    // Test to see all invitedGuest
    @GetMapping("/invited-guest")
    public List<InvitedGuestDto> getInvitedGuests()
    {
        return this.bookingManagement.findAllInvitedGuests();
    }

    // Test to see all table
    @GetMapping("/table")
    public List<TableDto> getTables()
    {
        return this.bookingManagement.findAllTables();
    }

    /**
     * Function to get the Booking with the given id
     *
     * @param id the ID of the {@link BookingDto}
     * @return the {@link BookingDto}
     */
    @GetMapping("/booking/{id}")
    public BookingDto getBooking(@PathVariable("id") long id)
    {
        return this.bookingManagement.findBooking(id);
    }

    /**
     * Save the booking
     *
     * @param booking the {@link Response} to be saved
     * @return the recently created {@link Response}
     */
    @PostMapping("/booking")
    public Response saveBooking(@RequestBody BookingDto booking)
    {
        BookingDto bookingDto = this.bookingManagement.createBooking(booking);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(bookingDto.getId().toString())
                .build()
                .toUri();
        return created(uri).build();
    }

    /**
     * Delete the booking
     *
     * @param id ID of the {@link BookingDto} to be deleted
     */
    @DeleteMapping("/booking/{id}")
    public Response deleteBooking(@PathVariable("id") long id)
    {
        boolean result = this.bookingManagement.deleteBooking(id);
        if (result)
        {
            return status(Response.Status.NO_CONTENT.getStatusCode()).build();
        }
        else
        {
            return status(Response.Status.FORBIDDEN.getStatusCode()).build();
        }

    }

    /**
     * TODO - currently not working
     * Find the Bookings by the given searchCriteriaDto
     *
     * @param searchCriteriaDto the pagination and search criteria to be used for finding bookings.
     * @return the list of matching {@link BookingDto}s.
     */
    @PostMapping("/booking/search")
    public Page<BookingDto> findBookingsByPost(@RequestBody BookingSearchCriteriaDto searchCriteriaDto)
    {
        return this.bookingManagement.findBookingsByPost(searchCriteriaDto);
    }

    /**
     * Function to get the InvitedGuest with the given id
     *
     * @param id the ID of the {@link InvitedGuestDto}
     * @return the {@link InvitedGuestDto}
     */
    @GetMapping("/invited-guest/{id}")
    public InvitedGuestDto getInvitedGuest(@PathVariable("id") long id)
    {
        return this.bookingManagement.findInvitedGuest(id);
    }

    /**
     * Save the invitedGuest
     *
     * @param invitedGuest the {@link InvitedGuestDto} to be saved
     * @return the recently created {@link InvitedGuestDto}
     */
    @PostMapping("/invited-guest")
    public Response saveInvitedGuest(@RequestBody InvitedGuestDto invitedGuest)
    {
        InvitedGuestDto invitedGuestDto = this.bookingManagement.saveInvitedGuest(invitedGuest);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(invitedGuestDto.getId().toString())
                .build()
                .toUri();
        return created(uri).build();
    }

    /**
     * Delete the invitedGuest
     *
     * @param id ID of the {@link InvitedGuestDto} to be deleted
     */
    @DeleteMapping("/invited-guest/{id}")
    public Response deleteInvitedGuest(@PathVariable("id") long id)
    {
        if (this.bookingManagement.deleteInvitedGuest(id))
        {
            return status(Response.Status.NO_CONTENT.getStatusCode()).build();
        }
        else
        {
            return status(Response.Status.FORBIDDEN.getStatusCode()).build();
        }
    }

    /**
     * TODO - currently not working
     * Find the InvitedGuests with the given SearchCriteria
     *
     * @param searchCriteriaTo the pagination and search criteria to be used for finding invitedguests.
     * @return the list of matching {@link InvitedGuestDto}s.
     */
    @PostMapping("/invited-guest/search")
    public Page<InvitedGuestDto> findInvitedGuestsByPost(@RequestBody InvitedGuestSearchCriteriaDto searchCriteriaTo)
    {
        return this.bookingManagement.findInvitedGuestsByPost(searchCriteriaTo);
    }

    /**
     * Accept the invite of a guest
     *
     * @param guestToken the Token of the {@link InvitedGuestDto}
     * @return the {@link InvitedGuestDto}
     */
    @GetMapping("/invited-guest/accept/{token}")
    public InvitedGuestDto acceptInvite(@PathVariable("token") String guestToken)
    {
        return this.bookingManagement.acceptInvite(guestToken);
    }

    /**
     * Decline the invite of a guest
     *
     * @param guestToken the Token of the {@link InvitedGuestDto}
     * @return the {@link InvitedGuestDto}
     */
    @GetMapping("/invited-guest/decline/{token}")
    public InvitedGuestDto declineInvite(@PathVariable("token") String guestToken)
    {
        return this.bookingManagement.declineInvite(guestToken);
    }

    /**
     * Cancel the invite of a guest
     *
     * @param bookingToken the Token of the {@link BookingDto}
     */
    @GetMapping("/booking/cancel/{token}")
    public Response cancelBooking(@PathVariable("token") String bookingToken)
    {
        this.bookingManagement.cancelInvite(bookingToken);
        return status(Response.Status.NO_CONTENT.getStatusCode()).build();
    }

    /**
     * Function to get the Table with the given id
     *
     * @param id the ID of the {@link TableDto}
     * @return the {@link TableDto}
     */
    @GetMapping("/table/{id}")
    public TableDto getTable(@PathVariable("id") long id)
    {
        return this.bookingManagement.findTable(id);
    }

    /**
     * Save the Table
     *
     * @param table the {@link TableDto} to be saved
     * @return the recently created table
     */
    @PostMapping("/table")
    public Response saveTable(@RequestBody TableDto table)
    {
        TableDto tableDto = this.bookingManagement.saveTable(table);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(tableDto.getId().toString())
                .build()
                .toUri();
        return created(uri).build();
    }

    /**
     * Delete the table
     *
     * @param id ID of the {@link Response} to be deleted
     */
    @DeleteMapping("/table/{id}")
    public Response deleteTable(@PathVariable("id") long id)
    {
        if (this.bookingManagement.deleteTable(id))
        {
            return status(Response.Status.NO_CONTENT.getStatusCode()).build();
        }
        else
        {
            return status(Response.Status.FORBIDDEN.getStatusCode()).build();
        }

    }

    /**
     * TODO - currently not working
     * Find the Tables with the given SearchCriteria
     *
     * @param searchCriteriaTo the pagination and search criteria to be used for finding tables.
     * @return the list of matching {@link TableDto}s.
     */
    @PostMapping("/table/search")
    public Page<TableDto> findTablesByPost(@RequestBody TableSearchCriteriaDto searchCriteriaTo)
    {
        return this.bookingManagement.findTablesByPost(searchCriteriaTo);
    }


}
