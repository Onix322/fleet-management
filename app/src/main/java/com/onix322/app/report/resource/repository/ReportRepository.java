package com.onix322.app.report.resource.repository;

import com.onix322.app.report.resource.model.impl.InspectionReport;
import com.onix322.database.repository.BaseRepository;
import jakarta.inject.Singleton;

import java.util.UUID;

@Singleton
public class ReportRepository extends BaseRepository<InspectionReport, UUID> {
    public ReportRepository() {
        super(InspectionReport.class);
    }
}
