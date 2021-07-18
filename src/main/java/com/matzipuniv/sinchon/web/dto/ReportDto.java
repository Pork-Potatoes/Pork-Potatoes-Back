package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.Report;
import com.matzipuniv.sinchon.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportDto {

    private Long reportNum;
    private Long review;
    private String description;
    private Long user;

    public static ReportDto of(Report report){
        ReportDto reportDto = new ReportDto();
        reportDto.setReview(report.getReview());
        reportDto.setDescription(report.getDescription());
        reportDto.setUser(report.getUser());

        return reportDto;
    }
}