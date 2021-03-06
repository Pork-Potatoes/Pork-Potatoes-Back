package com.matzipuniv.sinchon.web;

import com.matzipuniv.sinchon.service.ReportService;
import com.matzipuniv.sinchon.web.dto.ReportDto;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ReportApiController {

    private final ReportService reportService;

    public ReportApiController(ReportService reportService){
        this.reportService = reportService;
    }

    @PostMapping("/api/reports")
    public String registerReport(@RequestBody ReportDto reportDto){
        reportService.registerReport(reportDto);

        return "Success";
    }
}