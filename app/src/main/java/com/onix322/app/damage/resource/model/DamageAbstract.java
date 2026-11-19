package com.onix322.app.damage.resource.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.onix322.app.damage.resource.model.impl.VehicleDamage;
import com.onix322.app.details.resource.model.imp.DamageDetails;
import com.onix322.app.details.resource.model.imp.ImageDetails;
import com.onix322.app.details.resource.model.imp.ReportDetails;
import com.onix322.app.details.resource.model.imp.VehicleDetails;
import com.onix322.app.image.resource.model.impl.ImageDamage;
import com.onix322.app.report.resource.model.impl.DamageReport;
import com.onix322.app.vehicle.resource.model.VehicleAbstract;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Instantiator;

import java.util.UUID;

@Entity
@Table(name = "damages")
@Data
@DiscriminatorColumn(name = "damage_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = VehicleDamage.class, name = "vehicle_damage"),
})
public class DamageAbstract implements Damage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "damage")
    private DamageDetails damageDetails;

    @OneToOne(mappedBy = "damage")
    private ImageDamage imageDamage;

    @OneToOne
    @JoinColumn(name = "report_id", referencedColumnName = "id")
    @JsonIgnoreProperties("damage")
    private DamageReport report;
}
