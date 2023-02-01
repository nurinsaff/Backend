package com.example.LatihanRestApi.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class GetReportDTO {

    String username;
    String changeInPercentage;
    String balanceChangeDate;

    public GetReportDTO(String username, String changeInPercentage, String balanceChangeDate) {
        this.username = username;
        this.changeInPercentage = changeInPercentage;
        this.balanceChangeDate = balanceChangeDate;
    }
}
