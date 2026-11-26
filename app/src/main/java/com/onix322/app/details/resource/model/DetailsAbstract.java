package com.onix322.app.details.resource.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.onix322.app.details.resource.model.imp.dao.DamageDetails;
import com.onix322.app.details.resource.model.imp.dao.ImageDetails;
import com.onix322.app.details.resource.model.imp.dao.ReportDetails;
import com.onix322.app.details.resource.model.imp.dao.VehicleDetails;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "details")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DamageDetails.class, name = "damage_details"),
        @JsonSubTypes.Type(value = ImageDetails.class, name = "image_details"),
        @JsonSubTypes.Type(value = ReportDetails.class, name = "report_details"),
        @JsonSubTypes.Type(value = VehicleDetails.class, name = "vehicle_details"),
})

public class DetailsAbstract implements Details {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
