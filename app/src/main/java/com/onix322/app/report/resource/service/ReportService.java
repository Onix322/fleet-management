package com.onix322.app.report.resource.service;


import com.onix322.app.report.resource.model.ReportAbstract;
import com.onix322.app.report.resource.repository.ReportRepository;
import com.onix322.http.exception.client.BadRequestException;
import com.onix322.http.exception.client.NotFoundException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
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
            throw new BadRequestException("Vehicle id and details are required");
        }
        return reportRepository.save(report);
    }

    public List<T> getAll() {
        return reportRepository.findAll();
    }

    public Boolean delete(UUID id) {
        if (id == null) {
            throw new BadRequestException("Report not found");
        }
        return reportRepository.delete(id);
    }

    public T getById(UUID id) {
        if (id == null) {
            throw new BadRequestException("Report id is required");
        }
        Optional<T> report = reportRepository.find(id);
        return report.orElseThrow(() -> new NotFoundException("Report not found"));
    }

    public T update(UUID id, T report) {
        if (id == null || report == null) {
            throw new BadRequestException("Report id is required");
        }
        report.setId(id);
        return reportRepository.save(report);
    }

}
