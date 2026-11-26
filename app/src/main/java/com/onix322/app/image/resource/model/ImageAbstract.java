package com.onix322.app.image.resource.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.onix322.app.details.resource.model.imp.dao.ImageDetails;
import com.onix322.app.image.resource.model.impl.dao.ImageDamage;
import com.onix322.app.image.resource.model.impl.dao.ImageProfile;
import com.onix322.app.image.resource.model.impl.dao.ImageReport;
import jakarta.persistence.*;
import lombok.Data;

import java.net.URI;
import java.util.UUID;

@Data
@Entity
@Table(name = "image")
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

    @Column(name = "image_path")
    private URI path;

    @OneToOne
    @JoinColumn(name = "details_id", referencedColumnName = "id")
    private ImageDetails details;
}
