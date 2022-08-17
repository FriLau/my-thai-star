package com.devonfw.application.logic;

import com.devonfw.application.domain.model.BookingEntity;
import com.devonfw.application.domain.model.InvitedGuestEntity;
import com.devonfw.application.domain.model.TableEntity;
import com.devonfw.application.domain.repo.BookingRepository;
import com.devonfw.application.domain.repo.InvitedGuestRepository;
import com.devonfw.application.domain.repo.TableRepository;
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

public class BookingManagementImpl implements BookingManagement{
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

    private int hoursLimit = 1;

//    _________________ Booking _________________

    @Override
    public List<BookingDto> findAllBookings() {

        return this.bookingMapper.mapList(getBookingDao().findAll());
    }

    @Override
    public BookingDto findBooking(Long id) {

        LOG.debug("Get Booking with id {} from database.", id);
        BookingEntity bookingEntity = getBookingDao().findById(id).get();
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
        BookingEntity bookingEntity = bookingRepo.getBookingByToken(token);
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
    public BookingDto updateBooking(BookingDto booking) {
        Objects.requireNonNull(booking, "Booking object cant be null");

        BookingEntity bookingEntity = saveBooking(this.bookingMapper.mapTo(booking));
        return this.bookingMapper.mapTo(bookingEntity);
    }

    @Override
    public BookingDto createBooking(BookingDto booking) {

        Objects.requireNonNull(booking, "Booking object cant be null");
        BookingEntity bookingEntity = this.bookingMapper.mapTo(booking);
        List<InvitedGuestEntity> invitedGuestList = new ArrayList<>();

        for (InvitedGuestDto invitedGuestDto : booking.getInvitedGuests())
        {
            invitedGuestList.add(this.invitedGuestMapper.mapTo(invitedGuestDto));
        }

        for (InvitedGuestEntity invitedGuest : invitedGuestList) {
            try {
                invitedGuest.setGuestToken(buildToken(invitedGuest.getEmail(), "GB_"));
            } catch (NoSuchAlgorithmException e) {
                LOG.debug("MD5 Algorithm not available at the enviroment");
            }
            //inviteGuest.setAccepted(false);
            invitedGuest.setBooking(bookingEntity);
        }
        bookingEntity.setInvitedGuests(invitedGuestList);


        try {
            bookingEntity.setBookingToken(buildToken(bookingEntity.getEmail(), "CB_"));
        } catch (NoSuchAlgorithmException e) {
            LOG.debug("MD5 Algorithm not available at the enviroment");
        }

        bookingEntity.setCreationDate(Instant.now());
        bookingEntity.setExpirationDate(bookingEntity.getBookingDate().minus(Duration.ofHours(1)));

        BookingEntity resultEntity = saveBooking(bookingEntity);
        LOG.info("Booking with id '{}' has been created.", resultEntity.getId());


        return this.bookingMapper.mapTo(resultEntity);
    }

    private BookingEntity saveBooking(BookingEntity bookingEntity) {
        Objects.requireNonNull(bookingEntity, "Booking object cant be null");

        BookingEntity resultEntity = getBookingDao().save(bookingEntity);

        for (InvitedGuestEntity invitedGuest : resultEntity.getInvitedGuests()) {

            invitedGuest.setBooking(bookingEntity);
            saveInvitedGuest(invitedGuest);
            LOG.info("Invitation with id '{}' has been saved.", invitedGuest.getId());
        }

        saveTable(resultEntity.getTable());

        LOG.debug("Table with id '{}' has been saved.", resultEntity.getTable().getId());

        LOG.debug("Booking with id '{}' has been saved.", resultEntity.getId());

        return resultEntity;
    }

    @Override
    public boolean deleteBooking(Long bookingId) {

        //Delete all Order of this Booking

        //TODO deleteBooking != cancelInvite

        getBookingDao().deleteById(bookingId);
        LOG.debug("The booking with id '{}' has been deleted.", bookingId);
        return true;

    }

    @Override
    public Page<BookingDto> findBookingsByPost(BookingSearchCriteriaDto searchCriteriaDto) {

        Page<BookingDto> bookingDtoList = null;
        Page<BookingEntity> bookingEntityList = getBookingDao().findBookings(searchCriteriaDto);
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
    public BookingRepository getBookingDao() {

        return this.bookingRepo;
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

        return this.invitedGuestMapper.mapList(getInvitedGuestDao().findAll());
    }

    @Override
    public InvitedGuestDto findInvitedGuest(Long id) {

        InvitedGuestEntity invitedGuestEntity = getInvitedGuestDao().findById(id).get();
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
        InvitedGuestEntity invitedGuestEntity = getInvitedGuestDao().getInvitedGuestByToken(token);
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
        invitedGuestEntity.setBooking(getBookingDao().findById(invitedGuest.getBookingId()).get());

        invitedGuestEntity = saveInvitedGuest(invitedGuestEntity);

        InvitedGuestDto result = this.invitedGuestMapper.mapTo(invitedGuestEntity);
        result.setBookingId(invitedGuestEntity.getBooking().getId());

        return result;
    }

    private InvitedGuestEntity saveInvitedGuest(InvitedGuestEntity invitedGuestEntity) {
        invitedGuestEntity = getInvitedGuestDao().save(invitedGuestEntity);
        LOG.info("invitedGuestEntity with id '{}' has been saved.", invitedGuestEntity.getId());

        return invitedGuestEntity;
    }

    @Override
    public boolean deleteInvitedGuest(Long invitedGuestId) {

        getInvitedGuestDao().deleteById(invitedGuestId);
        LOG.debug("The invitedGuest with id '{}' has been deleted.", invitedGuestId);
        return true;
    }

    @Override
    public Page<InvitedGuestDto> findInvitedGuestsByPost(InvitedGuestSearchCriteriaDto searchCriteriaDto) {

        Page<InvitedGuestDto> invitedGuestDtoList = null;
        Page<InvitedGuestEntity> invitedGuestEntityList = getInvitedGuestDao().findInvitedGuests(searchCriteriaDto);
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
    public InvitedGuestRepository getInvitedGuestDao() {

        return this.invitedGuestRepo;
    }

    @Override
    public InvitedGuestDto acceptInvite(String guestToken) {

        InvitedGuestDto invitedGuestDto = this.invitedGuestMapper.mapTo(this.invitedGuestRepo.getInvitedGuestByToken(guestToken));
        invitedGuestDto.setAccepted(true);
        saveInvitedGuest(invitedGuestDto);
        return invitedGuestDto;
    }

    @Override
    public InvitedGuestDto declineInvite(String guestToken) {

        InvitedGuestDto invitedGuestDto = this.invitedGuestMapper.mapTo(this.invitedGuestRepo.getInvitedGuestByToken(guestToken));
        invitedGuestDto.setAccepted(false);
        saveInvitedGuest(invitedGuestDto);
        return invitedGuestDto;
    }

    //TODO
    @Override
    public void cancelInvite(String bookingToken) {

//        Objects.requireNonNull(bookingToken, "bookingToken");
//        BookingCto bookingCto = findBookingByToken(bookingToken);
//
//        if (bookingCto != null) {
//            if (!cancelInviteAllowed(bookingCto.getBooking())) {
//                throw new CancelInviteNotAllowedException();
//            }
//            List<InvitedGuestEto> guestsEto = findInvitedGuestByBooking(bookingCto.getBooking().getId());
//            if (guestsEto != null) {
//                for (InvitedGuestEto guestEto : guestsEto) {
//                    deleteInvitedGuest(guestEto.getId());
//                    sendCancellationEmailToGuest(bookingCto.getBooking(), guestEto);
//                }
//            }
//            // delete booking and related orders
//            deleteBooking(bookingCto.getBooking().getId());
//            sendCancellationEmailToHost(bookingCto.getBooking());
//        }
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

        return this.tableMapper.mapList(getTableDao().findAll());
    }

    @Override
    public TableDto findTable(Long id) {

        TableEntity tableEntity = getTableDao().findById(id).get();
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
        TableEntity resultEntity = getTableDao().save(tableEntity);
        LOG.debug("Table with id '{}' has been created.", resultEntity.getId());

        return resultEntity;
    }

    @Override
    public boolean deleteTable(Long tableId) {

        getTableDao().deleteById(tableId);
        LOG.debug("The table with id '{}' has been deleted.", tableId);
        return true;
    }

    @Override
    public Page<TableDto> findTablesByPost(TableSearchCriteriaDto searchCriteriaDto) {

        Page<TableDto> tableDtoList = null;
        Page<TableEntity> tableEntityList = getTableDao().findTables(searchCriteriaDto);
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
    public TableRepository getTableDao() {

        return this.tableRepo;
    }
}
