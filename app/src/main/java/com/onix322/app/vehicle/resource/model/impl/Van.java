package com.onix322.app.vehicle.resource.model.impl;

import com.onix322.app.vehicle.resource.model.VehicleMotorized;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@DiscriminatorValue("van")
@Entity
public class Van extends VehicleMotorized {

}
