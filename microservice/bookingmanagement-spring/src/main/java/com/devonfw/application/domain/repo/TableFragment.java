package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.TableEntity;
import com.devonfw.application.service.model.TableSearchCriteriaDto;
import org.springframework.data.domain.Page;

public interface TableFragment {
    public Page<TableEntity> findTables(TableSearchCriteriaDto criteria);
}
