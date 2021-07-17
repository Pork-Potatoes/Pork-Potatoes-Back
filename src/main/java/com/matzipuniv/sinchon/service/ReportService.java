package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.Report;
import com.matzipuniv.sinchon.domain.ReportRepository;
import com.matzipuniv.sinchon.web.dto.ReportDto;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

    public void registerReport(ReportDto reportDto){
        Report report = new Report(reportDto.getReview(), reportDto.getDescription(), reportDto.getUser());

        reportRepository.save(report);
    }
}
