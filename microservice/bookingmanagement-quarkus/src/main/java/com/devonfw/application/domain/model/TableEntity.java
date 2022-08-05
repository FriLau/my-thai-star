package com.devonfw.application.domain.model;

import com.devonfw.application.general.domain.model.ApplicationPersistenceEntity;

import javax.persistence.Entity;

@Entity
@javax.persistence.Table(name = "\"Table\"")
public class TableEntity extends ApplicationPersistenceEntity {

    private Integer seatsNumber;

    private static final long serialVersionUID = 1L;

    /**
     * @return seatsNumber
     */
    public Integer getSeatsNumber() {

        return this.seatsNumber;
    }

    /**
     * @param seatsNumber new value of {@link #getSeatsNumber}.
     */
    public void setSeatsNumber(Integer seatsNumber) {

        this.seatsNumber = seatsNumber;
    }

}
