package com.onix322.app.vehicle.resource.model;

import com.onix322.app.image.resource.model.ImageDamage;
import com.onix322.app.image.resource.model.ImageProfile;
import com.onix322.app.vehicle.util.OwnershipType;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@Table(name = "vehicle")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "vehicle_type")
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

    @OneToMany
    private List<ImageProfile> imageProfileList;

    @OneToMany
    private List<ImageDamage> imageDamageList;
}
