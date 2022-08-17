package com.devonfw.application;


import com.devonfw.application.domain.model.BookingType;
import com.devonfw.application.service.rest.model.BookingDto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class BookingManagementRestTest {

    @Test
    @Order(1)
    void saveTable()
    {

    }

    @Test
    @Order(2)
    void getTable()
    {

    }

    @Test
    @Order(3)
    void saveInvitedGuest()
    {

    }

    @Test
    @Order(4)
    void getInvitedGuest()
    {

    }

    @Test
    @Order(5)
    void saveBooking() {

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
                .table(null)
                .invitedGuests(null)
                .build();
        given().body(bookingDto)
                .when().post("/bookingmanagement/v1/booking/")
                .then()
                .statusCode(200)
                .body(is(bookingDto));

//        Response response = given().when().body(bookingDto).contentType(MediaType.APPLICATION_JSON)
//                .post("/bookingmanagement/v1/booking/").then().log().all()
//                .statusCode(CREATED.getStatusCode()).extract().response();
//        String url = response.header("Location");
//        response = given().when().contentType(MediaType.APPLICATION_JSON).get(url).then()
//                .log().all().statusCode(OK.getStatusCode()).extract().response();
//
//        assertEquals(bookingDto.getName(), response.body().jsonPath().getString("name"));

    }

    @Test
    @Order(6)
    void getBooking() {

    }
}
