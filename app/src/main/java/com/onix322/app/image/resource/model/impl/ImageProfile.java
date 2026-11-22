package com.onix322.app.image.resource.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onix322.app.image.resource.model.ImageAbstract;
import com.onix322.app.vehicle.resource.model.VehicleAbstract;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("image_profile")
public class ImageProfile extends ImageAbstract {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties("images")
    private VehicleAbstract vehicle;
}
