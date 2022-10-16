package com.devonfw.application;

import org.flywaydb.core.Flyway;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.nativex.hint.TypeHint;
import org.springframework.nativex.hint.TypeHints;

@SpringBootApplication
@TypeHints({
		@TypeHint(types = PhysicalNamingStrategyStandardImpl.class, typeNames = "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl"),
		@TypeHint(types = Flyway.class, typeNames = "org.flywaydb.core.Flyway")
})
public class BookingmanagementSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingmanagementSpringApplication.class, args);
	}

}
