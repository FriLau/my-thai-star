package com.devonfw.application;


import com.devonfw.application.domain.model.BookingType;
import com.devonfw.application.service.rest.model.BookingDto;
import com.devonfw.application.service.rest.model.InvitedGuestDto;
import com.devonfw.application.service.rest.model.TableDto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingManagementRestTest {

    static Long tableId = 0L;
    static Long invitedGuestId = 0L;
    static Long bookingId = 0L;

    @Test
    @Order(1)
    void saveTable()
    {
        TableDto tableDto = TableDto.builder().seatsNumber(3).build();

        Response response = given().when().body(tableDto).contentType(MediaType.APPLICATION_JSON)
                .post("/booking-management/v1/table/").then().log().all()
                .statusCode(CREATED.getStatusCode()).extract().response();
        String url = response.header("Location");
        response = given().when().contentType(MediaType.APPLICATION_JSON).get(url).then()
                .log().all().statusCode(OK.getStatusCode()).extract().response();


        tableId = response.body().jsonPath().getLong("id");

        assertEquals(tableDto.getSeatsNumber().intValue(), response.body().jsonPath().getInt("seatsNumber"));
    }

    @Test
    @Order(2)
    void getTable()
    {
        Response response = given().when().contentType(MediaType.APPLICATION_JSON).get("/booking-management/v1/table/" + tableId + "/").then()
                .log().all().statusCode(OK.getStatusCode()).extract().response();

        assertEquals(tableId, response.body().jsonPath().getLong("id"));
    }

    @Test
    @Order(3)
    void saveInvitedGuest()
    {
        InvitedGuestDto invitedGuestDto = InvitedGuestDto.builder()
                .email("guest@email.com")
                .accepted(false)
                .modificationDate((Instant.now()))
                .bookingId(1L)
                .build();

        Response response = given().when().body(invitedGuestDto).contentType(MediaType.APPLICATION_JSON)
                .post("/booking-management/v1/invited-guest/").then().log().all()
                .statusCode(CREATED.getStatusCode()).extract().response();
        String url = response.header("Location");
        response = given().when().contentType(MediaType.APPLICATION_JSON).get(url).then()
                .log().all().statusCode(OK.getStatusCode()).extract().response();

        invitedGuestId = response.body().jsonPath().getLong("id");

        assertEquals(invitedGuestDto.getEmail(), response.body().jsonPath().getString("email"));
        assertEquals(invitedGuestDto.getAccepted(), response.body().jsonPath().getBoolean("accepted"));
        var test = response.body().jsonPath().getLong("bookingId");
        assertEquals(invitedGuestDto.getBookingId(), response.body().jsonPath().getLong("bookingId"));

    }

    @Test
    @Order(4)
    void getInvitedGuest()
    {
        Response response = given().when().contentType(MediaType.APPLICATION_JSON).get("/booking-management/v1/invited-guest/" + invitedGuestId + "/").then()
                .log().all().statusCode(OK.getStatusCode()).extract().response();

        assertEquals(invitedGuestId, response.body().jsonPath().getLong("id"));
    }

    @Test
    @Order(5)
    void saveBooking() {

        TableDto tableDto = TableDto.builder().seatsNumber(3).build();
        tableDto.setId(tableId);

        BookingDto bookingDto = BookingDto.builder()
                .name("Jean Doe")
                .comment("Outside table")
                .bookingDate(Instant.now())
                .expirationDate(Instant.now().plus(30, ChronoUnit.DAYS))
                .creationDate(Instant.now())
                .email("Jean.Doe@testmail.com")
                .canceled(false)
                .bookingType(BookingType.COMMON)
                .orderId(5L)
                .assistants(3)
                .userId(3L)
                .table(tableDto)
                .invitedGuests(null)
                .build();

        Response response = given().when().body(bookingDto).contentType(MediaType.APPLICATION_JSON)
                .post("/booking-management/v1/booking/").then().log().all()
                .statusCode(CREATED.getStatusCode()).extract().response();
        String url = response.header("Location");
        response = given().when().contentType(MediaType.APPLICATION_JSON).get(url).then()
                .log().all().statusCode(OK.getStatusCode()).extract().response();

        bookingId = response.body().jsonPath().getLong("id");

        assertEquals(bookingDto.getName(), response.body().jsonPath().getString("name"));

    }

    @Test
    @Order(6)
    void getBooking() {
        Response response = given().when().contentType(MediaType.APPLICATION_JSON)
                .get("/booking-management/v1/booking/" + bookingId + "/").then()
                .log().all().statusCode(OK.getStatusCode()).extract().response();

        assertEquals(bookingId, response.body().jsonPath().getLong("id"));
    }

    @Test
    @Order(7)
    void deleteBooking()
    {
        {
            Response response = given().when().contentType(MediaType.APPLICATION_JSON).delete("/booking-management/v1/booking/" + bookingId + "/").then()
                    .log().all().statusCode(NO_CONTENT.getStatusCode()).extract().response();

        }
    }

    @Test
    @Order(8)
    void deleteInvitedGuest()
    {
        {
            Response response = given().when().contentType(MediaType.APPLICATION_JSON).delete("/booking-management/v1/invited-guest/" + invitedGuestId + "/").then()
                    .log().all().statusCode(NO_CONTENT.getStatusCode()).extract().response();
        }
    }

    @Test
    @Order(19)
    void deleteTable()
    {
        {
            Response response = given().when().contentType(MediaType.APPLICATION_JSON).delete("/booking-management/v1/table/" + tableId + "/").then()
                    .log().all().statusCode(NO_CONTENT.getStatusCode()).extract().response();
        }
    }
}
