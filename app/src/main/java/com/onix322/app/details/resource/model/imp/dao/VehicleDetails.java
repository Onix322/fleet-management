package com.onix322.app.details.resource.model.imp.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onix322.app.details.resource.model.DetailsAbstract;
import com.onix322.app.vehicle.resource.model.impl.VehicleAbstract;
import com.onix322.app.vehicle.util.OwnershipType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class VehicleDetails extends DetailsAbstract {

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

    @Column(name = "total_milage")
    private Long totalMilage;

    @OneToOne(optional = false)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties("details")
    private VehicleAbstract vehicle;
}
