package com.onix322.app.vehicle.resource.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("non_motorized")
@ToString(callSuper = true)
public class VehicleNonMotorized extends VehicleAbstract{
}
