package com.devonfw.application.service.rest;


import com.devonfw.application.logic.BookingManagement;
import com.devonfw.application.service.rest.model.BookingDto;
import com.devonfw.application.service.rest.model.BookingSearchCriteriaDto;
import com.devonfw.application.service.rest.model.InvitedGuestDto;
import com.devonfw.application.service.rest.model.InvitedGuestSearchCriteriaDto;
import com.devonfw.application.service.rest.model.TableDto;
import com.devonfw.application.service.rest.model.TableSearchCriteriaDto;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.uri.UriBuilder;
import org.springframework.data.domain.Page;

import javax.inject.Inject;
import java.net.URI;
import java.util.List;

import static io.micronaut.http.HttpStatus.FORBIDDEN;
import static io.micronaut.http.HttpStatus.NO_CONTENT;

// TODO
// Json Instant conversion bug
// Repository Fragment Bug
// ReadMe
// Docker Image
// native Image
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Controller(value = "/booking-management/v1")
public class BookingManagementRestService {

    @Inject
    BookingManagement bookingManagement;

    // Test to see all bookings
    @Get("/booking")
    public List<BookingDto> getBookings()
    {
        return this.bookingManagement.findAllBookings();
    }

    // Test to see all invitedGuest
    @Get("/invited-guest/")
    public List<InvitedGuestDto> getInvitedGuests()
    {
        return this.bookingManagement.findAllInvitedGuests();
    }

    // Test to see all table
    @Get("/table/")
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
    @Get("/booking/{id}/")
    public BookingDto getBooking(@PathVariable("id") long id)
    {
        return this.bookingManagement.findBooking(id);
    }

    /**
     * Save the booking
     *
     * @param booking the {@link HttpResponse} to be saved
     * @return the recently created {@link HttpResponse}
     */
    @Post("/booking/")
    public HttpResponse saveBooking(BookingDto booking)
    {
        BookingDto bookingDto = this.bookingManagement.createBooking(booking);
//        URI uri = UriBuilder.of("/booking-management/v1" + "/booking/" + bookingDto.getId()).build();
        URI uri = URI.create("/booking-management/v1" + "/booking/" + bookingDto.getId());
        return HttpResponse.redirect(uri);
    }

    /**
     * Delete the booking
     *
     * @param id ID of the {@link BookingDto} to be deleted
     */
    @Delete("/booking/{id}/")
    public HttpResponse deleteBooking(@PathVariable("id") long id)
    {
        boolean result = this.bookingManagement.deleteBooking(id);
        if (result)
        {
            return HttpResponse.ok().status(NO_CONTENT);
        }
        else
        {
            return HttpResponse.ok().status(FORBIDDEN);
        }

    }

    /**
     * TODO - currently not working
     * Find the Bookings by the given searchCriteriaDto
     *
     * @param searchCriteriaDto the pagination and search criteria to be used for finding bookings.
     * @return the list of matching {@link BookingDto}s.
     */
    @Post("/booking/search")
    public Page<BookingDto> findBookingsByPost(BookingSearchCriteriaDto searchCriteriaDto)
    {
        return this.bookingManagement.findBookingsByPost(searchCriteriaDto);
    }

    /**
     * Function to get the InvitedGuest with the given id
     *
     * @param id the ID of the {@link InvitedGuestDto}
     * @return the {@link InvitedGuestDto}
     */
    @Get("/invited-guest/{id}/")
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
    @Post("/invited-guest/")
    public HttpResponse saveInvitedGuest(InvitedGuestDto invitedGuest)
    {
        InvitedGuestDto invitedGuestDto = this.bookingManagement.saveInvitedGuest(invitedGuest);
//        URI uri = UriBuilder.of("/booking-management/v1" + "/invited-guest/" + invitedGuestDto.getId()).build();
        URI uri = URI.create("/booking-management/v1" + "/invited-guest/" + invitedGuestDto.getId());
        return HttpResponse.redirect(uri);
    }

    /**
     * Delete the invitedGuest
     *
     * @param id ID of the {@link InvitedGuestDto} to be deleted
     */
    @Delete("/invited-guest/{id}/")
    public HttpResponse deleteInvitedGuest(@PathVariable("id") long id)
    {
        if (this.bookingManagement.deleteInvitedGuest(id))
        {
            return HttpResponse.ok().status(NO_CONTENT);
        }
        else
        {
            return HttpResponse.ok().status(FORBIDDEN);
        }
    }

    /**
     * TODO - currently not working
     * Find the InvitedGuests with the given SearchCriteria
     *
     * @param searchCriteriaTo the pagination and search criteria to be used for finding invitedGuests.
     * @return the list of matching {@link InvitedGuestDto}s.
     */
    @Post("/invited-guest/search")
    public Page<InvitedGuestDto> findInvitedGuestsByPost(InvitedGuestSearchCriteriaDto searchCriteriaTo)
    {
        return this.bookingManagement.findInvitedGuestsByPost(searchCriteriaTo);
    }

    /**
     * Accept the invite of a guest
     *
     * @param guestToken the Token of the {@link InvitedGuestDto}
     * @return the {@link InvitedGuestDto}
     */
    @Get("/invited-guest/accept/{token}")
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
    @Get("/invited-guest/decline/{token}")
    public InvitedGuestDto declineInvite(@PathVariable("token") String guestToken)
    {
        return this.bookingManagement.declineInvite(guestToken);
    }

    /**
     * Cancel the invite of a guest
     *
     * @param bookingToken the Token of the {@link BookingDto}
     */
    @Get("/booking/cancel/{token}")
    public HttpResponse cancelBooking(@PathVariable("token") String bookingToken)
    {
        this.bookingManagement.cancelInvite(bookingToken);
        return HttpResponse.ok().status(NO_CONTENT);
    }

    /**
     * Function to get the Table with the given id
     *
     * @param id the ID of the {@link TableDto}
     * @return the {@link TableDto}
     */
    @Get("/table/{id}/")
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
    @Post("/table/")
    public HttpResponse saveTable(TableDto table)
    {
        TableDto tableDto = this.bookingManagement.saveTable(table);
//        URI uri = UriBuilder.of("/booking-management/v1" + "/table/" + tableDto.getId()).build();
        URI uri = URI.create("/booking-management/v1" + "/table/" + tableDto.getId());
        return HttpResponse.redirect(uri);
    }

    /**
     * Delete the table
     *
     * @param id ID of the {@link HttpResponse} to be deleted
     */
    @Delete("/table/{id}/")
    public HttpResponse deleteTable(@PathVariable("id") long id)
    {
        if (this.bookingManagement.deleteTable(id))
        {
            return HttpResponse.ok().status(NO_CONTENT);
        }
        else
        {
            return HttpResponse.ok().status(FORBIDDEN);
        }
    }

    /**
     * TODO - currently not working
     * Find the Tables with the given SearchCriteria
     *
     * @param searchCriteriaTo the pagination and search criteria to be used for finding tables.
     * @return the list of matching {@link TableDto}s.
     */
    @Get("/table/search")
    public Page<TableDto> findTablesByPost(TableSearchCriteriaDto searchCriteriaTo)
    {
        return this.bookingManagement.findTablesByPost(searchCriteriaTo);
    }


}
