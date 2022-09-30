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
import java.util.List;

import static io.micronaut.http.HttpStatus.FORBIDDEN;
import static io.micronaut.http.HttpStatus.NO_CONTENT;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Controller("/booking-management/v1")
public class BookingManagementRestService {

    //TODO
//    @Context
//    UriInfo uriInfo;

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
//        UriBuilder uriBuilder = this.uriInfo.getAbsolutePathBuilder().path(Long.toString(bookingDto.getId()));
//        return created(uriBuilder.build()).build();
        //TODO
        return HttpResponse.ok(bookingDto).contentType(MediaType.APPLICATION_JSON);
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
//            return status(Response.Status.NO_CONTENT.getStatusCode()).build();
        }
        else
        {
            return HttpResponse.ok().status(FORBIDDEN);
//            return status(Response.Status.FORBIDDEN.getStatusCode()).build();
        }

    }

    /**
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
//        UriBuilder uriBuilder = this.uriInfo.getAbsolutePathBuilder().path(Long.toString(invitedGuestDto.getId()));
//        return created(uriBuilder.build()).build();
        //TODO
        return HttpResponse.ok(invitedGuestDto).contentType(MediaType.APPLICATION_JSON);
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
//            return status(Response.Status.NO_CONTENT.getStatusCode()).build();
        }
        else
        {
            return HttpResponse.ok().status(FORBIDDEN);
//            return status(Response.Status.FORBIDDEN.getStatusCode()).build();
        }
    }

    /**
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
//        return status(Response.Status.NO_CONTENT.getStatusCode()).build();
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
//        UriBuilder uriBuilder = this.uriInfo.getAbsolutePathBuilder().path(Long.toString(tableDto.getId()));
//        return created(uriBuilder.build()).build();
        //TODO
        return HttpResponse.ok(tableDto).contentType(MediaType.APPLICATION_JSON);
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
//            return status(Response.Status.NO_CONTENT.getStatusCode()).build();
        }
        else
        {
            return HttpResponse.ok().status(FORBIDDEN);
//            return status(Response.Status.FORBIDDEN.getStatusCode()).build();
        }
    }

    /**
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
