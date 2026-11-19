package com.onix322.app.image.resource.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onix322.app.image.resource.model.ImageAbstract;
import com.onix322.app.report.resource.model.ReportAbstract;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("image_report")
public class ImageReport extends ImageAbstract {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "report_id", referencedColumnName = "id")
    @JsonIgnoreProperties("images")
    private ReportAbstract report;
}
