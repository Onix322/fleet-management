package com.onix322.app.vehicle.resource.model.impl;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString(callSuper = true)
public class VehicleMotorized extends VehicleAbstract {

    @Column(name = "last_pti")
    private Date lastPTI; // last periodic technical inspection

    @Column(name = "vin", unique = true, nullable = false)
    private String vin;

    @Column(name = "license_plate", unique = true, nullable = false)
    private String licensePlate;
}
