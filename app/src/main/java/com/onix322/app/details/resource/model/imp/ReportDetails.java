package com.onix322.app.details.resource.model.imp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onix322.app.details.resource.model.DetailsAbstract;
import com.onix322.app.report.resource.model.ReportAbstract;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("report_details")
public class ReportDetails extends DetailsAbstract {
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "report_id", referencedColumnName = "id")
    @JsonIgnore
    private ReportAbstract report;
}
