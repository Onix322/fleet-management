package com.onix322.app.details.resource.model.imp;

import com.onix322.app.damage.resource.model.DamageAbstract;
import com.onix322.app.details.resource.model.DetailsAbstract;
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
@DiscriminatorValue("damage_details")
public class DamageDetails extends DetailsAbstract {
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "damage_id", referencedColumnName = "id")
    private DamageAbstract damage;
}
