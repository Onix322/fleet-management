package com.onix322.app.details.resource.model.imp;

import com.onix322.app.details.resource.model.DetailsAbstract;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("image_details")
public class ImageDetails extends DetailsAbstract {
    @Column
    private Double x;
    @Column
    private Double y;
}
