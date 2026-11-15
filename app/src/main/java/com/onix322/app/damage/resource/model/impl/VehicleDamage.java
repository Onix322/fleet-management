package com.onix322.app.damage.resource.model.impl;

import com.onix322.app.damage.resource.model.DamageAbstract;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("vehicle_damage")
public class VehicleDamage extends DamageAbstract {

}
