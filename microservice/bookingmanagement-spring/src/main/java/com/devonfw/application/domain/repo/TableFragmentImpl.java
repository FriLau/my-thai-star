package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.QTableEntity;
import com.devonfw.application.domain.model.TableEntity;
import com.devonfw.application.service.model.TableSearchCriteriaDto;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;


public class TableFragmentImpl implements TableFragment{

    @Inject
    EntityManager entityManager;

    /**
     * @param criteria the {@link TableSearchCriteriaDto} with the criteria to search.
     * @return the {@link Page} of the {@link TableEntity} objects that matched the search.
     */
    @Override
    public Page<TableEntity> findTables(TableSearchCriteriaDto criteria)
    {
        Pageable pageable = criteria.getPageable();
        QTableEntity tableEntity = QTableEntity.tableEntity;

        JPAQuery<TableEntity> query = new JPAQuery<>(this.entityManager);
        query.from(tableEntity);

        Integer seatsNumber = criteria.getSeatsNumber();
        if (seatsNumber != null) {
            query.where(tableEntity.seatsNumber.eq(seatsNumber));
        }

        List<TableEntity> tableList = query.limit(pageable.getPageSize())
                .offset(pageable.getPageNumber() * pageable.getPageSize()).fetch();
        return new PageImpl<>(tableList, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()),
                tableList.size());
    }
}
