package com.onix322.app.vehicle.resource.model.impl;

import com.fasterxml.jackson.annotation.*;
import com.onix322.app.details.resource.model.imp.dao.VehicleDetails;
import com.onix322.app.image.resource.model.impl.dao.ImageProfile;
import com.onix322.app.report.resource.model.impl.dao.DamageReport;
import com.onix322.app.report.resource.model.impl.dao.InspectionReport;
import com.onix322.app.vehicle.resource.model.Vehicle;
import com.onix322.app.vehicle.resource.model.impl.dao.Van;
import com.onix322.app.vehicle.util.OperationalStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.BatchSize;

import java.util.List;
import java.util.UUID;

@Data
@Table(name = "vehicle")
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Van.class, name = "van"),
})
public abstract class VehicleAbstract implements Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "operational_status", nullable = false)
    private OperationalStatus operationalStatus;

    @OneToOne(
            mappedBy = "vehicle",
            orphanRemoval = true,
            optional = false,
            cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties("vehicle")
    private VehicleDetails details;

    @OneToMany(
            mappedBy = "vehicle",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties("vehicle")
    @BatchSize(size = 10)
    private List<ImageProfile> images;

    @OneToMany(
            mappedBy = "vehicle",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties("vehicle")
    @BatchSize(size = 10)
    private List<InspectionReport> inspectionReports;

    @OneToMany(
            mappedBy = "vehicle",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties("vehicle")
    @BatchSize(size = 10)
    private List<DamageReport> damageReports;
}
