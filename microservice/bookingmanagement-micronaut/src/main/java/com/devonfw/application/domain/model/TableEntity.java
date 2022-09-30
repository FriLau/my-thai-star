package com.devonfw.application.domain.model;

import com.devonfw.application.general.model.ApplicationPersistenceEntity;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Setter
@Table(name = "\"Table\"")
public class TableEntity extends ApplicationPersistenceEntity {

    private Integer seatsNumber;

    public Integer getSeatsNumber() {
        return seatsNumber;
    }
}
