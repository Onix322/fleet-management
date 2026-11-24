package com.onix322.app.report.resource.model.impl.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onix322.app.damage.resource.model.impl.VehicleDamage;
import com.onix322.app.report.resource.model.ReportAbstract;
import com.onix322.app.vehicle.resource.model.impl.VehicleAbstract;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
public class DamageReport extends ReportAbstract {

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    @JsonIgnoreProperties("damageReports")
    private VehicleAbstract vehicle;

    @OneToOne(mappedBy = "report", cascade = CascadeType.ALL)
    private VehicleDamage damage;
}
