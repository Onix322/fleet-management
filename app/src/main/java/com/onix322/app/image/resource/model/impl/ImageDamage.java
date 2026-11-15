package com.onix322.app.image.resource.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onix322.app.damage.resource.model.DamageAbstract;
import com.onix322.app.details.resource.model.imp.ImageDetails;
import com.onix322.app.image.resource.model.ImageAbstract;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("image_damage")
public class ImageDamage extends ImageAbstract {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "details_id", referencedColumnName = "id")
    private ImageDetails details;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "damage_id", referencedColumnName = "id")
    @JsonIgnoreProperties("imageDamage")
    private DamageAbstract damage;
}
