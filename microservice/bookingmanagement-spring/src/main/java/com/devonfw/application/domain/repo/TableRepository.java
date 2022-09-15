package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<TableEntity, Long>, TableFragment {
}
