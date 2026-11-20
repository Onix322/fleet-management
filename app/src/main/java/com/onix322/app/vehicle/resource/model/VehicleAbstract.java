package com.onix322.app.vehicle.resource.model;

import com.fasterxml.jackson.annotation.*;
import com.onix322.app.details.resource.model.imp.VehicleDetails;
import com.onix322.app.image.resource.model.impl.ImageProfile;
import com.onix322.app.report.resource.model.impl.DamageReport;
import com.onix322.app.report.resource.model.impl.InspectionReport;
import com.onix322.app.vehicle.resource.model.impl.Van;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.BatchSize;

import java.util.List;
import java.util.UUID;

@Data
@Table(name = "vehicle")
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "vehicle_type")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "vehicle_type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Van.class, name = "van"),
})
public abstract class VehicleAbstract implements Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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
