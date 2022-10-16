package com.devonfw.application;

import io.micronaut.core.annotation.ReflectionConfig;
import io.micronaut.core.annotation.TypeHint;
import io.micronaut.runtime.Micronaut;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;

@ReflectionConfig(type = PhysicalNamingStrategyStandardImpl.class, accessType = TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS)
@ReflectionConfig(type = ImplicitNamingStrategyLegacyJpaImpl.class, accessType = TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS)
public class Application {
    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
