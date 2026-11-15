package com.onix322.app.image.resource.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.onix322.app.details.resource.model.imp.ImageDetails;
import com.onix322.app.image.resource.model.impl.ImageDamage;
import com.onix322.app.image.resource.model.impl.ImageProfile;
import com.onix322.app.image.resource.model.impl.ImageReport;
import com.onix322.app.report.resource.model.impl.InspectionReport;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "image")
@DiscriminatorColumn(name = "image_type")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ImageProfile.class, name = "image_profile"),
        @JsonSubTypes.Type(value = ImageDamage.class, name = "image_damage"),
        @JsonSubTypes.Type(value = ImageReport.class, name = "image_report"),
})
public class ImageAbstract implements Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
