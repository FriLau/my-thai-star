package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.TableEntity;
import com.devonfw.application.service.rest.model.TableSearchCriteriaDto;
import org.springframework.data.domain.Page;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TableFragmentImpl implements TableFragment{

    @Inject
    EntityManager entityManager;

    @Override
    public Page<TableEntity> findTables(TableSearchCriteriaDto criteria)
    {
        // TODO
        return null;

    }
}
