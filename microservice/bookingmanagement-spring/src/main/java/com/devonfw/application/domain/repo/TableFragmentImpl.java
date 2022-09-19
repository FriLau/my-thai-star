package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.QTableEntity;
import com.devonfw.application.domain.model.TableEntity;
import com.devonfw.application.service.model.TableSearchCriteriaDto;
import com.devonfw.application.utils.QueryUtil;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
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
        QTableEntity tableEntity = QTableEntity.tableEntity;
        List<Predicate> predicates = new ArrayList<>();

        Integer seatsNumber = criteria.getSeatsNumber();
        if (seatsNumber != null) {
            predicates.add(tableEntity.seatsNumber.eq(seatsNumber));
        }

        JPAQuery<TableEntity> query = new JPAQuery<TableEntity>(this.entityManager).from(tableEntity);
        if (!predicates.isEmpty())
        {
            query.where(predicates.toArray(Predicate[]::new));
        }

        Pageable pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize());
        return QueryUtil.findPaginated(pageable, query, criteria.isDetermineTotal());
    }
}
