package com.onix322.app.vehicle.resource.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.UUID;

@MappedSuperclass
@Data
public abstract class VehicleAbstract implements Vehicle {
    @Id
    private UUID id;
}
