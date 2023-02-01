package com.example.LatihanRestApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetReportResponseDTO {

    List<GetReportDTO> reportBalanceChangeInPercentage;

    public GetReportResponseDTO(List<GetReportDTO> reportBalanceChangeInPercentage) {
        this.reportBalanceChangeInPercentage = reportBalanceChangeInPercentage;
    }
}