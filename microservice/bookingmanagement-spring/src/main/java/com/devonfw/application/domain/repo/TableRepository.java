package com.devonfw.application.domain.repo;

import com.devonfw.application.domain.model.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Long>, TableFragment {
}
