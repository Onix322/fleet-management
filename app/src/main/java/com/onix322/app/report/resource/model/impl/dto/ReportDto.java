package com.onix322.app.report.resource.model.impl.dto;

import com.onix322.app.details.resource.model.imp.dao.ReportDetails;
import com.onix322.app.image.resource.model.impl.dao.ImageReport;
import com.onix322.app.vehicle.resource.model.impl.dto.VehicleDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDto {
    private ReportDetails details;
    private List<ImageReport> images;
    private VehicleDto vehicle;
}
