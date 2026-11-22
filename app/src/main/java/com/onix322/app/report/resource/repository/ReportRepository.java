package com.onix322.app.report.resource.repository;

import com.onix322.app.report.resource.model.ReportAbstract;
import com.onix322.app.report.resource.model.impl.InspectionReport;
import com.onix322.database.repository.BaseRepository;
import jakarta.inject.Singleton;

import java.util.UUID;

@Singleton
public class ReportRepository<T extends ReportAbstract> extends BaseRepository<T, UUID> {
    public ReportRepository(Class<T> type) {
        super(type);
    }
}
