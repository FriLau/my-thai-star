package com.devonfw.application;

import org.flywaydb.core.Flyway;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.nativex.hint.TypeHint;
import org.springframework.nativex.hint.TypeHints;

import java.time.Instant;

@SpringBootApplication
@TypeHints({
		@TypeHint(types = PhysicalNamingStrategyStandardImpl.class, typeNames = "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl"),
		@TypeHint(types = Flyway.class, typeNames = "org.flywaydb.core.Flyway")
})
public class BookingmanagementSpringApplication {
	private static final Logger LOG = LoggerFactory.getLogger(BookingmanagementSpringApplication.class);


	public static void main(String[] args) {
		LOG.debug("Start Time:" + Instant.now());
		SpringApplication.run(BookingmanagementSpringApplication.class, args);
		LOG.debug("End Time:" + Instant.now());
	}

}
