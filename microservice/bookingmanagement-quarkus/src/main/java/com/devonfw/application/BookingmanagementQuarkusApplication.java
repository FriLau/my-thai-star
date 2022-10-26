package com.devonfw.application;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;


@QuarkusMain
public class BookingmanagementQuarkusApplication {
    private static final Logger LOG = LoggerFactory.getLogger(BookingmanagementQuarkusApplication.class);


    public static void main(String ... args) {
        LOG.info("Start Time:" + Instant.now());
        Quarkus.run(args);
        LOG.info("End Time:" + Instant.now());
    }
}
