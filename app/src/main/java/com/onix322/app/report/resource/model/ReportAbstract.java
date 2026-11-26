package com.onix322.app.report.resource.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.onix322.app.details.resource.model.imp.dao.ReportDetails;
import com.onix322.app.image.resource.model.impl.dao.ImageReport;
import com.onix322.app.report.resource.model.impl.dao.DamageReport;
import com.onix322.app.report.resource.model.impl.dao.ReportInspection;
import com.onix322.app.vehicle.resource.model.impl.VehicleAbstract;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Table(name = "report")
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "report_type")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ReportInspection.class, name = "inspection_report"),
        @JsonSubTypes.Type(value = DamageReport.class, name = "damage_report"),
})
public abstract class ReportAbstract implements Report {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(
            mappedBy = "report",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private ReportDetails details;

    @OneToMany(
            mappedBy = "report",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ImageReport> images;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    @JsonIgnoreProperties("inspectionReports")
    private VehicleAbstract vehicle;
}
