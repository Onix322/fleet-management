package com.onix322.app.report.resource.service;


import com.onix322.app.report.resource.model.ReportAbstract;
import com.onix322.app.report.resource.repository.ReportRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
@AllArgsConstructor(onConstructor_ = @Inject)
public class ReportService<T extends ReportAbstract> {
   private ReportRepository<T> reportRepository;

    public T create(T report) {
        if (report.getVehicle().getId() == null || report.getDetails() == null) {
            throw new IllegalArgumentException("Vehicle id and details are required");
        }
        return reportRepository.save(report);
    }

    public List<T> getAll() {
        return reportRepository.findAll();
    }

    public Boolean delete(UUID id) {

        return reportRepository.delete(id);
    }

    public T getById(UUID id) {
        Optional<T> report = reportRepository.find(id);
        return report.orElseThrow(() -> new NotFoundException("Report not found"));
    }

    public T update(UUID id, T report) {
        report.setId(id);
        return reportRepository.save(report);
    }

}
