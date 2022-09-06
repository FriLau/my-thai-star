package com.devonfw.application.service.rest;


import com.devonfw.application.logic.BookingManagement;

import com.devonfw.application.service.rest.model.BookingDto;
import com.devonfw.application.service.rest.model.BookingSearchCriteriaDto;
import com.devonfw.application.service.rest.model.InvitedGuestDto;
import com.devonfw.application.service.rest.model.InvitedGuestSearchCriteriaDto;
import com.devonfw.application.service.rest.model.TableDto;
import com.devonfw.application.service.rest.model.TableSearchCriteriaDto;

import org.springframework.data.domain.Page;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import java.util.List;

import static javax.ws.rs.core.Response.created;
import static javax.ws.rs.core.Response.status;

@Path("/booking-management/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookingManagementRestService {

    @Context
    UriInfo uriInfo;

    @Inject
    BookingManagement bookingManagement;

    // Test to see all bookings
    @GET
    @Path("/booking/")
    public List<BookingDto> getBookings()
    {
        return this.bookingManagement.findAllBookings();
    }

    // Test to see all invitedGuest
    @GET
    @Path("/invited-guest/")
    public List<InvitedGuestDto> getInvitedGuests()
    {
        return this.bookingManagement.findAllInvitedGuests();
    }

    // Test to see all table
    @GET
    @Path("/table/")
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
    @GET
    @Path("/booking/{id}/")
    public BookingDto getBooking(@PathParam("id") long id)
    {
        return this.bookingManagement.findBooking(id);
    }

    /**
     * Save the booking
     *
     * @param booking the {@link Response} to be saved
     * @return the recently created {@link Response}
     */
    @POST
    @Path("/booking/")
    public Response saveBooking(BookingDto booking)
    {
        BookingDto bookingDto = this.bookingManagement.createBooking(booking);
        UriBuilder uriBuilder = this.uriInfo.getAbsolutePathBuilder().path(Long.toString(bookingDto.getId()));
        return created(uriBuilder.build()).build();
    }

    /**
     * Delete the booking
     *
     * @param id ID of the {@link BookingDto} to be deleted
     */
    @DELETE
    @Path("/booking/{id}/")
    public Response deleteBooking(@PathParam("id") long id)
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
     * Find the Bookings by the given searchCriteriaDto
     *
     * @param searchCriteriaDto the pagination and search criteria to be used for finding bookings.
     * @return the list of matching {@link BookingDto}s.
     */
    @Path("/booking/search")
    @POST
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
    @GET
    @Path("/invited-guest/{id}/")
    public InvitedGuestDto getInvitedGuest(@PathParam("id") long id)
    {
        return this.bookingManagement.findInvitedGuest(id);
    }

    /**
     * Save the invitedGuest
     *
     * @param invitedGuest the {@link InvitedGuestDto} to be saved
     * @return the recently created {@link InvitedGuestDto}
     */
    @POST
    @Path("/invited-guest/")
    public Response saveInvitedGuest(InvitedGuestDto invitedGuest)
    {
        InvitedGuestDto invitedGuestDto = this.bookingManagement.saveInvitedGuest(invitedGuest);
        UriBuilder uriBuilder = this.uriInfo.getAbsolutePathBuilder().path(Long.toString(invitedGuestDto.getId()));
        return created(uriBuilder.build()).build();
    }

    /**
     * Delete the invitedGuest
     *
     * @param id ID of the {@link InvitedGuestDto} to be deleted
     */
    @DELETE
    @Path("/invited-guest/{id}/")
    public Response deleteInvitedGuest(@PathParam("id") long id)
    {
        this.bookingManagement.deleteInvitedGuest(id);
        return status(Response.Status.NO_CONTENT.getStatusCode()).build();
    }

    /**
     * Find the InvitedGuests with the given SearchCriteria
     *
     * @param searchCriteriaTo the pagination and search criteria to be used for finding invitedguests.
     * @return the list of matching {@link InvitedGuestDto}s.
     */
    @Path("/invited-guest/search")
    @POST
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
    @Path("/invited-guest/accept/{token}")
    @GET
    public InvitedGuestDto acceptInvite(@PathParam("token") String guestToken)
    {
        return this.bookingManagement.acceptInvite(guestToken);
    }

    /**
     * Decline the invite of a guest
     *
     * @param guestToken the Token of the {@link InvitedGuestDto}
     * @return the {@link InvitedGuestDto}
     */
    @Path("/invited-guest/decline/{token}")
    @GET
    public InvitedGuestDto declineInvite(@PathParam("token") String guestToken)
    {
        return this.bookingManagement.declineInvite(guestToken);
    }

    /**
     * Cancel the invite of a guest
     *
     * @param bookingToken the Token of the {@link BookingDto}
     */
    @Path("/booking/cancel/{token}")
    @GET
    public Response cancelBooking(@PathParam("token") String bookingToken)
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
    @GET
    @Path("/table/{id}/")
    public TableDto getTable(@PathParam("id") long id)
    {
        return this.bookingManagement.findTable(id);
    }

    /**
     * Save the Table
     *
     * @param table the {@link TableDto} to be saved
     * @return the recently created table
     */
    @POST
    @Path("/table/")
    public Response saveTable(TableDto table)
    {
        TableDto tableDto = this.bookingManagement.saveTable(table);
        UriBuilder uriBuilder = this.uriInfo.getAbsolutePathBuilder().path(Long.toString(tableDto.getId()));
        return created(uriBuilder.build()).build();
    }

    /**
     * Delete the table
     *
     * @param id ID of the {@link Response} to be deleted
     */
    @DELETE
    @Path("/table/{id}/")
    public Response deleteTable(@PathParam("id") long id)
    {
        this.bookingManagement.deleteTable(id);
        return status(Response.Status.NO_CONTENT.getStatusCode()).build();
    }

    /**
     * Find the Tables with the given SearchCriteria
     *
     * @param searchCriteriaTo the pagination and search criteria to be used for finding tables.
     * @return the list of matching {@link TableDto}s.
     */
    @Path("/table/search")
    @POST
    public Page<TableDto> findTablesByPost(TableSearchCriteriaDto searchCriteriaTo)
    {
        return this.bookingManagement.findTablesByPost(searchCriteriaTo);
    }


}
