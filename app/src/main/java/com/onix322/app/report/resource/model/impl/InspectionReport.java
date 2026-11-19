package com.onix322.app.report.resource.model.impl;

import com.onix322.app.report.resource.model.ReportAbstract;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("inspection_report")
public class InspectionReport extends ReportAbstract {

}
