package com.onix322.app.image.resource.model;

import com.onix322.app.image.resource.ImageAbstract;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("image_damage")
public class ImageDamage extends ImageAbstract {

}
