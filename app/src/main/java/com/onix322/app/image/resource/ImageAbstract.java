package com.onix322.app.image.resource;

import com.onix322.app.vehicle.resource.model.VehicleAbstract;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@Entity
@Table(name = "image")
@DiscriminatorColumn(name = "image_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ImageAbstract implements Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private VehicleAbstract vehicle;
}
