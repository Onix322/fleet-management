package com.onix322.app.vehicle.resource.model;

import com.onix322.app.vehicle.util.OwnershipType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DiscriminatorValue("motorized")
public class VehicleMotorized  extends VehicleAbstract {

    @Column(name = "last_pti")
    private Date lastPTI; // last periodic technical inspection

    @Column(name = "vin", unique = true, nullable = false)
    private String vin;

    @Column(name = "license_plate", unique = true, nullable = false)
    private String licensePlate;
}
