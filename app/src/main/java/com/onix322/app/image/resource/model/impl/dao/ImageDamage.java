package com.onix322.app.image.resource.model.impl.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onix322.app.damage.resource.model.DamageAbstract;
import com.onix322.app.details.resource.model.imp.dao.ImageDetails;
import com.onix322.app.image.resource.model.ImageAbstract;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class ImageDamage extends ImageAbstract {

    @OneToOne
    @JoinColumn(name = "details_id", referencedColumnName = "id")
    private ImageDetails details;

    @OneToOne
    @JoinColumn(name = "damage_id", referencedColumnName = "id")
    @JsonIgnoreProperties("imageDamage")
    private DamageAbstract damage;
}
