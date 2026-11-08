package com.onix322.app.vehicle.resource.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public class VehicleNonMotorized extends VehicleAbstract{
}
