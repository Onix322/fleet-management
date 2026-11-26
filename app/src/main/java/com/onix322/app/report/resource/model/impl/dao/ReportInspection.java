package com.onix322.app.report.resource.model.impl.dao;

import com.onix322.app.report.resource.model.ReportAbstract;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
public class ReportInspection extends ReportAbstract {

}
