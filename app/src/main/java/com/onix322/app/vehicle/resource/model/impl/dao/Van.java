package com.onix322.app.vehicle.resource.model.impl.dao;

import com.onix322.app.vehicle.resource.model.impl.VehicleMotorized;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@ToString(callSuper = true)
public class Van extends VehicleMotorized {

}
