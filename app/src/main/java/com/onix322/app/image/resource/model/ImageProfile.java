package com.onix322.app.image.resource.model;

import com.onix322.app.image.resource.ImageAbstract;
import com.onix322.app.vehicle.resource.model.VehicleAbstract;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("image_profile")
public class ImageProfile extends ImageAbstract {


}
