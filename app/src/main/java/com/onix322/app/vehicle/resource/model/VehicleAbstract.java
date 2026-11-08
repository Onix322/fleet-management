package com.onix322.app.vehicle.resource.model;

import com.onix322.app.vehicle.util.OwnershipType;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@MappedSuperclass
@Data
public abstract class VehicleAbstract implements Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "construction_year")
    private Date constructionYear;

    @Column(name = "ownership_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OwnershipType ownType;

    @Column(name = "registration_date")
    private Date registrationDate;

}
