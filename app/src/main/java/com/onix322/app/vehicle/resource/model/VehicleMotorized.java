package com.onix322.app.vehicle.resource.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
public class VehicleMotorized  extends VehicleAbstract {

}
