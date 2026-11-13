package com.onix322.app.report.resource.service;


import com.onix322.app.report.resource.model.impl.InspectionReport;
import com.onix322.app.report.resource.repository.ReportRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
@AllArgsConstructor(onConstructor_ = @Inject)
public class InspectionReportService {
    private ReportRepository reportRepository;

    public InspectionReport create(InspectionReport report) {
        if (report.getVehicle().getId() == null || report.getDetails() == null){
            throw new IllegalArgumentException("Vehicle id and details are required");
        }
        return reportRepository.save(report);
    }

    public List<InspectionReport> getAll() {
        return reportRepository.findAll();
    }

    public Boolean delete(UUID id) {
        Optional<InspectionReport> report = reportRepository.find(id);
        report.ifPresent(value -> reportRepository.delete(value));
        return reportRepository.exists(id);
    }

    public Optional<InspectionReport> getById(UUID id) {
        return reportRepository.find(id);
    }

    public InspectionReport update(UUID id, InspectionReport report) {
        report.setId(id);
        return reportRepository.update(report);
    }

}
