package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.TableEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
//TODO Adding Fragments results in not generating classes
public interface TableRepository extends CrudRepository<TableEntity, Long> {
}
