package com.onix322.app.report.resource.service;

import com.onix322.app.report.resource.model.impl.dao.ReportInspection;
import com.onix322.app.report.resource.model.impl.dto.ReportDto;
import com.onix322.app.report.resource.model.impl.mapper.ReportMapper;
import com.onix322.app.report.resource.repository.ReportRepository;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

public class InspectionReportService extends ReportService<ReportInspection>{
    private ReportMapper mapper;

    @Inject
    public InspectionReportService(ReportRepository<ReportInspection> reportRepository) {
        super(reportRepository);
    }

    public ReportDto save(ReportInspection reportInspection) {

        if (reportInspection == null) {
            throw new IllegalArgumentException("ReportInspection cannot be null");
        }

        ReportInspection savedReport = super.create(reportInspection);

        return mapper.toReportDto(savedReport);
    }


}
