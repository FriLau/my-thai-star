package com.devonfw.application;

import io.micronaut.core.annotation.ReflectionConfig;
import io.micronaut.core.annotation.TypeHint;
import io.micronaut.runtime.Micronaut;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

@ReflectionConfig(type = PhysicalNamingStrategyStandardImpl.class, accessType = TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS)
@ReflectionConfig(type = ImplicitNamingStrategyLegacyJpaImpl.class, accessType = TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS)
public class BookingmanagementMicronautApplication {
    private static final Logger LOG = LoggerFactory.getLogger(BookingmanagementMicronautApplication.class);

    public static void main(String[] args) {
        LOG.info("Start Time:" + Instant.now());
        Micronaut.run(BookingmanagementMicronautApplication.class, args);
        LOG.info("End Time:" + Instant.now());
    }
}
