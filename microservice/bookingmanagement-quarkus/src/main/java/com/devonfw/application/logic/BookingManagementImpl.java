package com.devonfw.application.logic;

import com.devonfw.application.domain.model.BookingEntity;
import com.devonfw.application.domain.model.InvitedGuestEntity;
import com.devonfw.application.domain.model.TableEntity;
import com.devonfw.application.domain.repo.BookingRepository;
import com.devonfw.application.domain.repo.InvitedGuestRepository;
import com.devonfw.application.domain.repo.TableRepository;
import com.devonfw.application.logic.exception.CancelInviteNotAllowedException;
import com.devonfw.application.service.rest.BookingManagementRestService;
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

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class BookingManagementImpl implements BookingManagement{
    private static final Logger LOG = LoggerFactory.getLogger(BookingManagementRestService.class);

    @Context
    UriInfo uriInfo;

    @Inject
    BookingRepository bookingRepository;

    @Inject
    BookingMapper bookingMapper;

    @Inject
    InvitedGuestRepository invitedGuestRepository;

    @Inject
    InvitedGuestMapper invitedGuestMapper;

    @Inject
    TableRepository tableRepository;

    @Inject
    TableMapper tableMapper;

    private int hoursLimit = 1;

//    _________________ Booking _________________

    @Override
    public List<BookingDto> findAllBookings() {
        var test = getBookingRepository().findAll();
        return this.bookingMapper.mapList(getBookingRepository().findAll());
    }

    @Override
    public BookingDto findBooking(Long id) {

        LOG.debug("Get Booking with id {} from database.", id);
        BookingEntity bookingEntity = getBookingRepository().findById(id).get();
        BookingDto bookingDto = null;
        if (bookingEntity != null)
        {
            bookingDto = this.bookingMapper.mapTo(bookingEntity);
            bookingDto.setTable(this.tableMapper.mapTo(bookingEntity.getTable()));
            bookingDto.setInvitedGuests(this.invitedGuestMapper.mapList(bookingEntity.getInvitedGuests()));
        }
        return bookingDto;
    }

    @Override
    public BookingDto findBookingByToken(String token) {

        LOG.debug("Get Booking with token {} from database.", token);
        BookingEntity bookingEntity = bookingRepository.getBookingByToken(token);
        BookingDto bookingDto = null;
        if (bookingEntity != null)
        {
            bookingDto = this.bookingMapper.mapTo(bookingEntity);
            bookingDto.setTable(this.tableMapper.mapTo(bookingEntity.getTable()));
            bookingDto.setInvitedGuests(this.invitedGuestMapper.mapList(bookingEntity.getInvitedGuests()));
        }
        return bookingDto;
    }

    @Override
    public BookingDto createBooking(BookingDto booking) {
        Objects.requireNonNull(booking, "Booking object cant be null");
        Objects.requireNonNull(booking.getTable(), "Booking need a Table to be created");

        Optional<TableEntity> tableEntity = this.tableRepository.findById(booking.getTable().getId());
        Objects.requireNonNull(tableEntity, "Table for Booking not found in database.");
        if (booking.getAssistants() < 1 )
        {
            throw new RuntimeException("Assistants must be greater than 0.");
        }

        List<InvitedGuestEntity> invitedGuestList = new ArrayList<>();
        List<InvitedGuestDto> invitedGuestDtoList = booking.getInvitedGuests();

        BookingEntity bookingEntity = this.bookingMapper.mapTo(booking);
        bookingEntity.setCanceled(false);
        bookingEntity.setTable(tableEntity.get());
        bookingEntity.setInvitedGuests(invitedGuestList);
        bookingEntity.setCreationDate(Instant.now());

        try {
            bookingEntity.setBookingToken(buildToken(bookingEntity.getEmail(), "CB_"));
        } catch (NoSuchAlgorithmException e) {
            LOG.debug("MD5 Algorithm not available at the environment");
        }

        BookingEntity resultEntity = this.bookingRepository.save(bookingEntity);

        if (booking.getInvitedGuests() != null)
        {
            for (InvitedGuestDto invitedGuestDto : invitedGuestDtoList)
            {
                InvitedGuestEntity invitedGuestEntity = this.invitedGuestMapper.mapTo(invitedGuestDto);
                invitedGuestList.add(this.invitedGuestMapper.mapTo(invitedGuestDto));

                invitedGuestEntity.setBooking(resultEntity);
                invitedGuestEntity.setAccepted(false);

                try {
                    invitedGuestEntity.setGuestToken(buildToken(invitedGuestEntity.getEmail(), "GB_"));
                } catch (NoSuchAlgorithmException e) {
                    LOG.debug("MD5 Algorithm not available at the environment");
                }

                invitedGuestList.add(getInvitedGuestRepository().save(invitedGuestEntity));

            }
        }

//        Objects.requireNonNull(booking, "Booking object cant be null");
//        BookingEntity bookingEntity = this.bookingMapper.mapTo(booking);
//        List<InvitedGuestEntity> invitedGuestList = new ArrayList<>();
//
//        // Invited Guest
//        if (booking.getInvitedGuests() != null)
//        {
//            for (InvitedGuestDto invitedGuestDto : booking.getInvitedGuests())
//            {
//                InvitedGuestEntity invitedGuestEntity = this.invitedGuestMapper.mapTo(invitedGuestDto);
//                invitedGuestList.add(this.invitedGuestMapper.mapTo(invitedGuestDto));
//
//                invitedGuestEntity.setAccepted(false);
//                //invitedGuestEntity.setBooking(bookingEntity);
//
//                try {
//                    invitedGuestEntity.setGuestToken(buildToken(invitedGuestEntity.getEmail(), "GB_"));
//                } catch (NoSuchAlgorithmException e) {
//                    LOG.debug("MD5 Algorithm not available at the environment");
//                }
//
//            }
//        }
//
//        bookingEntity.setInvitedGuests(invitedGuestList);
//
//        try {
//            bookingEntity.setBookingToken(buildToken(bookingEntity.getEmail(), "CB_"));
//        } catch (NoSuchAlgorithmException e) {
//            LOG.debug("MD5 Algorithm not available at the enviroment");
//        }
//
//        bookingEntity.setCreationDate(Instant.now());
//        bookingEntity.setExpirationDate(bookingEntity.getBookingDate().minus(Duration.ofHours(1)));
//
//        BookingEntity resultEntity = saveBooking(bookingEntity);
//        LOG.info("Booking with id '{}' has been created.", resultEntity.getId());
//
//
//        return this.bookingMapper.mapTo(resultEntity);


        return this.bookingMapper.mapTo(resultEntity);
    }

    private BookingEntity saveBooking(BookingEntity bookingEntity) {

        Objects.requireNonNull(bookingEntity, "Booking object cant be null");

        BookingEntity resultEntity = bookingEntity;

        Optional<TableEntity> tableEntity = this.tableRepository.findById(bookingEntity.getTable().getId());
        Objects.requireNonNull(tableEntity, "Table for Booking not valid.");

        resultEntity.setTable(tableEntity.get());

        List<InvitedGuestEntity> invitedGuestEntityArrayList = bookingEntity.getInvitedGuests();
        resultEntity.setInvitedGuests(new ArrayList<InvitedGuestEntity>());
        resultEntity = getBookingRepository().save(resultEntity);
        LOG.debug("Booking with id '{}' has been saved.", resultEntity.getId());

        for (InvitedGuestEntity invitedGuest : invitedGuestEntityArrayList) {

            invitedGuest.setBooking(resultEntity);
            resultEntity.getInvitedGuests().add(saveInvitedGuest(invitedGuest));
            LOG.info("Invitation with id '{}' has been saved.", invitedGuest.getId());
        }

        resultEntity = getBookingRepository().save(resultEntity);
        LOG.debug("Booking with id '{}' has been saved.", resultEntity.getId());

        return resultEntity;
    }

    @Override
    public boolean deleteBooking(Long bookingId) {

        //Delete all Order of this Booking

        if (!getBookingRepository().findById(bookingId).get().getInvitedGuests().isEmpty())
        {
            LOG.debug("Booking cant be deleted. InvitedGuest are still present. Cancel the Booking.");
            return false;
        }

        getBookingRepository().deleteById(bookingId);
        LOG.debug("The booking with id '{}' has been deleted.", bookingId);
        return true;

    }

    @Override
    public Page<BookingDto> findBookingsByPost(BookingSearchCriteriaDto searchCriteriaDto) {

        Page<BookingDto> bookingDtoList = null;
        Page<BookingEntity> bookingEntityList = getBookingRepository().findBookings(searchCriteriaDto);
        List<BookingDto> ctos = new ArrayList<>(bookingEntityList.getContent().size());
        for (BookingEntity booking: bookingEntityList)
        {
            BookingDto bookingDto = this.bookingMapper.mapTo(booking);
            bookingDto.setInvitedGuests(this.invitedGuestMapper.mapList(booking.getInvitedGuests()));
            bookingDto.setTable(this.tableMapper.mapTo(booking.getTable()));
            ctos.add(bookingDto);
        }
        if (ctos.size() > 0)
        {
            Pageable pageResult = PageRequest.of(searchCriteriaDto.getPageable().getPageNumber(), ctos.size());
            bookingDtoList = new PageImpl<>(ctos, pageResult, pageResult.getPageSize());
        }
        return bookingDtoList;
    }

    /**
     * Returns the field 'bookingDao'.
     *
     * @return the {@link BookingRepository} instance.
     */
    public BookingRepository getBookingRepository() {

        return this.bookingRepository;
    }

    public String buildToken(String email, String type) throws NoSuchAlgorithmException {

        Instant now = Instant.now();
        LocalDateTime ldt1 = LocalDateTime.ofInstant(now, ZoneId.systemDefault());
        String date = String.format("%04d", ldt1.getYear()) + String.format("%02d", ldt1.getMonthValue())
                + String.format("%02d", ldt1.getDayOfMonth()) + "_";

        String time = String.format("%02d", ldt1.getHour()) + String.format("%02d", ldt1.getMinute())
                + String.format("%02d", ldt1.getSecond());

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update((email + date + time).getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return type + date + sb;
    }

    //    _________________ InvitedGuest _________________

    @Override
    public List<InvitedGuestDto> findAllInvitedGuests() {

        return this.invitedGuestMapper.mapList(getInvitedGuestRepository().findAll());
    }

    @Override
    public InvitedGuestDto findInvitedGuest(Long id) {

        InvitedGuestEntity invitedGuestEntity = getInvitedGuestRepository().findById(id).get();
        InvitedGuestDto invitedGuestDto = null;
        if (invitedGuestEntity != null)
        {
            invitedGuestDto = this.invitedGuestMapper.mapTo(invitedGuestEntity);
            invitedGuestDto.setBookingId((invitedGuestEntity.getBooking().getId()));
        }
        LOG.debug("Get InvitedGuest with id {} from database.", id);
        return invitedGuestDto;

    }

    @Override
    public InvitedGuestDto findInvitedGuestByToken(String token) {

        LOG.debug("Get InvitedGuest with token {} from database.", token);
        InvitedGuestEntity invitedGuestEntity = getInvitedGuestRepository().getInvitedGuestByToken(token);
        InvitedGuestDto invitedGuestDto = null;
        if (invitedGuestEntity != null)
        {
            invitedGuestDto = this.invitedGuestMapper.mapTo(invitedGuestEntity);
            invitedGuestDto.setBookingId((invitedGuestEntity.getBooking().getId()));
        }
        LOG.debug("Get InvitedGuest with token {} from database.", token);
        return invitedGuestDto;
    }

    @Override
    public List<InvitedGuestDto> findInvitedGuestByBooking(Long bookingId) {

        BookingDto bookingDto = findBooking(bookingId);
        return bookingDto.getInvitedGuests();
    }

    @Override
    public InvitedGuestDto saveInvitedGuest(InvitedGuestDto invitedGuest) {
        Objects.requireNonNull(invitedGuest, "InvitedGuest object cant be null");

        InvitedGuestEntity invitedGuestEntity = this.invitedGuestMapper.mapTo(invitedGuest);
        invitedGuestEntity.setBooking(getBookingRepository().findById(invitedGuest.getBookingId()).get());

        invitedGuestEntity = saveInvitedGuest(invitedGuestEntity);

        InvitedGuestDto result = this.invitedGuestMapper.mapTo(invitedGuestEntity);
        result.setBookingId(invitedGuestEntity.getBooking().getId());

        return result;
    }

    private InvitedGuestEntity saveInvitedGuest(InvitedGuestEntity invitedGuestEntity) {
        invitedGuestEntity = getInvitedGuestRepository().save(invitedGuestEntity);
        LOG.info("invitedGuestEntity with id '{}' has been saved.", invitedGuestEntity.getId());

        return invitedGuestEntity;
    }

    @Override
    public boolean deleteInvitedGuest(Long invitedGuestId) {

        getInvitedGuestRepository().deleteById(invitedGuestId);
        LOG.debug("The invitedGuest with id '{}' has been deleted.", invitedGuestId);
        return true;
    }

    @Override
    public Page<InvitedGuestDto> findInvitedGuestsByPost(InvitedGuestSearchCriteriaDto searchCriteriaDto) {

        Page<InvitedGuestDto> invitedGuestDtoList = null;
        Page<InvitedGuestEntity> invitedGuestEntityList = getInvitedGuestRepository().findInvitedGuests(searchCriteriaDto);
        List<InvitedGuestDto> ctos = new ArrayList<>(invitedGuestEntityList.getContent().size());
        for (InvitedGuestEntity invitedGuest: invitedGuestEntityList)
        {
            InvitedGuestDto invitedGuestDto = this.invitedGuestMapper.mapTo(invitedGuest);
            invitedGuestDto.setBookingId((invitedGuest.getBooking().getId()));
            ctos.add(invitedGuestDto);
        }
        if (ctos.size() > 0)
        {
            Pageable pageResult = PageRequest.of(searchCriteriaDto.getPageable().getPageNumber(), ctos.size());
            invitedGuestDtoList = new PageImpl<>(ctos, pageResult, pageResult.getPageSize());
        }
        return invitedGuestDtoList;
    }

    /**
     * Returns the field 'invitedGuestDao'.
     *
     * @return the {@link InvitedGuestRepository} instance.
     */
    public InvitedGuestRepository getInvitedGuestRepository() {

        return this.invitedGuestRepository;
    }

    @Override
    public InvitedGuestDto acceptInvite(String guestToken) {

        InvitedGuestDto invitedGuestDto = this.invitedGuestMapper.mapTo(this.invitedGuestRepository.getInvitedGuestByToken(guestToken));
        invitedGuestDto.setAccepted(true);
        saveInvitedGuest(invitedGuestDto);
        return invitedGuestDto;
    }

    @Override
    public InvitedGuestDto declineInvite(String guestToken) {

        InvitedGuestDto invitedGuestDto = this.invitedGuestMapper.mapTo(this.invitedGuestRepository.getInvitedGuestByToken(guestToken));
        invitedGuestDto.setAccepted(false);
        saveInvitedGuest(invitedGuestDto);
        return invitedGuestDto;
    }

    @Override
    public void cancelInvite(String bookingToken) {

        Objects.requireNonNull(bookingToken, "bookingToken");
        BookingDto bookingDto = findBookingByToken(bookingToken);

        if (bookingDto != null) {
            if (!cancelInviteAllowed(bookingDto)) {
                throw new CancelInviteNotAllowedException();
            }
            // Cancel all invites
             List<InvitedGuestDto> invitedGuestsDto = findInvitedGuestByBooking(bookingDto.getId());
            if (invitedGuestsDto != null) {
                for (InvitedGuestDto invitiedGuest : invitedGuestsDto) {
                    deleteInvitedGuest(invitiedGuest.getId());
                    //sendCancellationEmailToGuest(bookingCto.getBooking(), guestEto);
                }
            }
            // delete booking and related orders
            deleteBooking(bookingDto.getId());
            //sendCancellationEmailToHost(bookingCto.getBooking());

        }
    }

    private boolean cancelInviteAllowed(BookingDto booking) {

        Long bookingTimeMillis = booking.getBookingDate().toEpochMilli();
        Long cancellationLimit = bookingTimeMillis - (3600000 * this.hoursLimit);
        Long now = Instant.now().toEpochMilli();

        return (now > cancellationLimit) ? false : true;
    }


    //    _________________ Table _________________

    @Override
    public List<TableDto> findAllTables() {

        return this.tableMapper.mapList(getTableRepository().findAll());
    }

    @Override
    public TableDto findTable(Long id) {

        TableEntity tableEntity = getTableRepository().findById(id).get();
        LOG.debug("Get Table with id {} from database.", id);
        return this.tableMapper.mapTo(tableEntity);
    }

    @Override
    public TableDto saveTable(TableDto table) {

        Objects.requireNonNull(table, "Table object cant be null");
        TableEntity tableEntity = this.tableMapper.mapTo(table);

        tableEntity = saveTable(tableEntity);

        return this.tableMapper.mapTo(tableEntity);
    }

    private TableEntity saveTable(TableEntity tableEntity)
    {
        TableEntity resultEntity = getTableRepository().save(tableEntity);
        LOG.debug("Table with id '{}' has been created.", resultEntity.getId());

        return resultEntity;
    }

    @Override
    public boolean deleteTable(Long tableId) {

        if (!getBookingRepository().getBookingsByTableId(tableId).isEmpty())
        {
            LOG.debug("Table cant be deleted. Bookings are still present. First delete the Bookings.");
            return false;
        }

        getTableRepository().deleteById(tableId);
        LOG.debug("The Table with id '{}' has been deleted.", tableId);
        return true;
    }

    @Override
    public Page<TableDto> findTablesByPost(TableSearchCriteriaDto searchCriteriaDto) {

        Page<TableDto> tableDtoList = null;
        Page<TableEntity> tableEntityList = getTableRepository().findTables(searchCriteriaDto);
        List<TableDto> ctos = this.tableMapper.mapList(tableEntityList.getContent());
        if (ctos.size() > 0)
        {
            Pageable pageResult = PageRequest.of(searchCriteriaDto.getPageable().getPageNumber(), ctos.size());
            tableDtoList = new PageImpl<>(ctos, pageResult, pageResult.getPageSize());
        }
        return tableDtoList;
    }

    /**
     * Returns the field 'tableDao'.
     *
     * @return the {@link TableRepository} instance.
     */
    public TableRepository getTableRepository() {

        return this.tableRepository;
    }
}
