package com.devonfw.application.service.rest;

import com.devonfw.application.domain.model.BookingEntity;
import com.devonfw.application.domain.repo.BookingRepository;
import com.devonfw.application.domain.repo.InvitedGuestRepository;
import com.devonfw.application.domain.repo.TableRepository;
import com.devonfw.application.service.rest.mapper.BookingMapper;
import com.devonfw.application.service.rest.mapper.InvitedGuestMapper;
import com.devonfw.application.service.rest.mapper.TableMapper;
import com.devonfw.application.service.rest.model.BookingDto;
import com.devonfw.application.service.rest.model.BookingSearchCriteriaDto;
import com.devonfw.application.service.rest.model.InvitedGuestDto;
import com.devonfw.application.service.rest.model.InvitedGuestSearchCriteriaDto;
import com.devonfw.application.service.rest.model.TableDto;
import com.devonfw.application.service.rest.model.TableSearchCriteriaDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javax.ws.rs.core.Response.created;
import static javax.ws.rs.core.Response.status;

@Path("/bookingmanagement/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookingManagementRestService {

    private static final Logger LOG = LoggerFactory.getLogger(BookingManagementRestService.class);

    @Context
    UriInfo uriInfo;

    @Inject
    BookingRepository bookingRepo;

    @Inject
    BookingMapper bookingMapper;

    @Inject
    InvitedGuestRepository invitedGuestRepo;

    @Inject
    InvitedGuestMapper invitedGuestMapper;

    @Inject
    TableRepository tableRepo;

    @Inject
    TableMapper tableMapper;


    /**
     * Function to get the Booking
     *
     * @param id the ID of the {@link BookingDto}
     * @return the {@link BookingDto}
     */
    @GET
    @Path("/booking/{id}/")
    public BookingDto getBooking(@PathParam("id") long id)
    {
        Optional<BookingEntity> entity = this.bookingRepo.findById(id);
        return this.bookingMapper.mapTo(entity.get());
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
        BookingEntity entity = this.bookingRepo.save(this.bookingMapper.mapTo(booking));
        UriBuilder uriBuilder = this.uriInfo.getAbsolutePathBuilder().path(Long.toString(entity.getId()));
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
        this.bookingRepo.deleteById(id);
        LOG.info("Booking with Id" + id + "was deleted");
        return status(Response.Status.NO_CONTENT.getStatusCode()).build();
    }

    /**
     * TODO
     *
     * @param searchCriteriaTo the pagination and search criteria to be used for finding bookings.
     * @return the list of matching {@link BookingDto}s.
     */
    @Path("/booking/search")
    @POST
    public Page<BookingDto> findBookingsByPost(BookingSearchCriteriaDto searchCriteriaTo)
    {
        //TODO change variable names ???
        Page<BookingDto> pageListDto = null;
        Page<BookingEntity> bookingPage = this.bookingRepo.findBookings(searchCriteriaTo);
        List<BookingEntity> bookingList = bookingPage.getContent();
        List<BookingDto> ctos = new ArrayList<>(bookingList.size());
        for (BookingEntity booking: bookingPage)
        {
            BookingDto bookingDto = new BookingDto();
            bookingDto = this.bookingMapper.mapTo(booking);
            bookingDto.setInvitedGuests(this.invitedGuestMapper.mapList(booking.getInvitedGuests()));
            bookingDto.setTable(this.tableMapper.mapTo(booking.getTable()));
            ctos.add(bookingDto);
        }
        if (ctos.size() > 0)
        {
            Pageable pageResultTo = PageRequest.of(searchCriteriaTo.getPageable().getPageNumber(), ctos.size());
            pageListDto = new PageImpl<>(ctos, pageResultTo, pageResultTo.getPageSize());
        }
        return pageListDto;
    }

    /**
     * TODO
     *
     * @param id the ID of the {@link InvitedGuestDto}
     * @return the {@link InvitedGuestDto}
     */
    @GET
    @Path("/invitedguest/{id}/")
    public InvitedGuestDto getInvitedGuest(@PathParam("id") long id)
    {
        return null;
    }

    /**
     * TODO
     *
     * @param invitedGuest the {@link InvitedGuestDto} to be saved
     * @return the recently created {@link InvitedGuestDto}
     */
    @POST
    @Path("/invitedguest/")
    public Response saveInvitedGuest(InvitedGuestDto invitedGuest)
    {
        return null;
    }

    /**
     * TODO
     *
     * @param id ID of the {@link InvitedGuestDto} to be deleted
     */
    @DELETE
    @Path("/invitedguest/{id}/")
    public Response deleteInvitedGuest(@PathParam("id") long id)
    {
        return null;
    }

    /**
     * TODO
     *
     * @param searchCriteriaTo the pagination and search criteria to be used for finding invitedguests.
     * @return the list of matching {@link InvitedGuestDto}s.
     */
    @Path("/invitedguest/search")
    @POST
    public Page<InvitedGuestDto> findInvitedGuestsByPost(InvitedGuestSearchCriteriaDto searchCriteriaTo)
    {
        return null;
    }

    @Path("/invitedguest/accept/{token}")
    @GET
    public InvitedGuestDto acceptInvite(@PathParam("token") String guestToken)
    {
        return null;
    }

    @Path("/invitedguest/decline/{token}")
    @GET
    public InvitedGuestDto declineInvite(@PathParam("token") String guestToken)
    {
        return null;
    }

    @Path("/booking/cancel/{token}")
    @GET
    public void cancelInvite(@PathParam("token") String bookingToken)
    {

    }

    /**
     * TODO
     *
     * @param id the ID of the {@link TableDto}
     * @return the {@link TableDto}
     */
    @GET
    @Path("/table/{id}/")
    public TableDto getTable(@PathParam("id") long id)
    {
        return null;
    }

    /**
     * TODO
     *
     * @param table the {@link TableDto} to be saved
     * @return the recently created table
     */
    @POST
    @Path("/table/")
    public Response saveTable(TableDto table)
    {
        return null;
    }

    /**
     * TODO
     *
     * @param id ID of the {@link Response} to be deleted
     */
    @DELETE
    @Path("/table/{id}/")
    public Response deleteTable(@PathParam("id") long id)
    {
        return null;
    }

    /**
     * TODO
     *
     * @param searchCriteriaTo the pagination and search criteria to be used for finding tables.
     * @return the list of matching {@link TableDto}s.
     */
    @Path("/table/search")
    @POST
    public Page<TableDto> findTablesByPost(TableSearchCriteriaDto searchCriteriaTo)
    {
        return null;
    }


}
